package com.example.zhiqiang.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhiqiang.lab9.adapter.CardAdapter;
import com.example.zhiqiang.lab9.adapter.ViewHolder;
import com.example.zhiqiang.lab9.factory.ServiceFactory;
import com.example.zhiqiang.lab9.model.User;
import com.example.zhiqiang.lab9.service.GithubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private List<Map<String, Object>> listItems = new ArrayList<>();//没有初始值
    private CardAdapter<Map<String, Object>> cardAdapter;
    private EditText seach;
    private Button clear;
    private Button fetch;
    private String name;
    private GithubService service;
    private String BASE_URL;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BASE_URL = "https://api.github.com";
        findView();
        initialRecyclerView();
        setListener();
    }

    public void findView() {
        seach = (EditText) findViewById(R.id.seach);
        clear = (Button) findViewById(R.id.clear);
        fetch = (Button) findViewById(R.id.fetch);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void setListener() {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardAdapter.removeAll();
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = seach.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                getUserInfo(name);
            }
        });
    }

    public void getUserInfo(String name) {
        service = ServiceFactory.createService(BASE_URL);
        service.getUser(name)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<User>() {
            @Override
            public final void onCompleted() {
                Log.e("observable", "complete");
                removeWait();
            }
            @Override
            public void onError(Throwable e) {
                Log.e("observable", e.toString());
                Toast.makeText(MainActivity.this, e.hashCode() + "请确认你搜索的用户存在", Toast.LENGTH_SHORT).show();
                removeWait();
            }
            @Override
            public void onNext(User user) {
                cardAdapter.addData(user);
            }
        });
    }

    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    public void initialRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter<Map<String, Object>>(this, R.layout.user, listItems) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView login = holder.getView(R.id.login);
                login.setText(s.get("login").toString());
                TextView id = holder.getView(R.id.id);
                id.setText("id: " + s.get("id").toString());
                TextView blog = holder.getView(R.id.blog);
                blog.setText("blog :" + s.get("blog").toString());
            }
        };
        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, ReposActivity.class);
                Bundle bundle = new Bundle();
                String data = cardAdapter.getName(position).get("login").toString();
                bundle.putString("name", data);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
            @Override
            public void onLongClick(int position) {
                cardAdapter.removeItem(position);
            }
        });
        recyclerView.setAdapter(cardAdapter);
    }
}
