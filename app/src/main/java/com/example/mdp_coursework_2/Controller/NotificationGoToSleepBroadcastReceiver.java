package com.example.mdp_coursework_2.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.example.mdp_coursework_2.R;

public class NotificationGoToSleepBroadcastReceiver extends BroadcastReceiver {
    UserDataSleepRecord userSchedule;
    DataStorageHandler dataStorageHandler;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Tell user","Telling user to go to sleep");
        dataStorageHandler = new DataStorageHandler(context);
        userSchedule = dataStorageHandler.openUserData();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SleepHelperNotifChannel", "Sleep Helper Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"SleepHelperNotifChannel");

        builder.setContentTitle("Sleep time soon");
        builder.setContentText("Reminder to go to sleep in 1 hours! Your sleep time is: " + userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.RECOMMENDED_SLEEP_TIME));
        builder.setSmallIcon(R.drawable.ic_notif_icons);
        builder.setAutoCancel(true);

        Intent intentGoToSleep = new Intent(context,SleepModeBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intentGoToSleep,PendingIntent.FLAG_IMMUTABLE);

        builder.addAction(R.drawable.ic_notif_gottosleep_action,"Go to sleep",pendingIntent);

        Notification notification = builder.build();
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(0,notification);



    }
}