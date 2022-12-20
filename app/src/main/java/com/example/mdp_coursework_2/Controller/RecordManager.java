package com.example.mdp_coursework_2.Controller;

import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;

public class RecordManager {
    private UserDataSleepRecord userData;
    private Boolean isSleeping;

    public RecordManager() {
    }

    public RecordManager(UserDataSleepRecord userData) {
        this.userData = userData;
    }
}
