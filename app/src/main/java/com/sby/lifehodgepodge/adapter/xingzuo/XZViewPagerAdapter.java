package com.sby.lifehodgepodge.adapter.xingzuo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/2/19.
 */

public class XZViewPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> arrayList;

    public XZViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
