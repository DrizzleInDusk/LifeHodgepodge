package com.sby.lifehodgepodge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.empty.xingzuo.XZGsonEmpty;

/**
 * Created by Lenovo on 2017/2/19.
 */

public class Fragment_today extends Fragment{
    private TextView time,health,love,supei,money,study,number,summary,color;
    private RatingBar ratingBar;
    private String strtime,strall,strhealth,strcolor,strlove,strsupei,strmoney,strstudy,
            strnumber,strsummary;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_today,container,false);
        time= (TextView) view.findViewById(R.id.xz_frag1_time);
        health= (TextView) view.findViewById(R.id.xz_frag1_health);
        color= (TextView) view.findViewById(R.id.xz_frag1_color);
        love= (TextView) view.findViewById(R.id.xz_frag1_love);
        supei= (TextView) view.findViewById(R.id.xz_frag1_supei);
        money= (TextView) view.findViewById(R.id.xz_frag1_money);
        study= (TextView) view.findViewById(R.id.xz_frag1_study);
        number= (TextView) view.findViewById(R.id.xz_frag1_number);
        summary= (TextView) view.findViewById(R.id.xz_frag1_summary);
        ratingBar= (RatingBar) view.findViewById(R.id.xz_frag1_allrationbar);
        return view;
    }

    public void setValue(XZGsonEmpty empty0){
        strtime=empty0.getDatetime();
        strhealth=empty0.getHealth();
        strcolor =empty0.getColor();
        strlove =empty0.getLove();
        strsupei=empty0.getQFriend();
        strmoney=empty0.getMoney();
        strstudy=empty0.getWork();
        strnumber=empty0.getNumber();
        strsummary=empty0.getSummary();
        strall=empty0.getAll();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (strall.equals("10%") || strall.equals("20%")){
            ratingBar.setNumStars(1);
        }else if (strall.equals("30%") || strall.equals("40%")){
            ratingBar.setNumStars(2);
        }else if (strall.equals("50%") || strall.equals("60%")){
            ratingBar.setNumStars(3);
        }else if (strall.equals("70%") || strall.equals("80%")){
            ratingBar.setNumStars(4);
        }else if (strall.equals("90%") || strall.equals("100%")){
            ratingBar.setNumStars(5);
        }else {
            ratingBar.setNumStars(5);
        }

        time.setText(strtime);
        health.setText("健康指数:"+strhealth);
        color.setText("幸运颜色:"+strcolor);
        love.setText("爱情指数:"+strlove);
        supei.setText("速配星座:"+strsupei);
        summary.setText("短评:"+strsummary);
        study.setText("事业学业:"+strstudy);
        number.setText("幸运颜色:"+strnumber);
        money.setText("财富指数:"+strmoney);

    }
}
