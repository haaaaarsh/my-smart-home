package com.example.mysmarthome.ui.tutorial;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TutorialViewModelFactory implements ViewModelProvider.Factory {


    public TutorialViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TutorialViewModel.class)) {
            return (T) new TutorialViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
