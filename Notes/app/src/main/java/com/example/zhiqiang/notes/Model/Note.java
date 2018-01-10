package com.example.zhiqiang.notes.Model;

import java.io.Serializable;

/**
 * Created by zhiqiang on 2018/1/8.
 */

public class Note implements Serializable {
    private String time;
    private String content;
    private String record;
    private String imagePath;
    private String position;
    public Note(String t, String c, String r, String i, String p) {
        time= t;
        content = c;
        record = r;
        imagePath = i;
        position = p;
    }
    public String getTime() {
        return time;
    }
    public String getContent() {
        return content;
    }
    public String getRecord() {
        return record;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getPosition() {
        return position;
    }
}
