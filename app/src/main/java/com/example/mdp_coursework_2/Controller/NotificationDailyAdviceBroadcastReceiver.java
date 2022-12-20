package com.example.mdp_coursework_2.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.example.mdp_coursework_2.R;

public class NotificationDailyAdviceBroadcastReceiver extends BroadcastReceiver {

    LinksAdvicesManager manager;
    DataStorageHandler handler;

    @Override
    public void onReceive(Context context, Intent intent) {
        handler = new DataStorageHandler(context);
        manager = handler.openLinkSharedPreferences();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SleepHelperNotifChannel", "Sleep Helper Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"SleepHelperNotifChannel");

        builder.setContentTitle("Advice of the day");
        builder.setContentText(manager.getTodayAdvice());
        builder.setSmallIcon(R.drawable.ic_notif_icons);
        builder.setAutoCancel(true);

        Notification notification = builder.build();
        NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        notifManager.notify(0,notification);

    }
}