package com.sby.lifehodgepodge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.utils.GetRandom;

public class XhItemActivity extends AppCompatActivity {
    private TextView textView;
    private LinearLayout linearLayout;
    private ImageView imageView;
    private int[] img=new int[]{R.drawable.xh1l,R.drawable.xh2l,R.drawable.xh3l,R.drawable.xh4l,R.drawable.xh5l};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xh_item);
        textView= (TextView) findViewById(R.id.xh_item_tv);
        linearLayout= (LinearLayout) findViewById(R.id.activity_xh_item);
        imageView= (ImageView) findViewById(R.id.xh_item_iv);

        Intent intent=getIntent();
        String content=intent.getStringExtra("content");
        textView.setText(content);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        //设置可变背景图
        int bgid=new GetRandom(5).getI();
        imageView.setBackgroundResource(img[bgid]);
    }
}
