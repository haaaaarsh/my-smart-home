package com.example.mysmarthome.ui.tutorial;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysmarthome.MySmartHomeApp;
import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityTutorialBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.ui.practice.PracticeActivity;

public class TutorialActivity extends BaseActivity<TutorialViewModel> implements TutorialNavigator {

    ActivityTutorialBinding binding;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 0x01;

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
        if (checkPermission())
            openPracticeActivityForResult();
    }

    private void openPracticeActivityForResult() {
        Intent intent = new Intent(this, PracticeActivity.class);
        practiceActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> practiceActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int success = data.getIntExtra("success", 0);
                        String message = data.getStringExtra("message");
                        if (success == 1) {
                            Intent intent = new Intent();
                            intent.putExtra("success", success);
                            intent.putExtra("message", message);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else if (success == 0) {
                            showSnackbar(message, Color.RED, Color.WHITE);
                        }
                    }
                }
            });


    @Override
    public void replay() {
        binding.video.start();
        binding.replay.setVisibility(View.GONE);
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
        binding.video.requestFocus();
        binding.video.start();
        binding.video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.replay.setVisibility(View.VISIBLE);
            }
        });
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

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openPracticeActivityForResult();
                break;
        }
    }
}