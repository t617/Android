package com.example.zhiqiang.lab4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by zhiqiang on 2017/10/27.
 */

public class DynamicReceiver extends BroadcastReceiver {
    private Items items = new Items();
    private static final String DYNAMICACTION = "MyDynamicBroCast";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {
            Bundle bundle = intent.getExtras();

            String itemDetail = bundle.getString("MESSAGE");
            Toast.makeText(context,
                    itemDetail, Toast.LENGTH_SHORT).show();
            int imageId = items.itemImage[items.findPositionOfName(itemDetail)];
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(imageId)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), imageId))
                    .setContentTitle("马上下单")
                    .setContentText(itemDetail + "已添加到购物车").build();

            NotificationManager manager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE
            );
            Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.putExtra("shoppingCart", "cart");
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
//            PendingIntent.FLAG_UPDATE_CURRENT
            Notification notify = builder.build();
            builder.setContentIntent(pIntent);
            manager.notify(0, notify);
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(MessageEvent event) {
//
//        String msg = event.getMsg();
//        Map<String, Object> temp = new LinkedHashMap<>();
//        temp.put("tag", msg.substring(0, 1));
//        temp.put("name", msg);
//        temp.put("price", itemPrice[findPositionOfName(msg)]);
//        cartData.add(temp);
//        updateItems();
//    }

//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);//取消注册
//    }
}
