package com.example.mdp_coursework_2.Model.SleepScheduler;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class SleepTimeCycle implements Serializable {
    private Calendar sleepDate;
    private Calendar wakeupDate;

    public SleepTimeCycle(){

    }

    public SleepTimeCycle(Calendar sleepDate, Calendar wakeupDate) {
        this.sleepDate = sleepDate;
        this.wakeupDate = wakeupDate;
    }

    public Calendar getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(Calendar sleepDate) {
        this.sleepDate = sleepDate;
    }

    public Calendar getWakeupDate() {
        return wakeupDate;
    }

    public void setWakeupDate(Calendar wakeupDate) {
        this.wakeupDate = wakeupDate;
    }
}
