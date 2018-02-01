package com.example.zhiqiang.vq.fragment.InnerVideoPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.zhiqiang.vq.R;
import com.example.zhiqiang.vq.VideoActivity;
import com.example.zhiqiang.vq.adapter.MyBaseExpandableListAdapter;
import com.example.zhiqiang.vq.constant.TvLivesConstant;
import com.example.zhiqiang.vq.entity.tvLives;
import com.example.zhiqiang.vq.entity.tvLivesGroup;

import java.util.ArrayList;

/**
 * Created by zhiqiang on 2018/1/30.
 */

public class HotTV extends Fragment {
    private View view;
//    private ListView mLivingList;
//    private SimpleAdapter mAdapter;
//    private List<Map<String,Object>> mLivesList;
    private ArrayList<tvLivesGroup> gData = null;
    private ArrayList<ArrayList<tvLives>> iData = null;
    private ArrayList<tvLives> data = null;
    private ExpandableListView expandListTv;
    private MyBaseExpandableListAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hot_tv, container, false);
        bindView();
        initData();
        setMyAdapter();
        return view;
    }
    public void initData() {
        gData = new ArrayList<tvLivesGroup>();
        iData = new ArrayList<ArrayList<tvLives>>();

        for (String type : TvLivesConstant.tvType) {
            gData.add(new tvLivesGroup(type));
        }
        data = new ArrayList<tvLives>();
        for (String[] tvNameAndUrl : TvLivesConstant.tvNameAndUrl1) {
            data.add(new tvLives(tvNameAndUrl[0], tvNameAndUrl[1]));
        }
        iData.add(data);
        data = new ArrayList<tvLives>();
        for (String[] tvNameAndUrl : TvLivesConstant.tvNameAndUrl2) {
            data.add(new tvLives(tvNameAndUrl[0], tvNameAndUrl[1]));
        }
        iData.add(data);
        data = new ArrayList<tvLives>();
        for (String[] tvNameAndUrl : TvLivesConstant.tvNameAndUrl3) {
            data.add(new tvLives(tvNameAndUrl[0], tvNameAndUrl[1]));
        }
        iData.add(data);
        data = new ArrayList<tvLives>();
        for (String[] tvNameAndUrl : TvLivesConstant.tvNameAndUrl4) {
            data.add(new tvLives(tvNameAndUrl[0], tvNameAndUrl[1]));
        }
        iData.add(data);
        data = new ArrayList<tvLives>();
        for (String[] tvNameAndUrl : TvLivesConstant.tvNameAndUrl5) {
            data.add(new tvLives(tvNameAndUrl[0], tvNameAndUrl[1]));
        }
        iData.add(data);
    }
    public void setMyAdapter() {
        mAdapter = new MyBaseExpandableListAdapter(gData, iData, getContext());
        expandListTv.setAdapter(mAdapter);
        expandListTv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("stream_addr", iData.get(groupPosition).get(childPosition).getUrlAddr());
                intent.putExtra("description", iData.get(groupPosition).get(childPosition).getTvName());
                startActivity(intent);
//                Toast.makeText(getContext(), "你点击了：" + iData.get(groupPosition).get(childPosition).getUrlAddr(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void bindView() {
        expandListTv = (ExpandableListView) view.findViewById(R.id.expand_list_tv);
    }
}
