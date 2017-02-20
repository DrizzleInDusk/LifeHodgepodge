package com.sby.lifehodgepodge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.empty.xingzuo.XZGsonEmpty2;

/**
 * Created by Lenovo on 2017/2/19.
 */

public class Fragment_week extends Fragment{
    private TextView time,health,job,love,money,work;
    private String strtime,strhealth,strjob,strlove,strwork,strmoney;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_week,container,false);
        time= (TextView) view.findViewById(R.id.xz_frag3_time);
        health= (TextView) view.findViewById(R.id.xz_frag3_health);
        job= (TextView) view.findViewById(R.id.xz_frag3_job);
        love= (TextView) view.findViewById(R.id.xz_frag3_love);
        work= (TextView) view.findViewById(R.id.xz_frag3_work);
        money= (TextView) view.findViewById(R.id.xz_frag3_money);
        return view;
    }

    public void setValue(XZGsonEmpty2 empty){
        strtime=empty.getDate();
        strhealth=empty.getHealth();
        strjob=empty.getJob();
        strlove=empty.getLove();
        strwork=empty.getWork();
        strmoney=empty.getMoney();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        time.setText(strtime);
        health.setText(strhealth.substring(0,strhealth.length()-14));
        job.setText(strjob);
        love.setText(strlove);
        work.setText(strwork);
        money.setText(strmoney);
    }
}
