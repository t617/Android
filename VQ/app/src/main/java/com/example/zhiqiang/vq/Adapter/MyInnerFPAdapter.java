package com.example.zhiqiang.vq.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MyInnerFPAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles;

    public MyInnerFPAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
}
