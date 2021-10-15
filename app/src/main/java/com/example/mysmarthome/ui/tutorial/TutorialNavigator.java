package com.example.mysmarthome.ui.tutorial;

import android.content.Context;

public interface TutorialNavigator {

    Context getActivityContext();

    void openPracticeActivity();

    void replay();
}
