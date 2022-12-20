package com.example.mdp_coursework_2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mdp_coursework_2.Controller.DataStorageHandler;
import com.example.mdp_coursework_2.Controller.LinksAdvicesManager;
import com.example.mdp_coursework_2.R;

public class TipOfTheDayPopup extends AppCompatActivity {

    private TextView adviceText;

    private DataStorageHandler dataStorageHandler;
    private LinksAdvicesManager linksAdvicesManager;

    private final double POPUP_SIZE_FACTOR=0.85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_of_the_day_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm .heightPixels;

        getWindow().setLayout((int)(width*POPUP_SIZE_FACTOR),(int)(height*POPUP_SIZE_FACTOR));

        dataStorageHandler = new DataStorageHandler(this);
        linksAdvicesManager = dataStorageHandler.openLinkSharedPreferences();

        adviceText = findViewById(R.id.adviceText);
        adviceText.setText(linksAdvicesManager.getTodayAdvice());

    }

    public void getNextRandomAdvice(View view){
        adviceText.setText(linksAdvicesManager.getNextRandomAdvice());
    }


    public void closeTipPopup(View view){
        finish();
    }
}