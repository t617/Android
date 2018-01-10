package com.example.zhiqiang.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private Items items = new Items();
    DynamicReceiver dynamicReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        itemOption();
        Bundle extras = getIntent().getExtras();
        extrasString = null;
        if (extras != null) {
            extrasString = extras.getString("activityMain");
        }
        loadItemDetail();
        backImageListener();
        starImageListener();
        cartImageListener();
        dynamicReceiver = new DynamicReceiver();
        dynamicBroadCast();
    }

    public void dynamicBroadCast() {
        dynamicReceiver = new DynamicReceiver();
        IntentFilter dynamicFilter = new IntentFilter();
        dynamicFilter.addAction("MyDynamicBroCast");
        registerReceiver(dynamicReceiver, dynamicFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
        unregisterReceiver(dynamicReceiver);
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
//                Toast.makeText(getApplicationContext(),
//                        "cart", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent("MyDynamicBroCast");
                intent.putExtra("MESSAGE", extrasString);
                sendBroadcast(intent);
            }
        });
    }

    public void loadItemDetail() {
        final List<Map<String, Object>> data = new ArrayList<>();

        for (int i = 0; i < items.itemName.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("name", items.itemName[i]);
            temp.put("image", items.itemImageName[i]);
            temp.put("price", items.itemPrice[i]);
            temp.put("type", items.itemType[i]);
            data.add(temp);
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


