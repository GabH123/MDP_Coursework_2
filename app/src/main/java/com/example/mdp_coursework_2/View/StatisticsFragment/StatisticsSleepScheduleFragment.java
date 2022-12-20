package com.example.mdp_coursework_2.View.StatisticsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mdp_coursework_2.R;
import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.example.mdp_coursework_2.View.MainActivity;


public class StatisticsSleepScheduleFragment extends Fragment {
    TextView currentSleepScheduleText;
    TextView newSleepScheduleText;

    UserDataSleepRecord userSchedule;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_statistics_sleep_schedule, container, false);
        currentSleepScheduleText = (TextView) fragmentView.findViewById(R.id.currentSleepScheduleTime);
        newSleepScheduleText = (TextView) fragmentView.findViewById(R.id.newSleepScheduleTime);
        userSchedule = ((MainActivity)getActivity()).getUserSchedule();

        updateFragmentText(userSchedule);

        return fragmentView;
    }

    public void updateFragmentText(UserDataSleepRecord userSchedule){
        if(userSchedule!=null){
            currentSleepScheduleText.setText(userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.OLD_SLEEP_TIME)+" - "
                    +userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.OLD_WAKEUP_TIME));
            newSleepScheduleText.setText(userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.NEW_SLEEP_TIME)+" - "
                    +userSchedule.getTimeIn12HourFormat(UserDataSleepRecord.NEW_WAKEUP_TIME));
        } else{
            currentSleepScheduleText.setText("-:- - -:-");
            newSleepScheduleText.setText("-:- - -:-");
        }
    }
}