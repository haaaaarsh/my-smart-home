package com.example.mysmarthome.ui.practice;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PracticeViewModelFactory implements ViewModelProvider.Factory {


    public PracticeViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PracticeViewModel.class)) {
            return (T) new PracticeViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
