package com.example.zhiqiang.electronicdictionary.Data;

import java.io.Serializable;
//人物的类
public class Present implements Serializable {

    private String firstLetter;
    private String name;
    private String sex;
    private String life;
    private String unit;
    private String place;
    private int imageId;
    private String info;

    public Present(String name,String sex, String life, String unit, String place, int imageId,String info) {
        this.name = name;
        this.sex = sex;
        this.life = life;
        this.unit = unit;
        this.place = place;
        this.imageId = imageId;
        this.info = info;
        firstLetter = name.charAt(0) + "";
    }

    public int getImageId() {
        return imageId;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getUnit() {return unit;}

    public String getLife() {
        return life;
    }

    public String getName() {
        return name;
    }

    public String getPlace (){return place;}

    public String getSex()  { return sex;}

    public String getInfo() { return info;}


}
