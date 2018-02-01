package com.example.zhiqiang.vq.entity;

/**
 * Created by zhiqiang on 2018/1/30.
 */

public class tvLives {
    private String tvName;
    private String urlAddr;
    public tvLives(String tvName, String urlAddr) {
        this.tvName = tvName;
        this.urlAddr = urlAddr;
    }
    public String getTvName() {
        return tvName;
    }
    public String getUrlAddr() {
        return urlAddr;
    }
}
