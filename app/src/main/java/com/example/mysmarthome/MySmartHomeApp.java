package com.example.mysmarthome;

import android.app.Application;

public class MySmartHomeApp extends Application {

    private static MySmartHomeApp mInstance;
    public static MySmartHomeApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}