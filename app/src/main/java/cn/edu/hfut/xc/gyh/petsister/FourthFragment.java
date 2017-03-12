package cn.edu.hfut.xc.gyh.petsister;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class FourthFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_person,container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        TextView user_name=(TextView) view.findViewById(R.id.username);
        String user = sharedPreferences.getString("email", "");
        System.out.println("userlogin"+user);
        user_name.setText(user);
        return view;
    }
}