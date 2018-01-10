package com.example.zhiqiang.notes.Model;

/**
 * Created by ts13 on 2018/1/7.
 */

public class Memo {
    private String time;
    private String content;
    public Memo(String t, String c) {
        time= t;
        content = c;
    }
    public String getTime() {
        return time;
    }
    public String getContent() {
        return content;
    }
}
