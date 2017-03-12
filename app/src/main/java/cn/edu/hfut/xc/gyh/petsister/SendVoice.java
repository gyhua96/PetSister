package cn.edu.hfut.xc.gyh.petsister;

/**
 * Created by gyh on 17-3-12.
 */

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SendVoice extends Activity {
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice);
        View click=findViewById(R.id.click);
        final TextView tv=(TextView) findViewById(R.id.tv);
        click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tv.setVisibility(View.VISIBLE);

                        tv.setText("手指上滑取消发送");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if(event.getY()<0){
                            tv.setText("手指松开取消发送");
                        }else{
                            tv.setText("手指上滑取消发送");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(event.getY()<0){//因为getY是相对控件本身的坐标，所以当<0时，手指已不再此控件上
                            Toast.makeText(SendVoice.this, "取消发送", 1).show();
                        }else {
                            Toast.makeText(SendVoice.this, "正在发送", 1).show();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
