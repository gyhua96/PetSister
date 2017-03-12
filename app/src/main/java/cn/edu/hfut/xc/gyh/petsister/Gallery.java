package cn.edu.hfut.xc.gyh.petsister;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Gallery extends AbsSingleFragmentActivity {
    public static Gallery mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
    }
    @Override
    protected Fragment createFragment()
    {
        return new ListImgsFragment();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_single_fragment;
    }

}
