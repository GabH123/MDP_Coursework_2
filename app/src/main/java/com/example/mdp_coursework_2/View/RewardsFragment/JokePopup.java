package com.example.mdp_coursework_2.View.RewardsFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mdp_coursework_2.Controller.DataStorageHandler;
import com.example.mdp_coursework_2.Controller.LinksAdvicesManager;
import com.example.mdp_coursework_2.R;

public class JokePopup extends AppCompatActivity {

    private DataStorageHandler dataStorageHandler;
    private LinksAdvicesManager linksAdvicesManager;

    private TextView jokeText;
    private final double POPUP_SIZE_FACTOR=0.85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm .heightPixels;

        getWindow().setLayout((int)(width*POPUP_SIZE_FACTOR),(int)(height*POPUP_SIZE_FACTOR));

        jokeText = (TextView) findViewById(R.id.jokeText);

        dataStorageHandler = new DataStorageHandler(this);
        linksAdvicesManager = dataStorageHandler.openLinkSharedPreferences();

        jokeText.setText(linksAdvicesManager.getTodayJoke());
    }



    public void closePopup(View view){
        finish();
    }
}