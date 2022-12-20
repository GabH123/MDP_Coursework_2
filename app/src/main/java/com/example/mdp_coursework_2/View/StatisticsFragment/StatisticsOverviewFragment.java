package com.example.mdp_coursework_2.View.StatisticsFragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mdp_coursework_2.R;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;

public class StatisticsOverviewFragment extends Fragment {

    CandleStickChart sleepStatisticsChart;

    public StatisticsOverviewFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        sleepStatisticsChart = getView().findViewById(R.id.sleepStatisticsChart);
        setupChart();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupChart(){
        sleepStatisticsChart.setDrawBorders(false);

        sleepStatisticsChart.setBorderColor(getResources().getColor(R.color.black));

        YAxis yAxis = sleepStatisticsChart.getAxisLeft();
        YAxis rightAxis = sleepStatisticsChart.getAxisRight();
        rightAxis.setEnabled(false);

        sleepStatisticsChart.requestDisallowInterceptTouchEvent(true);
        sleepStatisticsChart.setPinchZoom(false);
        sleepStatisticsChart.setDoubleTapToZoomEnabled(false);

        yAxis.setValueFormatter(new SleepChartYAxisFormatter());
        yAxis.setGranularity(1);
        yAxis.setLabelCount(SleepChartYAxisFormatter.getTimeStampCount(),false);

        XAxis xAxis = sleepStatisticsChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(6);

        Legend l = sleepStatisticsChart.getLegend();
        l.setEnabled(false);


        resetChart();
    }

    public void resetChart(){
        ArrayList<CandleEntry> yValsCandleStick= new ArrayList<CandleEntry>();

        yValsCandleStick.add(new CandleEntry(0, (float)1, (float)1, (float)3, (float)0));
        yValsCandleStick.add(new CandleEntry(1, (float)3, (float)3, (float)4, (float)3));
        yValsCandleStick.add(new CandleEntry(2, (float)6,  (float)6, (float)7, (float)6));
        yValsCandleStick.add(new CandleEntry(3, (float)9, (float)9, (float)11, (float)8.5));

        YAxis yAxis = sleepStatisticsChart.getAxisLeft();

        yAxis.setAxisMinimum(0);

        CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet 1");

        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(R.color.black));
        set1.setShadowWidth(0.8f);
        set1.setDecreasingColor(getResources().getColor(R.color.purple_700));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(getResources().getColor(R.color.teal_200));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.LTGRAY);

        CandleData data = new CandleData(set1);

        sleepStatisticsChart.setData(data);
        sleepStatisticsChart.invalidate();
    }
}