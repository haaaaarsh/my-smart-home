package com.example.mysmarthome.ui.tutorial;

import androidx.databinding.ObservableField;

import com.example.mysmarthome.ui.base.BaseViewModel;

public class TutorialViewModel extends BaseViewModel<TutorialNavigator> {

    private ObservableField<String> gesture = new ObservableField<>();

    public TutorialViewModel() {

    }

    public void openPracticeActivity() {
        getNavigator().openPracticeActivity();
    }

    public void replay() {
        getNavigator().replay();
    }

    public ObservableField<String> getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture.set(gesture);
    }
}
