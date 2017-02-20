package com.sby.lifehodgepodge.empty.weixin;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/2/15.
 */

public class WXGsonEmpty implements Serializable{
    private String title;
    private String source;
    private String firstImg;
    private String url;

    public String getTitle() {
        return title;
    }


    public String getSoucre() {
        return source;
    }



    public String getFirstImg() {
        return firstImg;
    }


    public String getUrl() {
        return url;
    }


}
