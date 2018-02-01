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
import com.example.zhiqiang.vq.entity.Lives;
import com.example.zhiqiang.vq.http.HttpUtils;

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
    private static final String url = "http://service.inke.com/api/live/simpleall?&gender=1" +
            "&gps_info=116.346844,40.090467&loc_info=CN,%E5%8C%97%E4%BA%AC%E5%B8%82,%E5%8C" +
            "%97%E4%BA%AC%E5%B8%82&is_new_user=1&lc=0000000000000053&cc=TG0001&cv=IK4." +
            "0.30_Iphone&proto=7&idfa=D7D0D5A2-3073-4A74-A726-98BE8B4E8F38&idfv=58A18E13-A2" +
            "1D-456D-B6D8-7499948B379D&devi=54b68af1895085419f7f8978d95d95257dd44f93&osvers" +
            "ion=ios_10.300000&ua=iPhone6_2&imei=&imsi=&uid=450515766&sid=20XNNoa5VwMozGALfm" +
            "i2xN1YCfLWvEq7aJuTHTQLu8bT88i1aNbi0&conn=wifi&mtid=391bb3520c38e0444ba0b3975f4bb" +
            "1aa&mtxid=f0b42913a33c&logid=162,210&s_sg=3111b3a0092d652ab3bcb218099968de&s_sc=100&s_st=1492954889";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hot_living, container, false);
        bindView();
        mLivesList = new ArrayList<>();
        mAdapter = new LivesAdapter(getActivity(), mLivesList);
        mGvLivingList.setAdapter(mAdapter);
        mGvLivingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("stream_addr", mLivesList.get(position).getStream_addr());
                intent.putExtra("description", mLivesList.get(position).getCreator().getDescription());
                intent.putExtra("portrait", mLivesList.get(position).getCreator().getPortrait());
                intent.putExtra("type", "living");
                startActivity(intent);
            }
        });
        new RequestLivingTask().execute(url);
        return view;
    }
    public void bindView() {
        mGvLivingList = (GridView) view.findViewById(R.id.gvLivingList);
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
            mLivesList.addAll(lives);
            mAdapter.notifyDataSetChanged();
//            mProgressDialog.dismiss();
        }
    }
}