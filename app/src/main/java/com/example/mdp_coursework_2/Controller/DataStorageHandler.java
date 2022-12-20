package com.example.mdp_coursework_2.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mdp_coursework_2.Model.SleepScheduler.UserDataSleepRecord;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataStorageHandler {
    final String USER_DATA_FILE_NAME = "UserSleepScheduleData.bin";
    final String MAIN_ACTIVITY_SHARED_PREFERENCES_NAME = "MainActivitySharedPreference";
    final String LINK_DATA_MANAGER_NAME = "LinkDataManager";
    Context activityContext;

    public DataStorageHandler(Context context) {
        this.activityContext=context;
    }

    public LinksAdvicesManager openLinkSharedPreferences(){
        SharedPreferences mainActivityData = activityContext.getSharedPreferences(MAIN_ACTIVITY_SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);

        if(mainActivityData.contains(LINK_DATA_MANAGER_NAME)) {
            Gson gson = new Gson();
            String json = mainActivityData.getString(LINK_DATA_MANAGER_NAME, "");
            return gson.fromJson(json, LinksAdvicesManager.class);
        }
        else{
            LinksAdvicesManager newLinksAdvicesManager = new LinksAdvicesManager();
            newLinksAdvicesManager.initialiseInitialData();
            return newLinksAdvicesManager;
        }
    }

    public void saveLinkSharedPreferences(LinksAdvicesManager linksAdvicesManager){
        SharedPreferences mainActivityData = activityContext.getSharedPreferences(MAIN_ACTIVITY_SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor mainActivityEditor = mainActivityData.edit();

        Gson gson = new Gson();
        String json = gson.toJson(linksAdvicesManager);
        mainActivityEditor.putString(LINK_DATA_MANAGER_NAME, json);
        mainActivityEditor.commit();
    }

    public UserDataSleepRecord openUserData(){
        FileInputStream fileInput;
        try{
            fileInput = activityContext.openFileInput(USER_DATA_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInput);
            UserDataSleepRecord userData = (UserDataSleepRecord) objectInputStream.readObject();
            fileInput.close();
            objectInputStream.close();
            return userData;
        }catch (FileNotFoundException e){
            return null;
        }catch (IOException e){
            return null;
        }
        catch (ClassNotFoundException e){
            return null;
        }


    }

    public void saveUserData(UserDataSleepRecord data){
        try {
            FileOutputStream fileOutputStream = activityContext.openFileOutput(USER_DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
