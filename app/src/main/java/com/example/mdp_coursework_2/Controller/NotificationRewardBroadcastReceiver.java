package com.example.mdp_coursework_2.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mdp_coursework_2.R;
import com.example.mdp_coursework_2.View.MainActivity;

public class NotificationRewardBroadcastReceiver extends BroadcastReceiver {

    static public final int REWARD_FRAGMENT_ID = 2;
    static public final String SHOW_REWARD_FRAGMENT = "showRewardFragment";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SleepHelperNotifChannel", "Sleep Helper Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"SleepHelperNotifChannel");

        builder.setContentTitle("Reward of the day");
        builder.setContentText("You went to sleep on time yesterday! You can watch a funny or cute video, or read a joke in the rewards section, picked just for you!");
        builder.setSmallIcon(R.drawable.ic_notif_icons);
        builder.setAutoCancel(true);

        Intent openRewardsFragment = new Intent(context, MainActivity.class);
        openRewardsFragment.putExtra(SHOW_REWARD_FRAGMENT, REWARD_FRAGMENT_ID);
        PendingIntent pendingReward = PendingIntent.getBroadcast(context,0,openRewardsFragment,PendingIntent.FLAG_IMMUTABLE);

        builder.setContentIntent(pendingReward);
        Notification notification = builder.build();
        NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        notifManager.notify(0,notification);
    }
}