package com.example.mdp_coursework_2.Controller;

import com.example.mdp_coursework_2.Model.LinksAdvices;

public class LinksAdvicesManager {


    LinksAdvices listOfAvailableIDsAndAdvices;

    int funnyIDOfTheDayIndex;
    int cuteIDOfTheDayIndex;
    int jokeOfTheDayIndex;
    int adviceOfTheDayIndex;


    public LinksAdvicesManager() {

        listOfAvailableIDsAndAdvices = new LinksAdvices();
        funnyIDOfTheDayIndex = 0;
        cuteIDOfTheDayIndex = 0;
        jokeOfTheDayIndex = 0;
        adviceOfTheDayIndex = 0;

    }

    public void randomiseIndex(){
        funnyIDOfTheDayIndex = (int)(listOfAvailableIDsAndAdvices.getFunnyYoutubeIDList().size()*Math.random());
        cuteIDOfTheDayIndex = (int)(listOfAvailableIDsAndAdvices.getCuteYoutubeIDList().size()*Math.random());
        jokeOfTheDayIndex = (int)(listOfAvailableIDsAndAdvices.getJokesList().size()*Math.random());
        adviceOfTheDayIndex = (int)(listOfAvailableIDsAndAdvices.getAdvicesList().size()*Math.random());
    }

    public String getNextRandomAdvice(){
        int newIndex = (int)(listOfAvailableIDsAndAdvices.getAdvicesList().size()*Math.random());
        if(newIndex==adviceOfTheDayIndex)
            newIndex = (int)(listOfAvailableIDsAndAdvices.getAdvicesList().size()*Math.random());
        adviceOfTheDayIndex = newIndex;
        return listOfAvailableIDsAndAdvices.getAdvicesList().get(newIndex);

    }

    public LinksAdvices getListOfAvailableIDsAndAdvices() {
        return listOfAvailableIDsAndAdvices;
    }


    public String getTodayFunnyID(){
        return listOfAvailableIDsAndAdvices.getFunnyYoutubeIDList().get(funnyIDOfTheDayIndex);
    }

    public String getTodayCuteID(){
        return listOfAvailableIDsAndAdvices.getCuteYoutubeIDList().get(cuteIDOfTheDayIndex);
    }

    public String getTodayJoke(){
        return listOfAvailableIDsAndAdvices.getJokesList().get(jokeOfTheDayIndex);
    }

    public String getTodayAdvice(){
        return listOfAvailableIDsAndAdvices.getAdvicesList().get(adviceOfTheDayIndex);
    }

    public void initialiseInitialData(){
        listOfAvailableIDsAndAdvices.getFunnyYoutubeIDList().add("1VuMdLm0ccU");
        listOfAvailableIDsAndAdvices.getCuteYoutubeIDList().add("69SzfigTO7M");
        listOfAvailableIDsAndAdvices.getCuteYoutubeIDList().add("jDhRkZFgqe4");
        listOfAvailableIDsAndAdvices.getJokesList().add("I searched a list of ten puns to find one that made me laugh....\n" +
                "\n" +
                "No pun in ten did");
        listOfAvailableIDsAndAdvices.getAdvicesList().add("It takes about 7 hours for caffeine to be removed from the body. As such, you " +
                "should stop drinking coffee around 2 to 3 PM to help you have a good sleep.");
        listOfAvailableIDsAndAdvices.getAdvicesList().add("Melatonin is the hormone that make you sleepy. However, it can inhibited by" +
                "bright light. As such, you should turn off any source of bright light a few hours " +
                "before going to sleep. If you need to work, you should use a night light.");
        listOfAvailableIDsAndAdvices.getAdvicesList().add("Blue light from phone screens can prevent you from feeling sleepy." +
                "Therefore, you should lower your brightness and turn on the blue filter (or night" +
                "light) on your phone.");
        listOfAvailableIDsAndAdvices.getAdvicesList().add("A cold environment can increase your likeliness to fall asleep and improve" +
                "your sleep quality. As such, you should keep the room ventilated and turn on the" +
                "air conditioner if your room is too hot.");
    }


}
