package cn.edu.hfut.xc.gyh.petsister;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;   // 注意这里我们导入的V4的包，不要导成app的包了
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 主页面内容
 * Created by dm on 16-1-19.
 */
public class Home extends AppCompatActivity  implements View.OnClickListener {
    // 保存用户
    SharedPreferences sharedPreferences;

    // 初始化顶部栏显示
    private ImageView titleLeftImv;
    private TextView titleTv;

    // 定义4个Fragment对象
    private FirstFragment fg1;
    private SecondFragment fg2;
    private ThirdFragment fg3;
    private FourthFragment fg4;
    private FifthFragment fg5;

    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;

    // 定义每个选项中的相关控件
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private RelativeLayout thirdLayout;
    private RelativeLayout fourthLayout;
    private RelativeLayout fifthLayout;

    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage;
    private ImageView fifthImage;

    private TextView title;
    private ImageView feedUp;

    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;
    private TextView fifthText;

    // 定义几个颜色
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int dark = 0xff000000;

    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;

    // 事件起点
    public static Home mActivity;

    private String email;
    private String pwd;
    private String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActivity=this;
        fragmentManager = getSupportFragmentManager();
        initView(); // 初始化界面控件
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");
        pwd = sharedPreferences.getString("pwd", "");
        uuid = sharedPreferences.getString("uuid", "");
        setChioceItem(0);   // 初始化页面加载时显示第一个选项卡
    }

    /**
     * 初始化页面
     */
    private void initView() {
        // 初始化页面标题栏
        //titleLeftImv = (ImageView) findViewById(R.id.title_imv);
        /*titleLeftImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, LoginActivity.class));
            }
        });
        */

        title = (TextView) findViewById(R.id.title_text_tv);
        feedUp = (ImageView) findViewById(R.id.feedUp);
        // 初始化底部导航栏的控件
        firstImage = (ImageView) findViewById(R.id.first_image);
        secondImage = (ImageView) findViewById(R.id.second_image);
        thirdImage = (ImageView) findViewById(R.id.third_image);
        fourthImage = (ImageView) findViewById(R.id.fourth_image);
        fifthImage = (ImageView) findViewById(R.id.fifth_image);

        firstText = (TextView) findViewById(R.id.first_text);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        fourthText = (TextView) findViewById(R.id.fourth_text);
        fifthText = (TextView) findViewById(R.id.fifth_text);

        firstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        secondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        thirdLayout = (RelativeLayout) findViewById(R.id.third_layout);
        fourthLayout = (RelativeLayout) findViewById(R.id.fourth_layout);
        fifthLayout = (RelativeLayout) findViewById(R.id.fifth_layout);

        firstLayout.setOnClickListener(Home.this);
        secondLayout.setOnClickListener(Home.this);
        thirdLayout.setOnClickListener(Home.this);
        fourthLayout.setOnClickListener(Home.this);
        fifthLayout.setOnClickListener(Home.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setChioceItem(0);
                break;
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            case R.id.fifth_layout:
                setChioceItem(4);
                break;
            default:
                break;
        }

    }

    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);

        switch (index) {
            case 0:
//                firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
                firstText.setTextColor(dark);
                firstLayout.setBackgroundColor(gray);

                // 如果fg1为空，则创建一个并添加到界面上
                if (fg1 == null) {
                    fg1 = new FirstFragment();
                    fragmentTransaction.add(R.id.content, fg1);
                    title.setText(R.string.title_control);
                    //fg1.init();
                } else {
                    // 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);
                    title.setText(R.string.title_control);
                    //fg1.init();
                    //TextView iv=(TextView) findViewById(R.id.firstPage);
                    //iv.setText("开始了！");

                }

                break;

            case 1:
//                secondImage.setImageResource(R.drawable.XXXX);
                secondText.setTextColor(dark);
                secondLayout.setBackgroundColor(gray);

                if (fg2 == null) {
                    fg2 = new SecondFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                    title.setText(R.string.title_store);
                } else {
                    fragmentTransaction.show(fg2);
                    title.setText(R.string.title_store);
                }

                break;

            case 2:
//                thirdImage.setImageResource(R.drawable.XXXX);
                thirdText.setTextColor(dark);
                thirdLayout.setBackgroundColor(gray);

                if (fg3 == null) {
                    fg3 = new ThirdFragment();
                    fragmentTransaction.add(R.id.content, fg3);
                    title.setText(R.string.title_community);
                } else {
                    fragmentTransaction.show(fg3);
                    title.setText(R.string.title_community);
                }

                break;

            case 3:
//                fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(dark);
                fourthLayout.setBackgroundColor(gray);

                if (fg4 == null) {
                    fg4 = new FourthFragment();
                    fragmentTransaction.add(R.id.content, fg4);
                    title.setText(R.string.title_person);

                } else {
                    fragmentTransaction.show(fg4);
                    title.setText(R.string.title_person);

                }
                //Intent intent = new Intent(this , PersonActivity.class);
                //startActivity(intent);
                //startActivity(new Intent(Home.this, GetTemp.class));
                break;
            case 4:
//                fourthImage.setImageResource(R.drawable.XXXX);
                fifthText.setTextColor(dark);
                fifthLayout.setBackgroundColor(gray);

                if (fg5 == null) {
                    fg5 = new FifthFragment();
                    fragmentTransaction.add(R.id.content, fg5);
                    title.setText(R.string.title_photo);
                } else {
                    fragmentTransaction.show(fg5);
                    title.setText(R.string.title_photo);
                }
                //startActivity(new Intent(Home.this, GetTemp.class));
                break;
        }

        fragmentTransaction.commit();   // 提交

    }

    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
//        firstImage.setImageResource(R.drawable.XXX);
        firstText.setTextColor(gray);
        firstLayout.setBackgroundColor(whirt);

//        secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(whirt);

        //        thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(gray);
        thirdLayout.setBackgroundColor(whirt);

        //        fourthImage.setImageResource(R.drawable.XXX);
        fourthText.setTextColor(gray);
        fourthLayout.setBackgroundColor(whirt);

        fifthText.setTextColor(gray);
        fifthLayout.setBackgroundColor(whirt);
    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }

        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }

        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }

        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
        if (fg5 != null) {
            fragmentTransaction.hide(fg5);
        }
    }

}