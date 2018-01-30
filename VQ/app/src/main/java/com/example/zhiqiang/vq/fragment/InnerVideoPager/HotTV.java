package com.example.zhiqiang.vq.fragment.InnerVideoPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.zhiqiang.vq.R;
import com.example.zhiqiang.vq.VideoActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhiqiang on 2018/1/30.
 */

public class HotTV extends Fragment {
    private View view;
    private ListView mLivingList;
    private SimpleAdapter mAdapter;
    private List<Map<String,Object>> mLivesList;
    private static final String[] urls = {"http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8",
            "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8", "http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        String mVideoPath = "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8";
//        String mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
//        String mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8";
//        String mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8";
        view = inflater.inflate(R.layout.activity_hot_tv, container, false);
        bindView();
        mLivesList = new ArrayList<>();
        initData();
        mAdapter = new SimpleAdapter(getContext(), mLivesList, R.layout.adapter_lives, new String[]
                {"nameAndCity", "description", "portrait"}, new int[]{R.id.tvNameAndCity, R.id.description, R.id.ivPortrait});
        mLivingList.setAdapter(mAdapter);
        mLivingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("stream_addr", urls[position]);
                intent.putExtra("description", (String) mLivesList.get(position).get("description"));
                intent.putExtra("portrait", (String) mLivesList.get(position).get("portrait"));
                startActivity(intent);
            }
        });
        return view;
    }
    public void initData() {
        Map<String, Object> listItem = new LinkedHashMap<>();
        listItem.put("nameAndCity", "");
        listItem.put("description", "CCTV1");
        listItem.put("portrait", "http://img2.inke.cn/MTUxNzMxODUwMjgzNyMxMzQjanBn.jpg");
        mLivesList.add(listItem);
        Map<String, Object> listItem1 = new LinkedHashMap<>();
        listItem.put("nameAndCity", "");
        listItem.put("description", "CCTV5");
        listItem.put("portrait", "http://img2.inke.cn/MTUxNzMxODUwMjgzNyMxMzQjanBn.jpg");
        mLivesList.add(listItem1);
        Map<String, Object> listItem2 = new LinkedHashMap<>();
        listItem.put("nameAndCity", "");
        listItem.put("description", "CCTV6");
        listItem.put("portrait", "http://img2.inke.cn/MTUxNzMxODUwMjgzNyMxMzQjanBn.jpg");
        mLivesList.add(listItem2);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void bindView() {
        mLivingList = (ListView) view.findViewById(R.id.list_view);
    }
}
