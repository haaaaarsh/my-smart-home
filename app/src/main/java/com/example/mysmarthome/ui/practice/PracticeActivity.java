package com.example.mysmarthome.ui.practice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysmarthome.MySmartHomeApp;
import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityPracticeBinding;
import com.example.mysmarthome.ui.base.BaseActivity;

import java.util.List;

public class PracticeActivity extends BaseActivity<PracticeViewModel> implements PracticeNavigator {

    ActivityPracticeBinding binding;

    @NonNull
    @Override
    protected PracticeViewModel createViewModel() {
        PracticeViewModelFactory factory = new PracticeViewModelFactory();
        return ViewModelProviders.of(this, factory).get(PracticeViewModel.class);
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

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice);
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
        getSupportActionBar().setTitle(currentGesture);
    }
}