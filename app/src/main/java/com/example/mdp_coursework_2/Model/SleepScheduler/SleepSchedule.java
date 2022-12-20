package com.example.mdp_coursework_2.Model.SleepScheduler;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;

public class SleepSchedule implements Serializable {

    private Calendar oldSleepTime;
    private Calendar oldWakeupTime;
    private Calendar newSleepTime;
    private Calendar newWakeupTime;


    public SleepSchedule() {
    }

    public SleepSchedule(Calendar oldSleepTime, Calendar oldWakeupTime, Calendar newSleepTime, Calendar newWakeupTime) {
        this.oldSleepTime = oldSleepTime;
        this.oldWakeupTime = oldWakeupTime;
        this.newSleepTime = newSleepTime;
        this.newWakeupTime = newWakeupTime;
    }




    public Calendar getOldSleepTime() {
        return oldSleepTime;
    }

    public void setOldSleepTime(Calendar oldSleepTime) {
        this.oldSleepTime = oldSleepTime;
    }

    public Calendar getOldWakeupTime() {
        return oldWakeupTime;
    }

    public void setOldWakeupTime(Calendar oldWakeupTime) {
        this.oldWakeupTime = oldWakeupTime;
    }

    public Calendar getNewSleepTime() {
        return newSleepTime;
    }

    public void setNewSleepTime(Calendar newSleepTime) {
        this.newSleepTime = newSleepTime;
    }

    public Calendar getNewWakeupTime() {
        return newWakeupTime;
    }

    public void setNewWakeupTime(Calendar newWakeupTime) {
        this.newWakeupTime = newWakeupTime;
    }
}
