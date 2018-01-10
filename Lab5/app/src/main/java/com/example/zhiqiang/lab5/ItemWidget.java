package com.example.zhiqiang.lab5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class ItemWidget extends AppWidgetProvider {
    private Items items = new Items();
//    static DynamicReceiver dynamicReceiver;
    private final String STATICACTION = "com.example.Lab5.MyStaticBroadCast2";
    private final String DYNAMICACTION = "MyDynamicBroCast";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        int widgetImage = R.mipmap.shoplist;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setImageViewResource(R.id.appwidget_image, widgetImage);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.appwidget, pi);
        ComponentName me = new ComponentName(context, ItemWidget.class);
        appWidgetManager.updateAppWidget(me, updateViews);

//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(STATICACTION)) {
            String itemDetail = bundle.getString("MyStaticBroadCast2");
//            Toast.makeText(context,
//                    "收到静态广播"+itemDetail, Toast.LENGTH_LONG).show();
            String itemPrice = items.itemPrice[items.findPositionOfName(itemDetail)];
            int imageId = items.itemImage[items.findPositionOfName(itemDetail)];

            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
            updateViews.setTextViewText(R.id.appwidget_text, itemDetail + "仅售" + itemPrice);
            updateViews.setImageViewResource(R.id.appwidget_image, imageId);
            Intent i = new Intent(context, ItemDetailActivity.class);
            i.putExtra("activityMain", itemDetail);
            PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.appwidget, pi);
            ComponentName me = new ComponentName(context, ItemWidget.class);
            appWidgetManager.updateAppWidget(me, updateViews);

        }
    }

    @Override
    public void onEnabled(Context context) {
//        super.onEnabled(context);
//        dynamicReceiver = new DynamicReceiver();
//        IntentFilter dynamicFilter = new IntentFilter();
//        dynamicFilter.addAction("MyDynamicBroCast");
//        context.registerReceiver(dynamicReceiver, dynamicFilter);
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
//        context.unregisterReceiver(dynamicReceiver);
        // Enter relevant functionality for when the last widget is disabled
    }
}

