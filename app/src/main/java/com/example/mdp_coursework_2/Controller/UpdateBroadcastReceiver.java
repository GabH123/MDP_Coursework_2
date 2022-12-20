package com.example.mdp_coursework_2.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DataStorageHandler handle = new DataStorageHandler(context);
        LinksAdvicesManager linkManager = handle.openLinkSharedPreferences();
        linkManager.randomiseIndex();
        handle.saveLinkSharedPreferences(linkManager);
    }
}
