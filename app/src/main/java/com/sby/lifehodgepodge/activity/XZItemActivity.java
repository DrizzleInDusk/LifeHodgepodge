package com.sby.lifehodgepodge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sby.lifehodgepodge.R;
import com.sby.lifehodgepodge.adapter.xingzuo.XZViewPagerAdapter;
import com.sby.lifehodgepodge.empty.xingzuo.XZGsonEmpty;
import com.sby.lifehodgepodge.empty.xingzuo.XZGsonEmpty2;
import com.sby.lifehodgepodge.empty.xingzuo.XZGsonEmpty3;
import com.sby.lifehodgepodge.fragment.Fragment_month;
import com.sby.lifehodgepodge.fragment.Fragment_today;
import com.sby.lifehodgepodge.fragment.Fragment_tomorrow;
import com.sby.lifehodgepodge.fragment.Fragment_week;
import com.sby.lifehodgepodge.utils.AllUrlUtil;
import com.sby.lifehodgepodge.utils.GsonUtil;
import com.sby.lifehodgepodge.utils.IsNetWork;
import com.sby.lifehodgepodge.utils.Ok3Util;

import java.util.ArrayList;

public class XZItemActivity extends FragmentActivity {
    private ImageView imageView,img_hx;
    private FloatingActionButton floatingActionButton;
    private ViewPager viewPager;
    private ArrayList<Fragment> arrayList=new ArrayList<>();
    private TextView tv_today,tv_tomorrow,tv_week,tv_month;
    private XZViewPagerAdapter viewPagerAdapter;
    private int screenWidth;
    private static int requesttype=0;
    private Fragment_today today=new Fragment_today();
    private Fragment_week week=new Fragment_week();
    private Fragment_month month=new Fragment_month();
    private Fragment_tomorrow tomorrow=new Fragment_tomorrow();
    private String intentName;
    private static boolean isCreate=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xz_item);
        imageView= (ImageView) findViewById(R.id.xz_actitem_iv);
        viewPager= (ViewPager) findViewById(R.id.xz_item_viewpager);
        floatingActionButton= (FloatingActionButton) findViewById(R.id.xz_actitem_floatbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XZItemActivity.this,XingZuoActivity.class));
            }
        });
        //注意，引入布局之后不用先获得itemview 再itemview.findviewbyid  直接findviewbyid就行
        img_hx= (ImageView) findViewById(R.id.xz_viewpager_item_iv);
        tv_today= (TextView) findViewById(R.id.xz_viewpager_item_today);
        tv_tomorrow= (TextView)findViewById(R.id.xz_viewpager_item_tomorrow);
        tv_week= (TextView) findViewById(R.id.xz_viewpager_item_week);
        tv_month= (TextView) findViewById(R.id.xz_viewpager_item_month);

        tv_today.setOnClickListener(new MyClickListener(0));
        tv_tomorrow.setOnClickListener(new MyClickListener(1));
        tv_week.setOnClickListener(new MyClickListener(2));
        tv_month.setOnClickListener(new MyClickListener(3));

        setImgBackground();

        if (IsNetWork.isNetworkAvailable(this)){
            //today为0，womorrow为1，week为2，month为3
            requesttype=0;
            isCreate=true;
            startRequest(intentName,"today");
        }
        else {
            viewPager.setBackgroundResource(R.drawable.error);
        }


        //设置底下的绿线
        initLine();
        //设置滑动事件
        viewPager.setOnPageChangeListener(new MyPageChange());

    }

    //自己的监听接口，点击不同类型可直接跳转
    private class MyClickListener implements View.OnClickListener{
        private  int item=0;

        public MyClickListener(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
//            isCreate=false;
//            switch (item){
//                case 0:
//                    requesttype=0;
//                    startRequest(intentName,"today");
//                    break;
//                case 1:
//                    requesttype=1;
//                    startRequest(intentName,"tomorrow");
//                    break;
//                case 2:
//                    requesttype=2;
//                    startRequest(intentName,"week");
//                    break;
//                case 3:
//                    requesttype=3;
//                    startRequest(intentName,"month");
//                    break;
//            }
            viewPager.setCurrentItem(item);
        }
    }

    //自定义滑动监听
    private class MyPageChange implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) img_hx.getLayoutParams();
            //返回组件距离左侧组件的距离
            lp.leftMargin= (int) ((positionOffset+position)*screenWidth/4);
            img_hx.setLayoutParams(lp);
