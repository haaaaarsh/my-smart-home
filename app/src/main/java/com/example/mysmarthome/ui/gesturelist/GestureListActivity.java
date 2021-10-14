package com.example.mysmarthome.ui.gesturelist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mysmarthome.MySmartHomeApp;
import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityGestureListBinding;
import com.example.mysmarthome.databinding.ActivitySplashBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.ui.practice.PracticeActivity;
import com.example.mysmarthome.ui.splash.SplashNavigator;
import com.example.mysmarthome.ui.splash.SplashViewModel;
import com.example.mysmarthome.ui.splash.SplashViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GestureListActivity extends BaseActivity<GestureListViewModel> implements GestureListNavigator, AdapterView.OnItemSelectedListener {

    ActivityGestureListBinding binding;
    List<String> gestureNameList;

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
        setSpinner();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gesture_list);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    private void setSpinner() {
        List<String> gestureList = new ArrayList<>(((MySmartHomeApp) getApplication()).getGestureMap().values());
        gestureNameList = new ArrayList<>(((MySmartHomeApp) getApplication()).getGestureMap().keySet());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gestureList.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            String gesture = gestureNameList.get(position);
            setCurrentGesture(gesture);
            openActivity(PracticeActivity.class);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}