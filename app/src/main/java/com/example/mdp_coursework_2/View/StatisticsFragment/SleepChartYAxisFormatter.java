package com.example.mdp_coursework_2.View.StatisticsFragment;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class SleepChartYAxisFormatter extends ValueFormatter {
    static private final String[] timeStamp={"8PM","10PM","Next day 12AM","2AM","4AM","6AM","8AM","10AM","12PM","2PM","4PM","6PM","8PM"};

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        super.getAxisLabel(value, axis);
        return timeStamp[(int) value];
    }

    static public int getTimeStampCount(){
        return timeStamp.length;
    }

}
