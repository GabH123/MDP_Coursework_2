package com.example.mdp_coursework_2.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;

import java.util.Calendar;

public class WakeupModeBroadcastReceiver extends BroadcastReceiver {
    UserDataSleepRecord userSchedule;
    DataStorageHandler dataStorageHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        dataStorageHandler = new DataStorageHandler(context);
        userSchedule = dataStorageHandler.openUserData();
        userSchedule.userWokeUp(Calendar.getInstance());
        dataStorageHandler.saveUserData(userSchedule);
    }
}