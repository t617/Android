package com.example.zhiqiang.notes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.zhiqiang.notes.Factory.ServiceFactory;
import com.example.zhiqiang.notes.Model.Weather;
import com.example.zhiqiang.notes.Service.WeatherService;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherActivity extends AppCompatActivity {

    SlidrConfig mSlidrConfig;
    SlidrConfig.Builder mBuilder;

    private WeatherService service;
    private String BASE_URL= "http://v.juhe.cn/weather/";

    TextView weatherTemp;
    TextView weatherTodayTemp;
    TextView weatherTodatWea;
    TextView weatherDress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        findViews();

        getData();

        setSlidr();

    }

    private void findViews() {
        weatherTemp= (TextView) findViewById(R.id.weatherTemp);
        weatherTodayTemp= (TextView) findViewById(R.id.weatherTodayTemp);
        weatherTodatWea= (TextView) findViewById(R.id.weatherTodayWea);
        weatherDress= (TextView) findViewById(R.id.weatherDress);
    }

    public void getData() {
        service= ServiceFactory.createService(BASE_URL);
        service.getWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
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
                    public void onNext(Weather weather) {
                        Log.d("mine", "onNext");
                        Log.d("mine", weather.getResultcode());
                        Log.d("mine", weather.getResult().getSk().getTemp());
                        Log.d("mine", weather.getResult().getToday().getTemperature());
                        Log.d("mine", weather.getResult().getToday().getDressing_advice());
                        Log.d("mine", weather.getResult().getToday().getWeather());
                        weatherTemp.setText(weather.getResult().getSk().getTemp()+ "℃");
                        weatherTodayTemp.setText(weather.getResult().getToday().getTemperature());
                        weatherTodatWea.setText(weather.getResult().getToday().getWeather());
                        weatherDress.setText(weather.getResult().getToday().getDressing_advice());
                    }
                });
    }

    private void setSlidr() {
        int primary = getResources().getColor(R.color.colorPrimaryDark);
        int secondary = getResources().getColor(R.color.colorAccent);
        mBuilder = new SlidrConfig.Builder().primaryColor(primary)
                .secondaryColor(secondary)
                .scrimColor(Color.BLACK)
                .position(SlidrPosition.RIGHT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(5f)
                .distanceThreshold(.35f);
        mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);
    }
}
