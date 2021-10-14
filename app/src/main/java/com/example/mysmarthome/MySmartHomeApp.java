package com.example.mysmarthome;

import android.app.Application;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MySmartHomeApp extends Application {

    private static MySmartHomeApp mInstance;

    public static MySmartHomeApp getInstance() {
        return mInstance;
    }

    private Map<String, String> gestureMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setGestureMap();
    }

    private void setGestureMap() {
        gestureMap = new LinkedHashMap<>();
        gestureMap.put("None", "Please select a gesture");
        gestureMap.put("LightOn", "Turn On Light");
        gestureMap.put("LightOff", "Turn Off Light");
        gestureMap.put("FanOn", "Turn On Fan");
        gestureMap.put("FanOff", "Turn Off Fan");
        gestureMap.put("FanUp", "Increase Fan Speed");
        gestureMap.put("FanDown", "Decrease Fan Speed");
        gestureMap.put("SetThermo", "Set Thermostat to specified temperature");
        gestureMap.put("Num0", "Number Gesture 0");
        gestureMap.put("Num1", "Number Gesture 1");
        gestureMap.put("Num2", "Number Gesture 2");
        gestureMap.put("Num3", "Number Gesture 3");
        gestureMap.put("Num4", "Number Gesture 4");
        gestureMap.put("Num5", "Number Gesture 5");
        gestureMap.put("Num6", "Number Gesture 6");
        gestureMap.put("Num7", "Number Gesture 7");
        gestureMap.put("Num8", "Number Gesture 8");
        gestureMap.put("Num9", "Number Gesture 9");
    }

    public Map<String, String> getGestureMap() {
        return this.gestureMap;
    }
}