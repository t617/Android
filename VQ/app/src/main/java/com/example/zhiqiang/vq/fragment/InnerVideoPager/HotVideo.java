package com.example.zhiqiang.vq.fragment.InnerVideoPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.zhiqiang.vq.Adapter.AdapterVideoList;
import com.example.zhiqiang.vq.R;
import com.example.zhiqiang.vq.VideoConstant;

import cn.jzvd.JZVideoPlayer;

public class HotVideo extends Fragment {
    private View view;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hot_video, container, false);
        bindView();
        loadView();
        return view;
    }
    public void loadView() {
        listView.setAdapter(new AdapterVideoList(getActivity(),
                VideoConstant.videoUrls[0],
                VideoConstant.videoTitles[0],
                VideoConstant.videoThumbs[0]));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                JZVideoPlayer.onScrollReleaseAllVideos(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }
    public void bindView() {
        listView = (ListView) view.findViewById(R.id.list_view);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}