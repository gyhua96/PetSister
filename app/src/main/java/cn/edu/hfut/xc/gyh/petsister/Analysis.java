package cn.edu.hfut.xc.gyh.petsister;

import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Analysis extends AppCompatActivity {
    String data;
    String datas[];
    String days[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Analysisdate Ad=new Analysisdate();
        Ad.execute(null,null);
    }

    class Analysisdate extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpURLConnection httpURLConnection = null;
                InputStream inputStream = null;
                String resultData = "";
                URL url = new URL(Config.Analysis);   //URl对象
                httpURLConnection = (HttpURLConnection) url.openConnection();   //使用URl打开一个链接

                httpURLConnection.setDoInput(true);//允许输入流，即允许下载
                httpURLConnection.setDoOutput(true);//允许输出流，即允许上传
                httpURLConnection.setUseCaches(false); //不使用缓冲
                httpURLConnection.setRequestMethod("GET"); //使用get请求
                try {
                    inputStream = httpURLConnection.getInputStream();//获取输入流，此时才真正建立链接
                } catch (Exception e) {
                    e.printStackTrace();
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                //System.out.println(bufferedReader.readLine());
                while ((inputLine = bufferedReader.readLine()) != null) {
                    //System.out.println(inputLine);
                    data = inputLine + resultData;
                    //weight=resultData;
                }
                System.out.println(data);
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            datas=data.split(";");
            TextView[] tv=new TextView[6];
            tv[0]=(TextView) findViewById(R.id.analysis0);
            tv[1]=(TextView) findViewById(R.id.analysis1);
            tv[2]=(TextView) findViewById(R.id.analysis2);
            tv[3]=(TextView) findViewById(R.id.analysis3);
            tv[4]=(TextView) findViewById(R.id.analysis4);
            tv[5]=(TextView) findViewById(R.id.analysis5);
            for(int i=0;i<datas.length-1;i++){
                days=datas[i].split(",");
                System.out.println(i);
                //System.out.println(days[i]);
                //System.out.println(getString(R.string.eat)+(Integer.parseInt(days[2])-Integer.parseInt(days[1]))+getString(R.string.eating)+(days[1])+getString(R.string.ate));
                tv[i].setText("在 "+days[2]+" 这一次喂食中，你喂食了 "+days[1] +" 克,剩余 "+
                        days[0]+" 克，宠物吃了 "+(Integer.parseInt(days[1])-Integer.parseInt(days[0]))+" 克。");
            }
        }

    }
}
