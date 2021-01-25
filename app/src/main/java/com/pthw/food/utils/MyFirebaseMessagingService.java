package com.pthw.food.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pthw.food.R;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("NotiToken", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().isEmpty())
            showxNotificaiotn(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        else
            showxNotificaiotn(remoteMessage.getData());
    }

    private void showxNotificaiotn(Map<String, String> data) {

        String title = data.get("tile");
        String body = data.get("body");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notificaiotn_channel_id = "mychannelid";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notificaiotn_channel_id, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("My channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, notificaiotn_channel_id);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    private void showxNotificaiotn(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notificaiotn_channel_id = "mychannelid";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(notificaiotn_channel_id, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("My channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, notificaiotn_channel_id);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

}
