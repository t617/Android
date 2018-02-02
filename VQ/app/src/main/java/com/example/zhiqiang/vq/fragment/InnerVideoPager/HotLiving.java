package com.example.zhiqiang.vq.fragment.InnerVideoPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.zhiqiang.vq.adapter.LivesAdapter;
import com.example.zhiqiang.vq.R;
import com.example.zhiqiang.vq.VideoActivity;
import com.example.zhiqiang.vq.constant.LivesConstant;
import com.example.zhiqiang.vq.entity.Lives;
import com.example.zhiqiang.vq.http.HttpUtils;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotLiving extends Fragment {
    private View view;
    private GridView mGvLivingList;
    private LivesAdapter mAdapter;
    private List<Lives> mLivesList;
    private List<Lives> mLivesListTotal;
    private RefreshLayout refreshLayout;
    private static int num = 20;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hot_living, container, false);
        bindView();
        mLivesList = new ArrayList<>();
        mLivesListTotal = new ArrayList<>();
        mAdapter = new LivesAdapter(getActivity(), mLivesList);
        mGvLivingList.setAdapter(mAdapter);
        setListener();
        new RequestLivingTask().execute(LivesConstant.url);
        return view;
    }
    public void bindView() {
        mGvLivingList = (GridView) view.findViewById(R.id.gvLivingList);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
    }
    public void setListener() {
        mGvLivingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("stream_addr", mLivesList.get(position).getStream_addr());
                intent.putExtra("description", mLivesList.get(position).getCreator().getDescription());
                intent.putExtra("portrait", mLivesList.get(position).getCreator().getPortrait());
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new RequestLivingTask().execute(LivesConstant.url);
                refreshlayout.finishRefresh(1500);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                for (int i = num; i < num + 20 && mLivesListTotal.size() >= num; i++) {
                    if (mLivesListTotal.size() >= num + 20)
                        mLivesList.add(mLivesListTotal.get(i));
                }
                num += 20;
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(1500);
            }
        });
    }
    private class RequestLivingTask extends AsyncTask<String,Void,List<Lives>> {
        ProgressDialog mProgressDialog ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog = ProgressDialog.show(getContext(), "加载中……",null,false);
        }
        @Override
        protected List<Lives> doInBackground(String... params) {
            String url = params[0];
            List<Lives> liveList = new ArrayList<>();
            try {
                String livingList = HttpUtils.getInstance().get(url);
                JSONObject livingObj = new JSONObject(livingList);
                JSONArray livesArr = livingObj.optJSONArray("lives");
                for (int i = 0; i < livesArr.length(); i++) {
                    JSONObject live = livesArr.optJSONObject(i);
                    Lives lives = new Lives(live);
                    liveList.add(lives);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return liveList;
        }

        @Override
        protected void onPostExecute(List<Lives> lives) {
            super.onPostExecute(lives);
            mLivesListTotal.addAll(lives);
            num = 20;
            if (lives.size() < 20) {
                num = lives.size();
            }
            for (int i = 0; i < num; i++) {
                mLivesList.add(lives.get(i));
            }
//            mLivesList.addAll(lives);
            mAdapter.notifyDataSetChanged();
//            mProgressDialog.dismiss();
        }
    }
}