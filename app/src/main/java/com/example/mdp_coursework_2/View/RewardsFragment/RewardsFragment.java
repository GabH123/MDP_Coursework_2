package com.example.mdp_coursework_2.View.RewardsFragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.example.mdp_coursework_2.R;
import com.example.mdp_coursework_2.View.MainActivity;

import org.w3c.dom.Text;

public class RewardsFragment extends Fragment {

    private Button showFunnyVideoBtn;
    private Button showCuteVideoBtn;
    private Button tellJokeBtn;
    private TextView onTimeOrNotText;
    private ImageView warningImage;

    private UserDataSleepRecord userData;

    public RewardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_rewards, container, false);
        showFunnyVideoBtn = fragmentView.findViewById(R.id.showFunnyVideoBtn);
        showCuteVideoBtn = fragmentView.findViewById(R.id.showCuteVideoBtn);
        tellJokeBtn = fragmentView.findViewById(R.id.tellJokeBtn);

        onTimeOrNotText = fragmentView.findViewById(R.id.onTimeOrNotText);
        warningImage = fragmentView.findViewById(R.id.warningImage);

        userData = ((MainActivity)getActivity()).getUserSchedule();

        enableButton();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        enableButton();
    }


    private void enableButton( ){
        if (userData==null)
            return;
        if (userData.didWentToSleepOnTime()){
            showFunnyVideoBtn.setVisibility(View.VISIBLE);
            showCuteVideoBtn.setVisibility(View.VISIBLE);
            tellJokeBtn.setVisibility(View.VISIBLE);
            onTimeOrNotText.setVisibility(View.INVISIBLE);
            warningImage.setVisibility(View.INVISIBLE);
        } else {
            showFunnyVideoBtn.setVisibility(View.INVISIBLE);
            showCuteVideoBtn.setVisibility(View.INVISIBLE);
            tellJokeBtn.setVisibility(View.INVISIBLE);
            onTimeOrNotText.setVisibility(View.VISIBLE);
            warningImage.setVisibility(View.VISIBLE);
        }
    }


}