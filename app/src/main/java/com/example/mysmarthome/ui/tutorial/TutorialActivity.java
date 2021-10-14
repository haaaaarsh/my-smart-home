package com.example.mysmarthome.ui.tutorial;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysmarthome.MySmartHomeApp;
import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityTutorialBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.ui.practice.PracticeActivity;

import java.util.List;

public class TutorialActivity extends BaseActivity<TutorialViewModel> implements TutorialNavigator {

    ActivityTutorialBinding binding;
    List<String> gestureNameList;

    @NonNull
    @Override
    protected TutorialViewModel createViewModel() {
        TutorialViewModelFactory factory = new TutorialViewModelFactory();
        return ViewModelProviders.of(this, factory).get(TutorialViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataBindings();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewModel.setNavigator(this);
        setToolBar();
        setViews();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void openPracticeActivity() {
        openActivity(PracticeActivity.class);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_tutorial);
    }

    private void setViews() {
        String currentGesture = ((MySmartHomeApp) getApplication()).getGestureMap().get(getCurrentGesture());
        viewModel.setGesture(currentGesture);
        prepVideo();
    }

    private void prepVideo() {
        int videoRes = getVideoResource();
        binding.video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoRes));
        binding.video.setMediaController(new MediaController(this));
        binding.video.requestFocus();
        binding.video.start();
    }

    private int getVideoResource() {
        int res = -1;
        String gesture = getCurrentGesture();
        switch (gesture) {
            case "LightOn":
                res = R.raw.light_on;
                break;
            case "LightOff":
                res = R.raw.light_off;
                break;
            case "FanOn":
                res = R.raw.fan_on;
                break;
            case "FanOff":
                res = R.raw.fan_off;
                break;
            case "FanUp":
                res = R.raw.increase_fan_speed;
                break;
            case "FanDown":
                res = R.raw.decrease_fan_speed;
                break;
            case "SetThermo":
                res = R.raw.set_thermo;
                break;
            case "Num0":
                res = R.raw.h_0;
                break;
            case "Num1":
                res = R.raw.h_1;
                break;
            case "Num2":
                res = R.raw.h_2;
                break;
            case "Num3":
                res = R.raw.h_3;
                break;
            case "Num4":
                res = R.raw.h_4;
                break;
            case "Num5":
                res = R.raw.h_5;
                break;
            case "Num6":
                res = R.raw.h_6;
                break;
            case "Num7":
                res = R.raw.h_7;
                break;
            case "Num8":
                res = R.raw.h_8;
                break;
            case "Num9":
                res = R.raw.h_9;
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }
}