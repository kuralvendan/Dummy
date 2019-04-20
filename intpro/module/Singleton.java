package com.binary2quantum.android.intpro.module;

public class Singleton {

    public String SCREEN_TAG = "HOME";
    private static Singleton sSoleInstance;
    private Singleton(){}  //private constructor.

    public static Singleton getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new Singleton();
        } return sSoleInstance;
    }
}

