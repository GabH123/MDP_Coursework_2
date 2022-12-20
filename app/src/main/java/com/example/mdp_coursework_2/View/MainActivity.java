package com.example.mdp_coursework_2.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mdp_coursework_2.Controller.DataStorageHandler;
import com.example.mdp_coursework_2.Controller.LinksAdvicesManager;
import com.example.mdp_coursework_2.Controller.NotificationDailyAdviceBroadcastReceiver;
import com.example.mdp_coursework_2.Controller.NotificationGoToSleepBroadcastReceiver;
import com.example.mdp_coursework_2.Controller.NotificationRewardBroadcastReceiver;
import com.example.mdp_coursework_2.Controller.NotificationWakeupBroadcastReceiver;
import com.example.mdp_coursework_2.R;
import com.example.mdp_coursework_2.Model.SleepScheduler.SleepSchedule;
import com.example.mdp_coursework_2.Controller.UpdateBroadcastReceiver;
import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.example.mdp_coursework_2.View.HomeFragment.HomeFragment;
import com.example.mdp_coursework_2.View.HomeFragment.NewSchedulePopup.ActivityNewSchedulePopup;
import com.example.mdp_coursework_2.View.RewardsFragment.JokePopup;
import com.example.mdp_coursework_2.View.RewardsFragment.RewardsFragment;
import com.example.mdp_coursework_2.View.StatisticsFragment.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationMainActivity;
    private ActionBar actionBar;
    private Fragment homeFragment;
    private Fragment statisticsFragment;
    private Fragment rewardsFragment;

    private UserDataSleepRecord userSchedule;
    private DataStorageHandler mainDataStorageHandler;

    private LinksAdvicesManager linksAdvicesManager;



    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationMainActivity = findViewById(R.id.bottomNavigationView);
        bottomNavigationMainActivity.setSelectedItemId(R.id.home);

        homeFragment = new HomeFragment();
        statisticsFragment = new StatisticsFragment();
        rewardsFragment = new RewardsFragment();
        actionBar = getSupportActionBar();
        mainDataStorageHandler = new DataStorageHandler(this);
        linksAdvicesManager = mainDataStorageHandler.openLinkSharedPreferences();
        userSchedule = mainDataStorageHandler.openUserData();

        //int getFragmentID = savedInstanceState.getInt(NotificationRewardBroadcastReceiver.SHOW_REWARD_FRAGMENT,-1);
        //if(getFragmentID == NotificationRewardBroadcastReceiver.REWARD_FRAGMENT_ID)
            //getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,rewardsFragment).commit();
        //else
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,homeFragment).commit();

        bottomNavigationMainActivity.setOnItemSelectedListener(item->{
            return switchMainFragment(item);
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == ActivityNewSchedulePopup.POPUP_DONE) {
                            userSchedule = new UserDataSleepRecord();
                            userSchedule.setSleepSchedule((SleepSchedule) result.getData().getExtras().getSerializable("USER_SCHEDULE"));
                            userSchedule.calculateNewSleepTime();
                            mainDataStorageHandler.saveUserData(userSchedule);
                            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
                            homeFragment.updateTextViews(userSchedule);
                            //setUpNotifyUserToSleep();
                            Log.d("New Sleep Time",userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.NEW_SLEEP_TIME));
                        }
                    }
                });

        setUpDailyUpdater();
        //setUpNotifyUserToSleep();
        //setUpDailyAdviceNotif();



    }

    private void setUpNotifyUserToSleep(){
        if (userSchedule==null)
            return;
        Calendar currentRecommendedSleepTime = userSchedule.getRecommendedSleepTime();
        Calendar sleepTime = Calendar.getInstance();
        sleepTime.set(Calendar.HOUR,currentRecommendedSleepTime.get(Calendar.HOUR)-1);
        sleepTime.set(Calendar.MINUTE,currentRecommendedSleepTime.get(Calendar.MINUTE));
        sleepTime.set(Calendar.SECOND,0);

        Intent intent = new Intent(this, NotificationGoToSleepBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        boolean alarmUp = (PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!userSchedule.checkUserIsSleeping() && Calendar.getInstance().before(userSchedule.getRecommendedSleepTime()) && !alarmUp)
            alarmManager.setExact(AlarmManager.RTC,sleepTime.getTimeInMillis(),pendingIntent);
    }

    private void setUpNotifyUserToWakeup(){
        if(userSchedule==null)
            return;
        Calendar currentRecommendedWakeupTime = userSchedule.getRecommendedWakeupTime();
        Calendar wakeupTime = Calendar.getInstance();
        wakeupTime.set(Calendar.HOUR,currentRecommendedWakeupTime.get(Calendar.HOUR)-1);
        wakeupTime.set(Calendar.MINUTE,currentRecommendedWakeupTime.get(Calendar.MINUTE));
        wakeupTime.set(Calendar.SECOND,0);

        Intent intent = new Intent(this, NotificationWakeupBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC,wakeupTime.getTimeInMillis(),pendingIntent);
    }

    private void setUpDailyUpdater(){
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY,0);
        currentCalendar.set(Calendar.MINUTE,0);
        currentCalendar.set(Calendar.SECOND,1);

        Intent createUpdater = new Intent(this, UpdateBroadcastReceiver.class);
        PendingIntent pendingUpdater = PendingIntent.getBroadcast(this.getApplicationContext(),1,createUpdater,PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,currentCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingUpdater);
    }

    private void setUpRewardToday(){
        Intent rewardNotification = new Intent(this,NotificationRewardBroadcastReceiver.class);
        PendingIntent pendingRewardNotif = PendingIntent.getBroadcast(this,0,rewardNotification,PendingIntent.FLAG_IMMUTABLE);
    }

    private void setUpDailyAdviceNotif(){
        Calendar currentCalendar = Calendar.getInstance();
        if(currentCalendar.get(Calendar.HOUR_OF_DAY)>=9)
            currentCalendar.add(Calendar.DATE,1);
        currentCalendar.set(Calendar.HOUR_OF_DAY,9);
        currentCalendar.set(Calendar.MINUTE,0);
        currentCalendar.set(Calendar.SECOND,9);

        Intent notifIntent = new Intent(this, NotificationDailyAdviceBroadcastReceiver.class);
        PendingIntent pendingNotif = PendingIntent.getBroadcast(this.getApplicationContext(),1,notifIntent,PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC,currentCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingNotif);

    }

    public void showFunnyVideo(View view){
        String newID =  linksAdvicesManager.getTodayFunnyID();

        if(checkOnScheduleFlag()){
            Intent showFunnyVideoApp = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" +newID));
            Intent showFunnyVideoWeb = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + newID));
            try{
                startActivity(showFunnyVideoApp);
            }catch (ActivityNotFoundException e) {
                startActivity(showFunnyVideoWeb);
            }
        }else{
            //Make toast
        }
    }

    public void showCuteVideo(View view){
        String newID =  linksAdvicesManager.getTodayCuteID();

        if(checkOnScheduleFlag()){
            Intent showCuteVideoApp = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" +newID));
            Intent showCuteVideoWeb = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + newID));
            try{
                startActivity(showCuteVideoApp);
            }catch (ActivityNotFoundException e) {
                startActivity(showCuteVideoWeb);
            }
        }else{
            //Make toast
        }
    }

    public void showJoke(View view){
        Intent openJokePopup = new Intent(this, JokePopup.class);
        startActivity(openJokePopup);
    }



    public void switchMode(View view){
        if(!userSchedule.checkUserIsSleeping()){
            ((HomeFragment)homeFragment).switchToSleepMode(userSchedule);
            mainDataStorageHandler.saveUserData(userSchedule);
            ((HomeFragment)homeFragment).updateTextViews(userSchedule);
            //setUpNotifyUserToWakeup();
        } else {
            ((HomeFragment)homeFragment).switchToWakeupMode(userSchedule);
            mainDataStorageHandler.saveUserData(userSchedule);
            ((HomeFragment)homeFragment).updateTextViews(userSchedule);
            //setUpNotifyUserToSleep();
        }
    }


    public void createNewSchedulePopup(View view){
        launcher.launch(new Intent(this, ActivityNewSchedulePopup.class));
    }

    public UserDataSleepRecord getUserSchedule() {
        return userSchedule;
    }

    public DataStorageHandler getMainDataStorageHandler() {
        return mainDataStorageHandler;
    }

    public boolean checkOnScheduleFlag(){
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setup_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tip_of_the_day_btn:
                openTipOfTheDayPopup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openTipOfTheDayPopup(){
        startActivity(new Intent(this, TipOfTheDayPopup.class));
    }

    private boolean switchMainFragment(MenuItem item){
        switch(item.getItemId()){
            case R.id.home:
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,homeFragment).commit();
            return true;

            case R.id.rewards:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,rewardsFragment).commit();
                return true;

            case R.id.statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,statisticsFragment).commit();
                return true;
        }
        return false;
    }
}