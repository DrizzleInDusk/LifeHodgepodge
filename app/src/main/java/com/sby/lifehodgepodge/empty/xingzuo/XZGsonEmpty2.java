package com.sby.lifehodgepodge.empty.xingzuo;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017/2/19.
 */

public class XZGsonEmpty2 implements Serializable{
    private String date,health,job,love,money,work,resultcode;
    private int error_code;

    public XZGsonEmpty2(String date, String health, String job,
                        String love, String money, String work,
                        String resultcode, int error_code) {
        this.date = date;
        this.health = health;
        this.job = job;
        this.love = love;
        this.money = money;
        this.work = work;
        this.resultcode = resultcode;
        this.error_code = error_code;
    }

    public String getDate() {
        return date;
    }

    public String getHealth() {
        return health;
    }

    public String getJob() {
        return job;
    }

    public String getLove() {
        return love;
    }

    public String getMoney() {
        return money;
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
