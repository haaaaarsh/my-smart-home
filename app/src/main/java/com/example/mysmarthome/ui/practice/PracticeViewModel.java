package com.example.mysmarthome.ui.practice;

import androidx.databinding.ObservableField;

import com.example.mysmarthome.ui.base.BaseViewModel;

public class PracticeViewModel extends BaseViewModel<PracticeNavigator> {

    private ObservableField<String> gesture = new ObservableField<>();

    public PracticeViewModel() {

    }

    public void record() {
        getNavigator().recordClick();
    }

    public void upload() {

    }

    public ObservableField<String> getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture.set(gesture);
    }
}
