package com.sby.lifehodgepodge.empty.weixin;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/2/15.
 */

public class WXRecyclerEmpty implements Serializable{
    private String title;
    private String source;
    private String firstImg;
    private String url;

    public WXRecyclerEmpty(String title, String source, String firstImg, String url) {
        this.title = title;
        this.source = source;
        this.firstImg = firstImg;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public String getUrl() {
        return url;
    }
}
