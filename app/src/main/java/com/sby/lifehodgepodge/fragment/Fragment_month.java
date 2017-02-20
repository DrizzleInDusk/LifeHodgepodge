package com.sby.lifehodgepodge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.empty.xingzuo.XZGsonEmpty3;

/**
 * Created by Lenovo on 2017/2/19.
 */

public class Fragment_month extends Fragment{
    private TextView time,health,all,love,money,work;
    private String strtime,strhealth,strall,strlove,strwork,strmoney;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_month,container,false);
        time= (TextView) view.findViewById(R.id.xz_frag4_time);
        health= (TextView) view.findViewById(R.id.xz_frag4_health);
        all= (TextView) view.findViewById(R.id.xz_frag4_all);
        love= (TextView) view.findViewById(R.id.xz_frag4_love);
        work= (TextView) view.findViewById(R.id.xz_frag4_work);
        money= (TextView) view.findViewById(R.id.xz_frag4_money);
        return view;
    }

    public void setValue(XZGsonEmpty3 empty){
        strtime=empty.getDate();
        strhealth=empty.getHealth();
        strall=empty.getAll();
        strlove=empty.getLove();
        strwork=empty.getWork();
        strmoney=empty.getMoney();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        health.setText("健康指数:"+strhealth);
        all.setText("总体运势:"+strall);
        love.setText("爱情指数:"+strlove);
        work.setText("事业学业:"+strwork);
        money.setText("财富指数:"+strmoney);
        time.setText(strtime);
    }
}
