package cn.edu.hfut.xc.gyh.petsister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import android.media.MediaRecorder;
import java.util.UUID;
/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class FirstFragment extends Fragment {
    ImageView feedUp;
    TextView temp;
    FrameLayout tempF;
    ImageView any;
    TextView food;
    FrameLayout foodF;
    LinearLayout header;
    ImageView sv;
    TextView tip;
    private MediaRecorder myRecorder;
    // 音频文件保存地址
    private String path;
    private String saveFilePath;
    // 所录音的文件
    String[] listFile = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dev_control, container, false);
        header=( LinearLayout) view.findViewById(R.id.header);
        feedUp = (ImageView) view.findViewById(R.id.feedUp);
        food = (TextView) view.findViewById(R.id.Food);
        temp = (TextView) view.findViewById(R.id.temp);
        tempF= (FrameLayout) view.findViewById(R.id.tempF);
        any = (ImageView) view.findViewById(R.id.any);
        foodF = (FrameLayout) view.findViewById(R.id.FoodF);
        sv=(ImageView) view.findViewById(R.id.click) ;
        tip=(TextView) view.findViewById(R.id.voicetip) ;
        sv.bringToFront();
        tip.bringToFront();


        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                path = Environment.getExternalStorageDirectory()
                        .getCanonicalPath().toString()
                        + "/RECORDERS";
                File files = new File(path);
                if (!files.exists()) {
                    files.mkdir();
                }
                listFile = files.list();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tip.setVisibility(View.VISIBLE);
                        System.out.println("touched");
                        tip.setText("手指上滑取消发送");
                        saveFilePath=path+"/"+UUID.randomUUID().toString() + ".amr";
                        myRecorder = new MediaRecorder();
                        // 从麦克风源进行录音
                        myRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                        // 设置输出格式
                        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                        // 设置编码格式
                        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                        myRecorder.setOutputFile(saveFilePath);
                        try {
                            myRecorder.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        myRecorder.start();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if(event.getY()<0){
                            tip.setText("手指松开取消发送");
                        }else{
                            tip.setText("手指上滑取消发送");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(event.getY()<0){//因为getY是相对控件本身的坐标，所以当<0时，手指已不再此控件上
                            myRecorder.reset();
                            myRecorder.release();
                            Toast.makeText(getActivity(), "取消发送", 1).show();
                        }else {
                            myRecorder.stop();
                            myRecorder.reset();
                            myRecorder.release();
                            System.out.println(saveFilePath);
                            Toast.makeText(getActivity(), "正在发送", 1).show();
                        }
                        tip.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                //
                return true;
            }
        });
        feedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//设置对话框的图标
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View view = factory.inflate(R.layout.feed_num, null);
                builder.setView(view);

                builder.setIcon(android.R.drawable.alert_light_frame);
//设置对话框的标题
                builder.setTitle("请输入喂食量");
//设置文本
                builder.setMessage("请输入要喂食的重量（克）");
//设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et=(EditText) view.findViewById(R.id.feed_num);
                        String feed=Config.FeedUp+"|"+et.getText();
                        Feed fd=new Feed(feed);
                        fd.execute(null,null);
                        System.out.println(feed);
                        //Toast.makeText(MainActivity.this, "感谢使用本软件,再见", Toast.LENGTH_SHORT).show();
                    }
                });
//设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "若不自宫,一定不成功", Toast.LENGTH_SHORT).show();
                        //iflogin=sharedPreferences.getString("uuid","").equals("");
                    }
                });
//使用创建器生成一个对话框对象
                AlertDialog ad = builder.create();
                ad.show();
                ad.findViewById(R.id.feed_num).requestFocus();
                view.findViewById(R.id.feed_num).requestFocus();
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Gallery.class);
                startActivity(intent);
            }
        });
        any.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Analysis.class);
                startActivity(intent);
            }
        });
        foodF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetWeight gw=new GetWeight();
                gw.execute(null,null);
                Snackbar.make(getActivity().getWindow().getDecorView(), "重量刷新成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        tempF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTemp gt=new GetTemp(temp);
                gt.execute(null,null);
                Snackbar.make(getActivity().getWindow().getDecorView(), "温度刷新成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }
    class Feed extends AsyncTask<Void, Void, Boolean> {
        private String feedS;
        public Feed(String f){
            this.feedS=f;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Socket client = new Socket(Config.ServerIp, Config.ServerPort);
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter cc = new PrintWriter(client.getOutputStream());
                cc.write(feedS + "\n");        //注意加一个换行符
                cc.write(Config.GetWeight+"\n");
                cc.flush();
                //str=input.next();
                //String ReceiveString = br.readLine();
                //System.out.println(ReceiveString);
                client.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            System.out.println("feeded");
            Snackbar.make(getActivity().getWindow().getDecorView(), "成功喂食一次，请等待重量更新！", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            GetWeight gw=new GetWeight();
            gw.execute(null,null);

        }
    }
    class GetWeight extends AsyncTask<String, Integer, String>{
        private String s=Config.WeightHost;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String resultData ="";
        @Override
        protected String doInBackground(String... params) {
            // tv.setText("开始中...");//多次 启动下载task时 此处会报错
            try {

                Socket client = new Socket(Config.ServerIp, Config.ServerPort);
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter cc = new PrintWriter(client.getOutputStream());
                //cc.write(Config.FeedUp + "\n");        //注意加一个换行符
                cc.write(Config.GetWeight+"\n");
                cc.flush();
                //str=input.next();
                //String ReceiveString = br.readLine();
                //System.out.println(ReceiveString);
                client.close();


                Thread.sleep(1000);

                URL url = new URL(s);   //URl对象
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
                    resultData = inputLine + resultData ;
                    //weight=resultData;
                }
                System.out.println(resultData);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "end";
        }
        @Override
        protected void onPostExecute(String result) {
            //TextView weightv=(TextView) findViewById(R.id.weight);
            //weightv.setText(
            food.setText(resultData+" g");
            System.out.println(resultData+" 克");
        }

    }
    class GetTemp extends AsyncTask<String, Integer, String> {
        private String s=null;
        private TextView tv;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String resultData ="";
        public GetTemp(TextView tv){
            this.tv=tv;
            s=Config.TempHost;
        }
        protected void onPostExecute(String result) {
            //System.out.println("onPostExecute "+result);
            //System.out.println(resultData+"kjkjn");
            tv.setText(resultData);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(s);   //URl对象
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
                    resultData = inputLine + resultData ;

                }
                System.out.println(resultData);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                httpURLConnection.disconnect();
            }
            return "execute end ";
        }
    }
}