package com.sby.lifehodgepodge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.adapter.xingzuo.XZRecycAdapter;
import com.sby.lifehodgepodge.empty.xingzuo.XZRecyclerEmpty;

import java.util.ArrayList;

public class XingZuoActivity extends Activity {
    private ArrayList<XZRecyclerEmpty> arrayList=new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xing_zuo);
        recyclerView= (RecyclerView) findViewById(R.id.xz_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL,false));
        initarraylist();
        recyclerView.setAdapter(new XZRecycAdapter(arrayList,this));

    }

    private void initarraylist(){
        arrayList.add(new XZRecyclerEmpty(R.drawable.baiyang,"白羊座(3.21-4.19)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.jinniu,"金牛座(4.20-5.20)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.shuangzi,"双子座(5.21-6.21)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.juxie,"巨蟹座(6.22-7.22)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.shizi,"狮子座(7.23-8.22)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.chunv,"处女座(8.23-9.22)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.tianping,"天秤座(9.23-10.23)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.tianxie,"天蝎座(10.24-11.22)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.sheshou,"射手座(11.23-12.21)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.mojie,"摩羯座(12.22-1.19)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.shuiping,"水瓶座(1.20-2.18)"));
        arrayList.add(new XZRecyclerEmpty(R.drawable.shuangyu,"双鱼座(2.19-3.20)"));
    }
}
