package com.sby.lifehodgepodge.empty.xiaohua;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/2/13.
 */

public class XhRecyclerEmpty implements Serializable{
    private String content;

    public XhRecyclerEmpty(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
