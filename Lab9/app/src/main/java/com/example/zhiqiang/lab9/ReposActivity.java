package com.example.zhiqiang.lab9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.zhiqiang.lab9.factory.ServiceFactory;
import com.example.zhiqiang.lab9.model.Repos;
import com.example.zhiqiang.lab9.service.GithubService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReposActivity extends AppCompatActivity {
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private ListView listView;
    private ProgressBar progressBar;
    private String name;
    private GithubService service;
    private String BASE_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        BASE_URL = "https://api.github.com";
        findView();
        initialRepos();
    }
    public void findView() {
        listView = (ListView) findViewById(R.id.reposList);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }
    public void getUserRepos(String name) {
        service = ServiceFactory.createService(BASE_URL);
        service.getUserRepos(name).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Repos>>() {
                @Override
                public final void onCompleted() {
                    Log.e("observable", "complete");
                    removeWait();
                }
                @Override
                public void onError(Throwable e) {
                    Log.e("observable", e.toString());
                    removeWait();
                }
                @Override
                public void onNext(List<Repos> repos) {
                    showRepos(repos);
                }
            });
    }
    public void showRepos(List<Repos> repos) {
        for (Repos r : repos) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("name", r.getName());
            listItem.put("description", r.getDescription());
            listItem.put("language", r.getLanguage());
            listItems.add(listItem);
        }
        simpleAdapter = new SimpleAdapter(this, listItems, R.layout.repos,
                new String[]{"name", "language", "description"}, new int[]{R.id.name, R.id.language, R.id.description});
        listView.setAdapter(simpleAdapter);
    }
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    public void initialRepos() {
        name = getIntent().getExtras().get("name").toString();
        getUserRepos(name);
    }
}
