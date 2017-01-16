package com.example.beyondsys.ppv.tools;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by zhsht on 2017/1/12.View视图适配器
 */
public class PageAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public PageAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> list) {
        super(supportFragmentManager);
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }
}
