package com.example.mdp_coursework_2.Model.SleepScheduler;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

public class UserDataSleepRecord implements Serializable {

    private final int LAST_NUMBER_OF_DAYS_RECORDED=7;

    static public final int OLD_SLEEP_TIME =0;
    static public final int OLD_WAKEUP_TIME =1;
    static public final int NEW_SLEEP_TIME =2;
    static public final int NEW_WAKEUP_TIME =3;
    static public final int RECOMMENDED_SLEEP_TIME=4;
    static public final int LATEST_SLEEP_TIME = 5;
    static public final int LATEST_WAKEUP_TIME = 6;
    static public final int RECOMMENDED_WAKEUP_TIME=7;

    private Boolean isSleeping;

    private Boolean isWentToSleepOnTime;

    private SleepSchedule sleepSchedule;
    private SleepTimeCycle[] sleepActivityPastFewDays = new SleepTimeCycle[LAST_NUMBER_OF_DAYS_RECORDED];
    private Calendar recommendedSleepTime;
    private Calendar recommendedWakeupTime;

    private SleepTimeCycle latestSleepTimeCycle;



    public UserDataSleepRecord() {
        isSleeping = false;
        isWentToSleepOnTime = false;
    }


    public void userWentToSleep(Calendar userSleepTime){
        latestSleepTimeCycle = new SleepTimeCycle();
        latestSleepTimeCycle.setSleepDate(userSleepTime);
        isSleeping = true;
        recommendedWakeupTime = (Calendar) userSleepTime.clone();
        Log.d("User sleep time", "Went to sleep at "+Calendar.getInstance().getTime().toString());
        recommendedWakeupTime.add(Calendar.HOUR_OF_DAY,8);
    }

    public void userWokeUp(Calendar userWokeupTime){
        latestSleepTimeCycle.setWakeupDate(userWokeupTime);
        isSleeping = false;
        addLatest(latestSleepTimeCycle);
        calculateNewSleepTime();
    }

    public void addLatest(SleepTimeCycle latestActivity){
        for (int i = sleepActivityPastFewDays.length-1;i>0;i--)
            sleepActivityPastFewDays[i]=sleepActivityPastFewDays[i-1];
        sleepActivityPastFewDays[0] = latestActivity;
    }

    public void calculateNewSleepTime(){

        if (recommendedSleepTime!=null && latestSleepTimeCycle!=null){
            recommendedSleepTime.set(Calendar.DATE,latestSleepTimeCycle.getSleepDate().get(Calendar.DATE));
            int hourOfDay = recommendedSleepTime.get(Calendar.HOUR_OF_DAY);
            if ((12>hourOfDay)&&(hourOfDay>=0))
                recommendedSleepTime.add(Calendar.DATE,1);

            if(latestSleepTimeCycle.getSleepDate().before(recommendedSleepTime)) {
                recommendedSleepTime.add(Calendar.MINUTE, -15);
                isWentToSleepOnTime = true;
            }else {
                isWentToSleepOnTime = false;
            }
        }else{
            recommendedSleepTime = Calendar.getInstance();
            recommendedSleepTime.set(Calendar.HOUR_OF_DAY,sleepSchedule.getOldSleepTime().get(Calendar.HOUR_OF_DAY));
            recommendedSleepTime.set(Calendar.MINUTE,sleepSchedule.getOldSleepTime().get(Calendar.MINUTE));

            if (sleepSchedule.getOldSleepTime().get(Calendar.HOUR_OF_DAY)>=recommendedSleepTime.get(Calendar.HOUR_OF_DAY)){
                if (sleepSchedule.getOldSleepTime().get(Calendar.MINUTE)>=recommendedSleepTime.get(Calendar.MINUTE)){

                }
                else
                    recommendedSleepTime.add(Calendar.DATE,1);
            }else
                recommendedSleepTime.add(Calendar.DATE,1);



            recommendedSleepTime.add(Calendar.MINUTE,-15);

        }

    }

    public String getTimeIn12HourFormat(int number){
        Calendar calendarToConvert;
        switch (number){
            case OLD_SLEEP_TIME:
                calendarToConvert = sleepSchedule.getOldSleepTime();
                break;
            case OLD_WAKEUP_TIME:
                calendarToConvert = sleepSchedule.getOldWakeupTime();
                break;
            case NEW_SLEEP_TIME:
                calendarToConvert = sleepSchedule.getNewSleepTime();
                break;
            case NEW_WAKEUP_TIME:
                calendarToConvert = sleepSchedule.getNewWakeupTime();
                break;
            case RECOMMENDED_SLEEP_TIME:
                calendarToConvert = recommendedSleepTime;
                break;
            case RECOMMENDED_WAKEUP_TIME:
                calendarToConvert = recommendedWakeupTime;
                break;
            case LATEST_SLEEP_TIME:
                calendarToConvert = latestSleepTimeCycle.getSleepDate();
                break;
            case LATEST_WAKEUP_TIME:
                calendarToConvert = latestSleepTimeCycle.getWakeupDate();
                break;
            default:
                calendarToConvert = null;
        }

        return convert24To12HourFormat(calendarToConvert);
    }

    public String convert24To12HourFormat(Calendar calendarToConvert){
        if(calendarToConvert!=null){
            String newTime = new String();
            int hourOfDay = calendarToConvert.get(Calendar.HOUR_OF_DAY);
            String hourOfDayFormatted;
            int minuteOfDay = calendarToConvert.get(Calendar.MINUTE);
            String minuteOfDayFormatted;
            String amOrPm;
            if (calendarToConvert.get(Calendar.HOUR_OF_DAY) > 12) {
                hourOfDay -= 12;
                amOrPm = "PM";
            }
            else {
                amOrPm = "AM";

            }

            if (hourOfDay!=0)
                hourOfDayFormatted = Integer.toString(hourOfDay);
            else
                hourOfDayFormatted = "12";

            if (10>minuteOfDay)
                minuteOfDayFormatted = "0" + minuteOfDay;
            else
                minuteOfDayFormatted = Integer.toString(minuteOfDay);
            return hourOfDayFormatted + ":"+ minuteOfDayFormatted + " "+amOrPm;
        } else
            return "--:--";
    }


    public boolean checkUserIsSleeping(){
        return isSleeping;
    }

    public void setLatestSleepTimeCycle(SleepTimeCycle latestSleepTimeCycle) {
        this.latestSleepTimeCycle = latestSleepTimeCycle;
    }

    public void setSleepSchedule(SleepSchedule sleepSchedule) {
        this.sleepSchedule = sleepSchedule;
    }

    public SleepTimeCycle getLatestSleepTimeCycle() {
        return latestSleepTimeCycle;
    }

    public SleepSchedule getSleepSchedule() {
        return sleepSchedule;
    }

    public void setSleepActivityPastFewDays(SleepTimeCycle[] sleepActivityPastFewDays) {
        this.sleepActivityPastFewDays = sleepActivityPastFewDays;
    }

    public SleepTimeCycle[] getSleepActivityPastFewDays() {
        return sleepActivityPastFewDays;
    }

    public Calendar getRecommendedSleepTime() {
        return recommendedSleepTime;
    }

    public Calendar getRecommendedWakeupTime() {
        return recommendedWakeupTime;
    }

    public Boolean didWentToSleepOnTime() {
        return isWentToSleepOnTime;
    }
}
