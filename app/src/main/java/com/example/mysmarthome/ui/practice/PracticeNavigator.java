package com.example.mysmarthome.ui.practice;

import android.content.Context;

public interface PracticeNavigator {

    Context getActivityContext();

    void onError(String message);

    void onSuccess(String message);

    void recordClick();

}
