package com.example.mdp_coursework_2.View.HomeFragment.NewSchedulePopup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TimePicker;

import com.example.mdp_coursework_2.R;
import com.example.mdp_coursework_2.Model.SleepScheduler.SleepSchedule;

import java.util.Calendar;

public class ActivityNewSchedulePopup extends AppCompatActivity {

    public static final int POPUP_CLOSE = 0;
    public static final int POPUP_DONE = 1;

    private final double POPUP_SIZE_FACTOR=0.85;

    Fragment currentScheduleFragment;
    Fragment newScheduleFragment;

    TimePicker timePickerCurrentSleepTime;
    TimePicker timePickerCurrentWakeupTime;
    TimePicker timePickerNewSleepTime;
    TimePicker timePickerNewWakeupTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_popup);

        currentScheduleFragment = new PopupCurrentScheduleFragment();
        newScheduleFragment =  new PopupNewScheduleFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.setupNewScheduleFragment,currentScheduleFragment).commit();


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm .heightPixels;

        getWindow().setLayout((int)(width*POPUP_SIZE_FACTOR),(int)(height*POPUP_SIZE_FACTOR));


    }

    public void closeNewSchedulePopup(View view){
        setResult(POPUP_CLOSE);
        finish();
    }

    public void nextGetNewSchedule(View view){
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.setupNewScheduleFragment);
        timePickerCurrentSleepTime = currentFragment.getView().findViewById(R.id.timePickerCurrentSleepTime);
        timePickerCurrentWakeupTime = currentFragment.getView().findViewById(R.id.timePickerCurrentWakeupTime);
        getSupportFragmentManager().beginTransaction().replace(R.id.setupNewScheduleFragment,newScheduleFragment).commit();
    }

    public void previousGetCurrentSchedule(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.setupNewScheduleFragment,currentScheduleFragment).commit();

    }

    public void doneMakingNewSchedule(View view){
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.setupNewScheduleFragment);

        timePickerNewSleepTime = currentFragment.getView().findViewById(R.id.timePickerNewSleepTime);
        timePickerNewWakeupTime = currentFragment.getView().findViewById(R.id.timePickerNewWakeupTime);

        Calendar oldSleepTime = Calendar.getInstance();
        Calendar oldWakeupTime = Calendar.getInstance();
        Calendar newSleepTime = Calendar.getInstance();
        Calendar newWakeupTime = Calendar.getInstance();

        oldSleepTime.set(Calendar.HOUR_OF_DAY,timePickerCurrentSleepTime.getCurrentHour());
        oldSleepTime.set(Calendar.MINUTE,timePickerCurrentSleepTime.getCurrentMinute());

        oldWakeupTime.set(Calendar.HOUR_OF_DAY,timePickerCurrentWakeupTime.getCurrentHour());
        oldWakeupTime.set(Calendar.MINUTE,timePickerCurrentWakeupTime.getCurrentMinute());

        newSleepTime.set(Calendar.HOUR_OF_DAY,timePickerNewSleepTime.getCurrentHour());
        newSleepTime.set(Calendar.MINUTE,timePickerNewSleepTime.getCurrentMinute());

        newWakeupTime.set(Calendar.HOUR_OF_DAY,timePickerNewWakeupTime.getCurrentHour());
        newWakeupTime.set(Calendar.MINUTE,timePickerNewWakeupTime.getCurrentMinute());


        SleepSchedule newSleepSchedule = new SleepSchedule(oldSleepTime,oldWakeupTime,newSleepTime,newWakeupTime);
        Intent finishActivity = new Intent();
        finishActivity.putExtra("USER_SCHEDULE",newSleepSchedule);
        setResult(POPUP_DONE,finishActivity);
        finish();
    }
}