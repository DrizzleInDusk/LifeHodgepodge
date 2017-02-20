package com.sby.lifehodgepodge.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/2/13.
 */

public class GsonUtil {

    //单例
    private static GsonUtil gsonUtil;
    private GsonUtil(){}
    public static GsonUtil getGsonUtil(){
        if (gsonUtil==null){
            gsonUtil=new GsonUtil();
        }
        return gsonUtil;
    }

    public <T> T startGsonToClass(String jsonbody,Class<T> cls) {
        Gson gson=new Gson();
        T t=gson.fromJson(jsonbody,cls);
        return t;
    }

    public <T> List<T> startGsonToArray(String jsonbody,Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            //泛型在编译期类型被擦除导致的解决方法
            JsonArray arry = new JsonParser().parse(jsonbody).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
