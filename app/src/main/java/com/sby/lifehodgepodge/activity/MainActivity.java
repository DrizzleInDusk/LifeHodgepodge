package com.sby.lifehodgepodge.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.sby.lifehodgepodge.R;

import java.util.ArrayList;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<View> arrayList=new ArrayList<>();
    private ImageView imghint;
    private static boolean isFirstCreate=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        imghint= (ImageView) findViewById(R.id.img_hint);
        arrayList.add(getLayoutInflater().inflate(R.layout.vp4,null));
        arrayList.add(getLayoutInflater().inflate(R.layout.vp3,null));
        arrayList.add(getLayoutInflater().inflate(R.layout.vp1,null));
        arrayList.add(getLayoutInflater().inflate(R.layout.vp2,null));

        startinit();
    }

    private void startinit(){
        if (isFirstCreate){
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("警告")
                    .setMessage("本软件需要联网才可正常使用，请确认您已开启数据流量或连接到wifi")
                    .setPositiveButton("我已开启网络", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .show();
            isFirstCreate=false;
        }


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(arrayList.get(position));
                return arrayList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(arrayList.get(position));
            }

        });
        viewPager.setPageTransformer(true,new CubeTransformer());


        //半透明提示
        new MaterialShowcaseView.Builder(MainActivity.this)
                .setTarget(imghint)
                .setDismissText("点此关闭")
                .setContentText("还可以向右滑动寻找更多乐趣呦~")
                .singleUse("id") // provide a unique ID used to ensure it is only shown once
                .show();
        imghint.setVisibility(View.GONE);

    }


    //viewpager动画效果（copy）
    private class CubeTransformer implements ViewPager.PageTransformer{
        private static final float ROT_MAX = 20.0f;
        private float mRot;
        @Override
        public void transformPage(View view, float position) {
            //正方体，失败了
//            if (position <= 0) {
//                //从右向左滑动为当前View
//                //设置旋转中心点；
//                ViewHelper.setPivotX(page, page.getMeasuredWidth());
//                ViewHelper.setPivotY(page, page.getMeasuredHeight() * 0.5f);
//
//                //只在Y轴做旋转操作
//                ViewHelper.setRotationY(page, 90f * position);
//
//
//            } else if (position <= 1) {
//                //从左向右滑动为当前View
//                ViewHelper.setPivotX(page, 0);
//                ViewHelper.setPivotY(page, page.getMeasuredHeight() * 0.5f);
//                ViewHelper.setRotationY(page, 90f * position);
//
//            }

            if (position < -1)
            {
                ViewHelper.setRotation(view, 0);

            } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
            {
                if (position < 0)
                {
                    mRot = (ROT_MAX * position);
                    ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(view, view.getMeasuredHeight());
                    ViewHelper.setRotation(view, mRot);
                } else
                {
                    mRot = (ROT_MAX * position);
                    ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(view, view.getMeasuredHeight());
                    ViewHelper.setRotation(view, mRot);
                }

            } else
            {
                ViewHelper.setRotation(view, 0);
            }
        }
    }

    public void myClick(View view){
        switch (view.getId()){
            case R.id.main_startWX:
                startActivity(new Intent(this, WeixinActivity.class));
                break;
            case R.id.main_startXZ:
                startActivity(new Intent(this,XingZuoActivity.class));
                break;
            case R.id.main_startMap:
                startActivity(new Intent(MainActivity.this,MapActivity.class));
                break;
            case R.id.main_startXH:
                startActivity(new Intent(this,XiaohuaActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("退出应用")
                .setCancelable(true)
                .setMessage("您要退出娱乐大杂烩吗?")
                .setNegativeButton("按错了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}
