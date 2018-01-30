package com.example.zhiqiang.vq.entity;

/**
 * Created by zhiqiang on 2018/1/30.
 */

public class tvLives {
    private String tvName;
    private String portrait;
    private String description;
    private String urlAddr;
    public tvLives(String tvName, String portrait, String description, String urlAddr) {
        this.tvName = tvName;
        this.portrait = portrait;
        this.description = description;
        this.urlAddr = urlAddr;
    }
    public String getTvName() {
        return tvName;
    }
    public String getPortrait() {
        return portrait;
    }
    public String getDescription() {
        return description;
    }
    public String getUrlAddr() {
        return urlAddr;
    }
}
