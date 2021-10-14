package com.example.mysmarthome.ui.gesturelist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GestureListViewModelFactory implements ViewModelProvider.Factory {


    public GestureListViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GestureListViewModel.class)) {
            return (T) new GestureListViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
