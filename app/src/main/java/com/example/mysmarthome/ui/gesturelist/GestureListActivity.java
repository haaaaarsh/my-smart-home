package com.example.mysmarthome.ui.gesturelist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityGestureListBinding;
import com.example.mysmarthome.databinding.ActivitySplashBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.ui.splash.SplashNavigator;
import com.example.mysmarthome.ui.splash.SplashViewModel;
import com.example.mysmarthome.ui.splash.SplashViewModelFactory;

import java.util.concurrent.TimeUnit;

public class GestureListActivity extends BaseActivity<GestureListViewModel> implements GestureListNavigator {

    ActivityGestureListBinding binding;

    @NonNull
    @Override
    protected GestureListViewModel createViewModel() {
        GestureListViewModelFactory factory = new GestureListViewModelFactory();
        return ViewModelProviders.of(this, factory).get(GestureListViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewModel.setNavigator(this);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gesture_list);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }
}