package com.example.zhiqiang.fragmentactivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FragmentMainActivity extends AppCompatActivity {
    private Button mainBtn;
    private Button weatherBtn;
    private Button newsBtn;

    private NewsFragment mNews;
    private MainFragment mMain;
    private WeatherFragment mWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);
        bindViews();
        setOnClickListener();

        FragmentManager fm= this.getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        mMain= new MainFragment();
        ft.replace(R.id.fragment_content, mMain);
        ft.commit();
    }
    private void bindViews() {
        mainBtn= (Button) findViewById(R.id.mainBtn);
        weatherBtn= (Button) findViewById(R.id.weatherBtn);
        newsBtn= (Button) findViewById(R.id.newsBtn);
    }

    private void setOnClickListener() {
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                if (mMain== null) {
                    mMain= new MainFragment();
                }
                ft.replace(R.id.fragment_content, mMain);
                ft.commit();
            }
        });
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                if (mWeather== null) {
                    mWeather= new WeatherFragment();
                }
                ft.replace(R.id.fragment_content, mWeather);
                ft.commit();
            }
        });
        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm= getFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                if (mNews== null) {
                    mNews= new NewsFragment();
                }
                ft.replace(R.id.fragment_content, mNews);
                ft.commit();
            }
        });
    }
}
