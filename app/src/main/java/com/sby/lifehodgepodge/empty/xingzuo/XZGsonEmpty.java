package com.sby.lifehodgepodge.empty.xingzuo;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/2/19.
 */

public class XZGsonEmpty implements Serializable{
    private String datetime,all,color,health,love,money,number,QFriend,summary,work,resultcode;
    private int error_code;

    public XZGsonEmpty(String datetime, String all, String color,
                       String health, String love, String money,
                       String number, String QFriend, String summary,
                       String work, String resultcode, int error_code) {
        this.datetime = datetime;
        this.all = all;
        this.color = color;
        this.health = health;
        this.love = love;
        this.money = money;
        this.number = number;
        this.QFriend = QFriend;
        this.summary = summary;
        this.work = work;
        this.resultcode = resultcode;
        this.error_code = error_code;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getAll() {
        return all;
    }

    public String getColor() {
        return color;
    }

    public String getHealth() {
        return health;
    }

    public String getLove() {
        return love;
    }

    public String getMoney() {
        return money;
    }

    public String getNumber() {
        return number;
    }

    public String getQFriend() {
        return QFriend;
    }

    public String getSummary() {
        return summary;
    }

    public String getWork() {
        return work;
    }

    public String getResultcode() {
        return resultcode;
    }

    public int getError_code() {
        return error_code;
    }
}
