package com.example.mdp_coursework_2.Model;

import java.util.ArrayList;

public class LinksAdvices {
    ArrayList<String> funnyYoutubeID;
    ArrayList<String> cuteYoutubeID;
    ArrayList<String> jokes;
    ArrayList<String> advices;


    public LinksAdvices() {
        funnyYoutubeID = new ArrayList<String>();
        cuteYoutubeID = new ArrayList<String>();
        jokes = new ArrayList<String>();
        advices = new ArrayList<String>();
    }

    public void addFunnyYoutubeID(String string){
        funnyYoutubeID.add(string);
    }

    public void addCuteYoutubeID(String string){
        cuteYoutubeID.add(string);
    }

    public void addJoke(String string){
        jokes.add(string);
    }

    public void addAdvice(String string){
        advices.add(string);
    }

    public ArrayList<String> getFunnyYoutubeIDList() {
        return funnyYoutubeID;
    }

    public ArrayList<String> getCuteYoutubeIDList() {
        return cuteYoutubeID;
    }

    public ArrayList<String> getJokesList() {
        return jokes;
    }

    public ArrayList<String> getAdvicesList() {
        return advices;
    }
}
