package com.sby.lifehodgepodge.utils;

/**
 * Created by Lenovo on 2017/2/13.
 */

public class AllUrlUtil {

    //单例模式
    private static AllUrlUtil allUrlUtil;

    private AllUrlUtil() {
    }

    public static AllUrlUtil AllUrlUtil() {
        if (allUrlUtil==null){
            allUrlUtil=new AllUrlUtil();
        }
        return allUrlUtil;
    }

    //笑话大全的url
    public String getUrlXiaohua(int page){
        long systemtime=Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10));
        String key="656abe47b0dd2987aa83e7ae7d30d61b";
        return "http://japi.juhe.cn/joke/content/list.from?key="+key+
                "&sort=desc&pagesize=20&page="+page+"&time="+systemtime;
    }

    //微信精选的url
    public String getUrlWeixin(int page){
        String key="75f127bdd54ea95ae1202f00bb0509ae";
        return "http://v.juhe.cn/weixin/query?key="+key+"&pno="+page;
    }

    //星座查询
    public String getUrlXingZuo(String consName,String type){
        String key="e4854ed53dd5834a8b7279fec98156b3";
        return "http://web.juhe.cn:8080/constellation/getAll?" +
                "consName="+consName+"&type="+type+"&key="+key;
    }

}
