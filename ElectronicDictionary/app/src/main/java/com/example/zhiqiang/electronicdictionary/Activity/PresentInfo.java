package com.example.zhiqiang.electronicdictionary.Activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhiqiang.electronicdictionary.Data.Present;
import com.example.zhiqiang.electronicdictionary.R;

/**
 * 联系人详细信息Activity
 */
public class PresentInfo extends AppCompatActivity {

    private ImageView topBack;  //  人物图片
    private TextView name;  //  人物姓名
    private TextView sex ;   //人物性别
    private TextView life;  //  生卒
    private TextView unit;  //  势力
    private TextView place;  //  籍贯
    private TextView info;  //  人物信息
    private Present Present;  //  该页面的商品

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        initialData();
    }

    /**
     * 初始化布局
     */
    private void findView() {
        setContentView(R.layout.content);
        topBack = (ImageView) findViewById(R.id.top_back);
        name = (TextView) findViewById(R.id.name);
        life = (TextView) findViewById(R.id.life);
        unit = (TextView) findViewById(R.id.unit) ;
        place = (TextView) findViewById(R.id.place);
        sex = (TextView) findViewById(R.id.sex);
        info = (TextView) findViewById(R.id.info);
    }

    /**
     * 初始化数据
     */
    private void initialData() {
        Resources res = getResources();

        Present = (Present) getIntent().getExtras().get("Present");
        if (Present != null) {
            //topBack.setBackgroundColor(res.getColor(contacts.getBackgroundColor()));
            topBack.setImageResource(Present.getImageId());
            name.setText(Present.getName());
            life.setText(Present.getLife());
            unit.setText(Present.getUnit());
            place.setText(Present.getPlace());
            sex.setText(Present.getSex());
            info.setText(Present.getInfo());
        }

    }
}
