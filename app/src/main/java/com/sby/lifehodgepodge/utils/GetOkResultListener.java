package com.sby.lifehodgepodge.utils;

import com.sby.lifehodgepodge.activity.XZItemActivity;
import com.sby.lifehodgepodge.activity.XiaohuaActivity;
import com.sby.lifehodgepodge.myinterface.getOkResult;

import static com.sby.lifehodgepodge.activity.WeixinActivity.getWeixinActivity;

/**
 * Created by Lenovo on 2017/2/14.
 */

public class GetOkResultListener implements getOkResult{
    private String result=null;


    @Override
    public void setresult(String result, int name) {
        switch (name){
            case 1: XiaohuaActivity.getXhActivity().getArraylist(result);
                break;
            case 2: getWeixinActivity().getArraylist(result);
                break;
            case 3: XZItemActivity.getXZItem().getArraylist(result);
                break;
        }
    }


}
