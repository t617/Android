package com.example.zhiqiang.living;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.zhiqiang.living.adapter.LivesAdapter;
import com.example.zhiqiang.living.entity.Lives;
import com.example.zhiqiang.living.http.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private static final String url = "http://service.inke.com/api/live/simpleall?&gender=1&gps_info=116.346844%2C40.090467&loc_info=CN%2C%E5%8C%97%E4%BA%AC%E5%B8%82%2C%E5%8C%97%E4%BA%AC%E5%B8%82&is_new_user=1&lc=0000000000000053&cc=TG0001&cv=IK4.0.30_Iphone&proto=7&idfa=D7D0D5A2-3073-4A74-A726-98BE8B4E8F38&idfv=58A18E13-A21D-456D-B6D8-7499948B379D&devi=54b68af1895085419f7f8978d95d95257dd44f93&osversion=ios_10.300000&ua=iPhone6_2&imei=&imsi=&uid=450515766&sid=20XNNoa5VwMozGALfmi2xN1YCfLWvEq7aJuTHTQLu8bT88i1aNbi0&conn=wifi&mtid=391bb3520c38e0444ba0b3975f4bb1aa&mtxid=f0b42913a33c&logid=162,210&s_sg=3111b3a0092d652ab3bcb218099968de&s_sc=100&s_st=1492954889";
private static final String url = "http://service.inke.com/api/live/simpleall?&gender=1&gps_info=116.346844,40.090467&loc_info=CN,%E7%8E%89%E6%9E%97%E5%B8%82,%E7%8E%89%E6%9E%97%E5%B8%82&is_new_user=1&lc=0000000000000053&cc=TG0001&cv=IK4.0.30_Iphone&proto=7&idfa=D7D0D5A2-3073-4A74-A726-98BE8B4E8F38&idfv=58A18E13-A21D-456D-B6D8-7499948B379D&devi=54b68af1895085419f7f8978d95d95257dd44f93&osversion=ios_10.300000&ua=iPhone6_2&imei=&imsi=&uid=450515766&sid=20XNNoa5VwMozGALfmi2xN1YCfLWvEq7aJuTHTQLu8bT88i1aNbi0&conn=wifi&mtid=391bb3520c38e0444ba0b3975f4bb1aa&mtxid=f0b42913a33c&logid=162,210&s_sg=3111b3a0092d652ab3bcb218099968de&s_sc=100&s_st=1492954889";

    private GridView mGvLivingList;
    private LivesAdapter mAdapter;
    private List<Lives> mLivesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGvLivingList = (GridView)findViewById(R.id.gvLivingList);

        mLivesList = new ArrayList<>();
        mAdapter = new LivesAdapter(this,mLivesList);
        mGvLivingList.setAdapter(mAdapter);


        mGvLivingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,VideoViewActivity.class);
                intent.putExtra("stream_addr", mLivesList.get(position).getStream_addr());
                startActivity(intent);
            }
        });

        new RequestLivingTask().execute(url);
    }

    private class RequestLivingTask extends AsyncTask<String,Void,List<Lives>>{

        ProgressDialog mProgressDialog ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(MainActivity.this,"加载中……",null,false);
        }

        @Override
        protected List<Lives> doInBackground(String... params) {
            String url = params[0];
            List<Lives> liveList = new ArrayList<>();
            try {
                String livingList = HttpUtils.getInstance().get(url);
                JSONObject livingObj = new JSONObject(livingList);
                JSONArray livesArr = livingObj.optJSONArray("lives");
                Log.e("", "");
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

            mProgressDialog.dismiss();
        }
    }
}
