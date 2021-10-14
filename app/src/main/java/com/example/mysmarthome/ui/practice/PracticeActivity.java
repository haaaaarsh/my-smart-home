package com.example.mysmarthome.ui.practice;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityGestureListBinding;
import com.example.mysmarthome.databinding.ActivityPracticeBinding;
import com.example.mysmarthome.ui.base.BaseActivity;

import java.util.List;

public class PracticeActivity extends BaseActivity<PracticeViewModel> implements PracticeNavigator, AdapterView.OnItemSelectedListener {

    ActivityPracticeBinding binding;
    List<String> gestureNameList;

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
        getSupportActionBar().setTitle(R.string.title_practice);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            String gesture = gestureNameList.get(position);
            setCurrentGesture(gesture);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}