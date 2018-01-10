package com.example.zhiqiang.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by zhiqiang on 2017/10/27.
 */

public class StaticBroadCast extends BroadcastReceiver {
    private Items items = new Items();
    private final String STATICACTION = "com.example.Lab5.MyStaticBroadCast";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)) {
            Bundle bundle = intent.getExtras();
            assert bundle != null;
            String itemDetail = bundle.getString("MyStaticBroadCast");
//            Toast.makeText(context,
//                    "MyStaticBroadCast", Toast.LENGTH_SHORT).show();
//            Random random = new Random();
//            String itemDetail = items.itemName[random.nextInt(items.itemName.length)];
            String itemPrice = items.itemPrice[items.findPositionOfName(itemDetail)];

            int imageId = items.itemImage[items.findPositionOfName(itemDetail)];

            NotificationManager manager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE
            );
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(imageId)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), imageId))
                    .setContentTitle("新商品热卖")
                    .setContentText(itemDetail + "仅售" + itemPrice);

            Intent mIntent = new Intent(context, ItemDetailActivity.class);
            mIntent.putExtra("activityMain", itemDetail);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, mIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notify = builder.build();
            builder.setContentIntent(pIntent);
            manager.notify(0, notify);
        }
    }
}
