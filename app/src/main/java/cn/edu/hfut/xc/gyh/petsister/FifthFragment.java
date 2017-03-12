package cn.edu.hfut.xc.gyh.petsister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.BoolRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class FifthFragment extends Fragment {
    private String url="http://www.gongyuhua.cn/1.jpg";
    private ImageView photo;
    private String newpic;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.take_photo, container, false);
        photo=(ImageView) view.findViewById(R.id.takePhoto);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(Config.ServerIp+":"+Config.ServerPort);
                loadPhoto loadphoto=new loadPhoto(url);
                loadphoto.execute(null,null);
                Snackbar.make(view, "拍照成功,请等待图片刷新", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {//视图设置一个点击事件的监听器
            @Override
            public void onClick(View v) {//重写点击事件的回调方法

                //System.out.println(this.toString());
                //System.out.println(getItem(position));
                Intent intent = new Intent(Home.mActivity,ImageDetailActivity.class);
                System.out.println("goto pic"+newpic);
                intent.putExtra("url", newpic);
                startActivity(intent);
                //System.out.println(mImageLoader.getDiskCacheDir(imageView.getContext(),mImageLoader.md5(getItem(position))).toString());
            }
        });
        return view;
    }
    class loadPhoto extends AsyncTask<Void, Void, Boolean> {
        private String path;
        loadPhoto(String url){
            this.path=url;
        }
        Bitmap btm;
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                System.out.println(Config.ServerIp+"::"+Config.ServerPort);
                Socket client = new Socket(Config.ServerIp,Config.ServerPort);
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter cc = new PrintWriter(client.getOutputStream());
                cc.write(Config.TakePhoto + "\n");        //注意加一个换行符
                cc.flush();
                client.close();

                Thread.sleep(5000);

                HttpURLConnection httpURLConnection = null;
                InputStream inputStream = null;
                String resultData ="";
                URL url = new URL(Config.NewestPic);   //URl对象
                httpURLConnection = (HttpURLConnection) url.openConnection();   //使用URl打开一个链接

                httpURLConnection.setDoInput(true);//允许输入流，即允许下载
                httpURLConnection.setDoOutput(true);//允许输出流，即允许上传
                httpURLConnection.setUseCaches(false); //不使用缓冲
                httpURLConnection.setRequestMethod("GET"); //使用get请求
                try {
                    inputStream = httpURLConnection.getInputStream();//获取输入流，此时才真正建立链接
                }catch (Exception e){
                    e.printStackTrace();
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                //System.out.println(bufferedReader.readLine());
                while ((inputLine = bufferedReader.readLine())!= null){
                    //System.out.println(inputLine);
                    newpic = inputLine + resultData ;
                    //weight=resultData;
                }
                System.out.println(url+"newurl");


                //url =Config.NewestPic; //"http://www.gongyuhua.cn/petsister/";
            }catch (Exception e){
                e.printStackTrace();
            }
            btm=downloadImgByUrl(newpic,photo);
            System.out.println(newpic+"path"+btm);
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            //ImageView iv=(ImageView) findViewById(R.id.takephoto);
            photo.setImageBitmap(btm);
            //url = "http://www.gongyuhua.cn/petsister/" + ReceiveString;
        }

    }
    public Bitmap downloadImgByUrl(String urlStr, ImageView imageview)
    {
        //FileOutputStream fos = null;
        InputStream is = null;
        try
        {
            URL url = new URL(newpic);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println("conn"+conn);
            is = new BufferedInputStream(conn.getInputStream());
            //is.mark(is.available());
            System.out.println(urlStr);
            System.out.println("is"+is);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bitmap;// = BitmapFactory.decodeStream(is, null, opts);

            //获取imageview想要显示的宽和高
            //ImageSizeUtil.ImageSize imageViewSize = ImageSizeUtil.getImageViewSize(imageview);
            opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts,
                    imageview.getWidth(), imageview.getHeight());

            opts.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeStream(is, null, opts);
            System.out.println("btmap"+bitmap);
            is.close();
            conn.disconnect();
            return bitmap;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }
}