package com.example.zhiqiang.vq.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.zhiqiang.vq.MainActivity;
import com.example.zhiqiang.vq.fragment.MainFragment;
import com.example.zhiqiang.vq.fragment.NoticeFragment;
import com.example.zhiqiang.vq.fragment.VideoFragment;

/**
 * Created by zhiqiang on 2018/1/28.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    private MainFragment mainFragment = null;
    private NoticeFragment noticeFragment = null;
    private VideoFragment videoFragment = null;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mainFragment = new MainFragment();
        noticeFragment = new NoticeFragment();
        videoFragment = new VideoFragment();
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = mainFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = videoFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = noticeFragment;
                break;
        }
        return fragment;
    }
}
