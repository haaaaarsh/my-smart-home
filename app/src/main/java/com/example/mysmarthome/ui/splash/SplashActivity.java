package com.example.mysmarthome.ui.splash;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivitySplashBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.ui.gesturelist.GestureListActivity;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends BaseActivity<SplashViewModel> implements SplashNavigator{

    ActivitySplashBinding binding;

    @NonNull
    @Override
    protected SplashViewModel createViewModel() {
        SplashViewModelFactory factory = new SplashViewModelFactory();
        return ViewModelProviders.of(this, factory).get(SplashViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewModel.setNavigator(this);
        decideNextActivity();
        getSupportActionBar().hide();
    }

    private void decideNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity(GestureListActivity.class);
                finish();
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }
}