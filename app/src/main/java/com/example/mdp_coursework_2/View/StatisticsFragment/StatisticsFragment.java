package com.example.mdp_coursework_2.View.StatisticsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mdp_coursework_2.R;
import com.google.android.material.tabs.TabLayout;

public class StatisticsFragment extends Fragment {

    final private int OVERVIEW_FRAGMENT_ID=0;
    final private int SLEEP_SCHEDULE_FRAGMENT_ID=1;

    TabLayout statisticsTabLayout;

    Fragment overviewFragment;
    Fragment sleepScheduleFragment;
    public StatisticsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        statisticsTabLayout = container.findViewById(R.id.statisticsTabLayout);

        overviewFragment = new StatisticsOverviewFragment();
        sleepScheduleFragment = new StatisticsSleepScheduleFragment();

        getChildFragmentManager().beginTransaction().replace(R.id.statisticsFrameLayout,overviewFragment).commit();

        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        statisticsTabLayout = (TabLayout) getView().findViewById(R.id.statisticsTabLayout);
        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeStatisticsFragment(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };

        statisticsTabLayout.addOnTabSelectedListener(listener);
    }

    private void changeStatisticsFragment(TabLayout.Tab tab){
        switch (tab.getPosition()){
            case OVERVIEW_FRAGMENT_ID:
                getChildFragmentManager().beginTransaction().replace(R.id.statisticsFrameLayout,overviewFragment).commit();
                break;

            case SLEEP_SCHEDULE_FRAGMENT_ID:
                getChildFragmentManager().beginTransaction().replace(R.id.statisticsFrameLayout,sleepScheduleFragment).commit();
                break;
        }
    }
}