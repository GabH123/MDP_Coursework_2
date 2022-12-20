package com.example.mdp_coursework_2.View.HomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mdp_coursework_2.R;
import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.example.mdp_coursework_2.View.MainActivity;

import java.util.Calendar;


public class HomeFragment extends Fragment {
    private UserDataSleepRecord userSchedule;

    private TextView wentToSleepText;
    private TextView goToSleepOrWakeupText;

    private TextView recommendedSleepTimeText;

    private Button homeMainBtn;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView =  inflater.inflate(R.layout.fragment_home, container, false);

        userSchedule = ((MainActivity)this.getActivity()).getUserSchedule();
        recommendedSleepTimeText = (TextView) fragmentView.findViewById(R.id.recommendedSleepTimeText);
        wentToSleepText = (TextView) fragmentView.findViewById(R.id.wentToSleepText);
        goToSleepOrWakeupText = (TextView) fragmentView.findViewById(R.id.goToSleepOrWakeUpText);
        homeMainBtn = (Button) fragmentView.findViewById(R.id.homeMainBtn);

        updateTextViews(userSchedule);

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTextViews(userSchedule);
    }


    public void updateSleepTimeText(UserDataSleepRecord userSchedule){
        if (userSchedule!=null) {
            Log.d("Schedule","Test");
             recommendedSleepTimeText.setText(userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.RECOMMENDED_SLEEP_TIME));
        }
    }

    public void updateTextViews(UserDataSleepRecord userSchedule){
        if (userSchedule!=null) {
            if (userSchedule.checkUserIsSleeping()) {
                String newTextTop = "Went to sleep at: " + userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.LATEST_SLEEP_TIME);
                String newTextMiddle = "You should wake up at: ";
                wentToSleepText.setText(newTextTop);
                wentToSleepText.setVisibility(View.VISIBLE);
                goToSleepOrWakeupText.setText(newTextMiddle);
                recommendedSleepTimeText.setText(userSchedule.convert24To12HourFormat(userSchedule.getRecommendedWakeupTime()));
                homeMainBtn.setText("Wake up");
            } else {
                wentToSleepText.setVisibility(View.INVISIBLE);
                String newTextMiddle = "You should go to sleep at: ";
                goToSleepOrWakeupText.setText(newTextMiddle);
                recommendedSleepTimeText.setText(userSchedule.convert24To12HourFormat(userSchedule.getRecommendedSleepTime()));
                homeMainBtn.setText("Going to sleep");
            }
        } else{

        }
    }

    public void switchToSleepMode(UserDataSleepRecord record){
        record.userWentToSleep(Calendar.getInstance());

    }

    public void switchToWakeupMode(UserDataSleepRecord record){
        record.userWokeUp(Calendar.getInstance());

    }


}