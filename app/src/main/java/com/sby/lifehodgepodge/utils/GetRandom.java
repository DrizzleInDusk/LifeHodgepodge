package com.sby.lifehodgepodge.utils;

import java.util.Random;

/**
 * Created by Lenovo on 2017/2/14.
 */

public class GetRandom {
    private int i;
    private int value;

    public GetRandom(int i) {
        Random random=new Random();
        value=random.nextInt(i);
    }

    public int getI() {
        return value;
    }
}
