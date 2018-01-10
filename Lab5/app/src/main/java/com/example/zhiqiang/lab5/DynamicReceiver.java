package com.example.zhiqiang.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by zhiqiang on 2017/10/27.
 */

public class DynamicReceiver extends BroadcastReceiver {
    private com.example.zhiqiang.lab5.Items items = new Items();
    private static final String DYNAMICACTION = "MyDynamicBroCast";

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(DYNAMICACTION)) {
            String itemDetail = bundle.getString("MESSAGE");

            int id = items.findPositionOfName(itemDetail);
            int imageId = items.itemImage[id];
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(imageId)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), imageId))
                    .setContentTitle("马上下单")
                    .setContentText(itemDetail + "已添加到购物车").build();

            NotificationManager manager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE
            );

            Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.putExtras(bundle);
            PendingIntent pi = PendingIntent.getActivity(context, 0, mIntent, 0);
//            PendingIntent.FLAG_UPDATE_CURRENT
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
            updateViews.setTextViewText(R.id.appwidget_text, itemDetail + "已添加到购物车");
            updateViews.setImageViewResource(R.id.appwidget_image, imageId);
            updateViews.setOnClickPendingIntent(R.id.appwidget, pi);
            ComponentName me = new ComponentName(context, ItemWidget.class);
            appWidgetManager.updateAppWidget(me, updateViews);

            Notification notify = builder.build();
            builder.setContentIntent(pi);
            manager.notify(0, notify);
        }
    }
}