//            //改变状态
//            isCreate=false;
//            switch (position){
//                case 0:
//                    requesttype=0;
//                    startRequest(intentName,"today");
//                    break;
//                case 1:
//                    requesttype=1;
//                    startRequest(intentName,"tomorrow");
//                    break;
//                case 2:
//                    requesttype=2;
//                    startRequest(intentName,"week");
//                    break;
//                case 3:
//                    requesttype=3;
//                    startRequest(intentName,"month");
//                    break;
//            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    //把fragment添加进viewpager的arraylist
    private void initArrayList(){

        arrayList.add(today);
        arrayList.add(tomorrow);
        arrayList.add(week);
        arrayList.add(month);

    }
    //开始请求
    private void startRequest(String consName,String type){
        String url=AllUrlUtil.AllUrlUtil().getUrlXingZuo(consName,type);
        Ok3Util.getok3util().startOK3(url,3);
    }

    //请求类型不同，对应的实体类也不同，要分开获取
    public void getArraylist(String result){
        switch (requesttype){
            case 0:
                XZGsonEmpty empty0=GsonUtil.getGsonUtil().startGsonToClass(result, XZGsonEmpty.class);
                if (empty0.getError_code()==0 && empty0.getResultcode().equals("200")){
                    today.setValue(empty0);
                }
                //解析完这个就开始下一个请求
                requesttype=1;
                startRequest(intentName,"tomorrow");
                break;
            case 1:
                XZGsonEmpty empty1=GsonUtil.getGsonUtil().startGsonToClass(result, XZGsonEmpty.class);
                if (empty1.getError_code()==0 && empty1.getResultcode().equals("200")){
                    tomorrow.setValue(empty1);
                }
                requesttype=2;
                startRequest(intentName,"week");
                break;
            case 2:
                XZGsonEmpty2 empty2=GsonUtil.getGsonUtil().startGsonToClass(result, XZGsonEmpty2.class);
                if (empty2.getError_code()==0 && empty2.getResultcode().equals("200")){
                    week.setValue(empty2);
                }
                requesttype=3;
                startRequest(intentName,"month");
                break;
            case 3:
                XZGsonEmpty3 empty3=GsonUtil.getGsonUtil().startGsonToClass(result, XZGsonEmpty3.class);
                if (empty3.getError_code()==0 && empty3.getResultcode().equals("200")){
                    month.setValue(empty3);
                }
                requesttype=4;
                break;

        }
        //全部请求完再添加
        if (requesttype==4){
            initArrayList();
            if (isCreate){
                viewPagerAdapter=new XZViewPagerAdapter(getSupportFragmentManager(),arrayList);
                viewPager.setAdapter(viewPagerAdapter);
            }else {
                if (viewPagerAdapter!=null){
                    viewPagerAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    //修改底下绿线的位置
    private void initLine(){
        //获取屏幕的宽度
        DisplayMetrics outMetrics=new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth=outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) img_hx.getLayoutParams();
        lp.width=screenWidth/4;//设置该控件的layoutParams参数
        img_hx.setLayoutParams(lp);//将修改好的layoutParams设置为该控件的layoutParams

    }

    //选择不同星座进入不同背景图片
    private void setImgBackground(){
        Intent intent=getIntent();
        intentName=intent.getStringExtra("xz");
        if (intentName.equals("白羊座")){
            imageView.setImageResource(R.drawable.topbaiyang);
        }else if (intentName.equals("金牛座")){
            imageView.setImageResource(R.drawable.topjinniu);
        }else if (intentName.equals("双子座")){
            imageView.setImageResource(R.drawable.topshuangzi);
        }else if (intentName.equals("巨蟹座")){
            imageView.setImageResource(R.drawable.topjuxie);
        }else if (intentName.equals("狮子座")){
            imageView.setImageResource(R.drawable.topshizi);
        }else if (intentName.equals("处女座")){
            imageView.setImageResource(R.drawable.topchunv);
        }else if (intentName.equals("天秤座")){
            imageView.setImageResource(R.drawable.toptianping);
        }else if (intentName.equals("天蝎座")){
            imageView.setImageResource(R.drawable.toptianxie);
        }else if (intentName.equals("射手座")){
            imageView.setImageResource(R.drawable.topsheshou);
        }else if (intentName.equals("摩羯座")){
            imageView.setImageResource(R.drawable.topmojie);
        }else if (intentName.equals("水瓶座")){
            imageView.setImageResource(R.drawable.topshuiping);
        }else if (intentName.equals("双鱼座")){
            imageView.setImageResource(R.drawable.topshuangyu);
        }

    }

    //获取此act实例
    private static XZItemActivity xzItemActivity;
    public XZItemActivity() {
        xzItemActivity=this;
    }
    public static XZItemActivity getXZItem(){
        return xzItemActivity;
    }
}
