package com.example.mysmarthome.ui.gesturelist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.mysmarthome.MySmartHomeApp;
import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityGestureListBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.ui.practice.PracticeActivity;
import com.example.mysmarthome.ui.tutorial.TutorialActivity;

import java.util.ArrayList;
import java.util.List;

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
        viewModel.setNavigator(this);
        setSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.spinner.setSelection(0);
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
            openTutorialActivityForResult();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void openTutorialActivityForResult() {
        Intent intent = new Intent(this, TutorialActivity.class);
        tutorialActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> tutorialActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int success = data.getIntExtra("success", 0);
                        String message = data.getStringExtra("message");
                        if (success == 1) {
                            showSnackbar(message, Color.MAGENTA, Color.WHITE);
                        } else if (success == 0) {
                            showSnackbar(message, Color.RED, Color.WHITE);
                        }
                    }
                }
            });
}