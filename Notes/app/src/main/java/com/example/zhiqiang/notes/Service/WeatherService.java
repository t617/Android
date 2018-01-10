package com.example.zhiqiang.notes.Service;
import com.example.zhiqiang.notes.Model.News;
import com.example.zhiqiang.notes.Model.Weather;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ts13 on 2017/12/14.
 */

public interface WeatherService {
    @GET("index?cityname=%E5%8C%97%E4%BA%AC&dtype=&format=&key=25284bc841114569b8065707912d3888")
    Observable<Weather> getWeather();

    @GET("index?type=top&key=b78c33de748f329ceb101c59f6901fd1")
    Observable<News> getNews();
}
