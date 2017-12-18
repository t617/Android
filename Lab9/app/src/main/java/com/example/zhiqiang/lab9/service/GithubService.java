package com.example.zhiqiang.lab9.service;

import com.example.zhiqiang.lab9.model.Repos;
import com.example.zhiqiang.lab9.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubService {
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Observable<List<Repos>> getUserRepos(@Path("user") String user);
}
