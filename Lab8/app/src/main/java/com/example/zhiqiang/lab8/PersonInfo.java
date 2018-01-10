package com.example.zhiqiang.lab8;

import java.io.Serializable;

/**
 * Created by zhiqiang on 2017/12/4.
 */

public class PersonInfo implements Serializable {
    private String name;
    private String birthday;
    private String gift;
    public PersonInfo(String name, String birthday, String gift) {
        this.name = name;
        this.birthday = birthday;
        this.gift = gift;
    }
    public String getName() {
        return name;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getGift() {
        return gift;
    }
}
