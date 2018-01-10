package com.example.zhiqiang.notes;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.zhiqiang.notes.Adapter.CardAdapter;
import com.example.zhiqiang.notes.Adapter.ViewHolder;
import com.example.zhiqiang.notes.Factory.ServiceFactory;
import com.example.zhiqiang.notes.Model.News;
import com.example.zhiqiang.notes.Service.WeatherService;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsActivity extends AppCompatActivity {

    SlidrConfig mSlidrConfig;
    SlidrConfig.Builder mBuilder;

    private WeatherService service;
    private String BASE_URL= "http://v.juhe.cn/toutiao/";

    private RecyclerView recyclerView;
    List<Map<String, Object>> list = new ArrayList<>();
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setSlidr();
        setRecyclerview();
        getData();
    }

    public void getData() {
        service= ServiceFactory.createService(BASE_URL);
        service.getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                        Log.d("mine", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("mine", "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(News news) {
                        Log.d("mine", "onNext");
                        List<News.ResultBean.DataBean> data= news.getResult().getData();
                        for (int i= 0; i< data.size(); i++) {
                            String date= data.get(i).getDate();
                            String title= data.get(i).getTitle();
                            String url= data.get(i).getUrl();
                            cardAdapter.addNews(title, date, url);
                        }

                    }
                });
    }

    private void setSlidr() {
        int primary = getResources().getColor(R.color.colorPrimaryDark);
        int secondary = getResources().getColor(R.color.colorAccent);
        mBuilder = new SlidrConfig.Builder().primaryColor(primary)
                .secondaryColor(secondary)
                .scrimColor(Color.BLACK)
                .position(SlidrPosition.LEFT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(5f)
                .distanceThreshold(.35f);
        mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);
    }

    private void setRecyclerview() {
        recyclerView = (RecyclerView) findViewById(R.id.newsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter<Map<String, Object>>(this, R.layout.cardview_news, list) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> map) {
                TextView newsTitle = holder.getView(R.id.newsTitle);
                TextView newsDate = holder.getView(R.id.newsDate);
                newsTitle.setText(map.get("title").toString());
                newsDate.setText(map.get("date").toString());
            }
        };

        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
//                Intent intent = new Intent(MainActivity.this, ReposActivity.class);
//                intent.putExtra("username", editText.getText().toString());
//                startActivity(intent);
                String my_uri= cardAdapter.getUrl(position);
                Log.d("mine", "uri: "+ my_uri);
                Uri uri = Uri.parse(my_uri);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });

        recyclerView.setAdapter(cardAdapter);
    }
}
