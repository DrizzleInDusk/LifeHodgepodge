package com.sby.lifehodgepodge.empty.xingzuo;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/2/18.
 */

public class XZRecyclerEmpty implements Serializable{
    private int imgid;
    private String text;

    public XZRecyclerEmpty(int imgid, String text) {
        this.imgid = imgid;
        this.text = text;
    }

    public int getImgid() {
        return imgid;
    }

    public String getText() {
        return text;
    }
}
