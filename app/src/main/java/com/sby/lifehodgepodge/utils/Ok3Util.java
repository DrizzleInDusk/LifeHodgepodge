package com.sby.lifehodgepodge.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lenovo on 2017/2/13.
 */

public class Ok3Util {
    private GetOkResultListener listener=new GetOkResultListener();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String result= (String) msg.obj;
                    int name= msg.arg1;
                    listener.setresult(result,name);
                break;
            }
        }
    };
    //单例
    private static Ok3Util util;
    private Ok3Util(){
    }

    public static Ok3Util getok3util(){
        if (util==null){
            util=new Ok3Util();
        }
        return util;
    }

    //根据url返回一个json类型的字符串
    public void startOK3(final String url, final int name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(url).build();
                try {
                    Response response=client.newCall(request).execute();
                    if (response.isSuccessful()){
                        String result=response.body().string();
                        Message message=new Message();
                        message.obj=result;
                        message.arg1=name;
                        message.what=1;
                        handler.sendMessage(message);
                    }else {
                        Log.i("ok3", "请求失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
