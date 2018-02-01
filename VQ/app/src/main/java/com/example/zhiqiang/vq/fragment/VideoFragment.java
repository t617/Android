package com.example.zhiqiang.vq.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhiqiang.vq.adapter.MyInnerFPAdapter;
import com.example.zhiqiang.vq.R;
import com.example.zhiqiang.vq.fragment.InnerVideoPager.HotLiving;
import com.example.zhiqiang.vq.fragment.InnerVideoPager.HotTV;
import com.example.zhiqiang.vq.fragment.InnerVideoPager.HotVideo;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

public class VideoFragment extends Fragment implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private View view;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private MyInnerFPAdapter viewPagerAdapter;
    private String[] titles = new String[] {"热门视频","热门直播" ,"网络电视"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        bindView();
        initFragment();
        return view;
    }

    public void bindView() {
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
    }
    public void initFragment() {
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for (String tab : titles){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        fragments.add(new HotVideo());
        fragments.add(new HotLiving());
        fragments.add(new HotTV());
        viewPagerAdapter = new MyInnerFPAdapter(getChildFragmentManager(), titles, fragments);//getChildFragmentManager()!!!!!!
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(listener);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setOnTabSelectedListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
//        JZVideoPlayer.releaseAllVideos();
    }
    @Override
    public void onDestroy() {
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {
        JZVideoPlayer.releaseAllVideos();
    }
    @Override
    public void onPageScrollStateChanged(int state) {}
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
