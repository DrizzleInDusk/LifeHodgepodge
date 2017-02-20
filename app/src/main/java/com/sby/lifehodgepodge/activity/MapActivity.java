package com.sby.lifehodgepodge.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.sby.lifehodgepodge.MyApplication;
import com.sby.lifehodgepodge.R;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private MapView mMapView;
    private BaiduMap baiduMap;
    private boolean isFirst=false;
    private LatLng latLng;
    private ArrayList<String> permissionArr=new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //声明LocationClient类
        mLocationClient = new LocationClient(MyApplication.getContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(MyApplication.getContext());
        setContentView(R.layout.activity_map);
        mMapView= (MapView) findViewById(R.id.bmapView);
        floatingActionButton= (FloatingActionButton) findViewById(R.id.map_floatbutton);

        baiduMap=mMapView.getMap();//这个类对象可以对地图进行各种设置
        baiduMap.setMyLocationEnabled(true);//允许显示图层位置，这个不开启没法定位

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionArr.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionArr.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionArr.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionArr.size()<=0){
            startMap();
        }
        else {
            ActivityCompat.requestPermissions
                    (this, permissionArr.toArray(new String[permissionArr.size()]),1);
        }

    }

    private void startMap(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity.this, "定位成功!", Toast.LENGTH_SHORT).show();
                isFirst=false;
                moveMyLocation();
            }
        });

        initlocation();
        clearLogo();
    }


    private void initlocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setScanSpan(1000);//请求时间间隔
        option.setIsNeedAddress(true);//返回结果包含地址信息
        option.setNeedDeviceDirect(true);//请求结果是否包含方向
        mLocationClient.setLocOption(option);
    }

    private void clearLogo(){
        // 隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mMapView.showScaleControl(true);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
    }

    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            baiduMap.clear();

            double mylatitude=bdLocation.getLatitude();
            double mylongitude=bdLocation.getLongitude();

            MyLocationData.Builder databuilder=new MyLocationData.Builder();
            MyLocationData data=databuilder.latitude(mylatitude).longitude(mylongitude).
                    build();//设置纬度和经度
            baiduMap.setMyLocationData(data);//设置自己位置的小圆点

//            DotOptions options=new DotOptions();
//            LatLng latLng=new LatLng(mylatitude,mylongitude);
//            options.center(latLng);
//            options.radius(150);
//            options.color(R.color.cardview_light_background);
//            baiduMap.addOverlay(options);

            CircleOptions options=new CircleOptions();
            latLng=new LatLng(mylatitude,mylongitude);
            options.center(latLng);
            options.radius(1000);
            options.fillColor(R.color.cardview_light_background);
            baiduMap.addOverlay(options);
            moveMyLocation();
        }
    }

    private void moveMyLocation(){
        if (!isFirst){
            MapStatusUpdate factory= MapStatusUpdateFactory.newLatLngZoom(latLng,14);
            baiduMap.animateMapStatus(factory);
            isFirst=true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result:grantResults){
            if (result!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "您拒绝了权限，位置服务无法开启", Toast.LENGTH_SHORT).show();
                finish();
//                Snackbar.make(floatingActionButton,"您拒绝了权限，位置服务无法开启",0)
//                        .setAction("去开启？", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(new Intent(MyApplication.getContext(),
//                                        MapActivity.class));
//                            }
//                        }).show();
            }
        }
        startMap();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(myListener);
        baiduMap.setMyLocationEnabled(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mLocationClient.start();
        mMapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
