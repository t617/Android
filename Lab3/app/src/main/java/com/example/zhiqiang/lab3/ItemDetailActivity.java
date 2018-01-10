package com.example.zhiqiang.lab3;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class  ItemDetailActivity extends AppCompatActivity {
    private ListView itemList;
    private String extrasString;

    String[] itemName = new String[] {"Enchated Forest", "Arla Milk", "DevonDale Milk",
            "Kindle Oasis", "waitrose 早餐麦片", "Mcvities 饼干", "Ferrero Rocher", "Maltesers",
            "Lindt", "Borggreve"};
    String[] itemImageName = new String[] {"enchatedforest", "arla", "devondale", "kindle",
            "waitrose", "mcvitie", "ferrero", "maltesers", "lindt", "borggreve"};
    String[] itemPrice = new String [] {"¥ 5.00 ", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00",
            "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
    String[] itemType = new String[] {"作者 Johanna Basford", "产地 德国", "产地 澳大利亚",
            "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g",
            "重量 118g", "重量 249g", "重量 640g"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        itemOption();
        loadItemDetail();
        backImageListener();
        starImageListener();
        cartImageListener();
    }

    public void starImageListener() {
        final ImageView starImage = (ImageView) findViewById(R.id.starImage);
        final boolean[] flag = {true};
        starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[0]) {
                    starImage.setImageResource(R.mipmap.full_star);
                    flag[0] = false;
                } else {
                    starImage.setImageResource(R.mipmap.empty_star);
                    flag[0] = true;
                }
            }
        });
    }

    public void backImageListener() {
        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void cartImageListener() {
        ImageView cartImage = (ImageView) findViewById(R.id.shoppingCart);
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "商品已加到购物车", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new MessageEvent(extrasString));
            }
        });
    }

    public void loadItemDetail() {
        final List<Map<String, Object>> data = new ArrayList<>();

        for (int i = 0; i < itemName.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("name", itemName[i]);
            temp.put("image", itemImageName[i]);
            temp.put("price", itemPrice[i]);
            temp.put("type", itemType[i]);
            data.add(temp);
        }

        Bundle extras = getIntent().getExtras();
        extrasString = null;
        if (extras != null) {
            extrasString = extras.getString("activityMain");
        }
        String imageString = null, priceString = null, typeString = null;
        for (Map<String, Object> m : data) {
            if (m.get("name").equals(extrasString)) {
                imageString = (String) m.get("image");
                priceString = (String) m.get("price");
                typeString = (String) m.get("type");
                break;
            }
        }
        TextView itemText = (TextView) findViewById(R.id.itemName);
        ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
        TextView itemPriceText = (TextView) findViewById(R.id.itemPrice);
        TextView itemTypeText = (TextView) findViewById(R.id.itemType);

        int imageName =getResource(imageString);
        itemImage.setImageResource(imageName);

        itemText.setText(extrasString);
        itemPriceText.setText(priceString);
        itemTypeText.setText(typeString);
    }

    public int  getResource(String imageName){
        Context ctx = getBaseContext();
        return getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
    }


    public void itemOption() {
        itemList = (ListView) findViewById(R.id.itemOptions);
        final List<Map<String, Object>> data = new ArrayList<>();
        String[] items = new String[] {"一键下单", "分享商品", "不感兴趣",
                "查看更多商品促销信息"};
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("", items[i]);
            data.add(temp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.info,
                new String[] {""}, new int[] {R.id.price});
        itemList.setAdapter(simpleAdapter);
    }
}


