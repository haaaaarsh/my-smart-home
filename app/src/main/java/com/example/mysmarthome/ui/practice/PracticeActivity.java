package com.example.mysmarthome.ui.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.daasuu.camerarecorder.CameraRecordListener;
import com.daasuu.camerarecorder.CameraRecorder;
import com.daasuu.camerarecorder.CameraRecorderBuilder;
import com.daasuu.camerarecorder.LensFacing;
import com.example.mysmarthome.MySmartHomeApp;
import com.example.mysmarthome.R;
import com.example.mysmarthome.databinding.ActivityPracticeBinding;
import com.example.mysmarthome.ui.base.BaseActivity;
import com.example.mysmarthome.utils.SampleGLView;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PracticeActivity extends BaseActivity<PracticeViewModel> implements PracticeNavigator {

    ActivityPracticeBinding binding;

    private SampleGLView sampleGLView;
    protected CameraRecorder cameraRecorder;
    private String filepath;
    protected LensFacing lensFacing = LensFacing.FRONT;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    CountDownTimer cTimer;
    private int practiceNum = 1;

    @NonNull
    @Override
    protected PracticeViewModel createViewModel() {
        PracticeViewModelFactory factory = new PracticeViewModelFactory();
        return ViewModelProviders.of(this, factory).get(PracticeViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setDataBindings();
        viewModel.setNavigator(this);
        setToolBar();
        setViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
        cancelTimer();
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

    @Override
    public void onError(String message) {
        showSnackbar(message, Color.RED, Color.WHITE);
    }

    @Override
    public void onSuccess(String message) {
        showSnackbar(message, Color.MAGENTA, Color.WHITE);
    }

    @Override
    public void recordClick() {
        File file;
        String filename = String.format("%s_PRACTICE_%d.mp4", getCurrentGesture(), practiceNum);
        practiceNum++;
        file = new File(this.getCacheDir(), filename);
        filepath = file.getAbsolutePath();
        cameraRecorder.start(filepath);
        binding.btnRecord.setVisibility(View.GONE);
        if (binding.btnUpload.getVisibility() == View.VISIBLE)
            binding.btnUpload.setVisibility(View.GONE);
        startTimer();
    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (cameraRecorder != null) {
            cameraRecorder.stop();
            cameraRecorder.release();
            cameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.rootView)).removeView(sampleGLView);
            sampleGLView = null;
        }
    }


    private void setUpCameraView() {
        runOnUiThread(() -> {
            FrameLayout frameLayout = findViewById(R.id.rootView);
            frameLayout.removeAllViews();
            sampleGLView = null;
            sampleGLView = new SampleGLView(getApplicationContext());
            sampleGLView.setTouchListener((event, width, height) -> {
                if (cameraRecorder == null) return;
                cameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
            });
            frameLayout.addView(sampleGLView);
        });
    }


    private void setUpCamera() {
        setUpCameraView();

        cameraRecorder = new CameraRecorderBuilder(this, sampleGLView)
                .recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
                        runOnUiThread(() -> {

                        });
                    }

                    @Override
                    public void onRecordComplete() {
//                        exportMp4ToGallery(getApplicationContext(), filepath);
                    }

                    @Override
                    public void onRecordStart() {

                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("CameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {

                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .mute(true)
                .build();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraRecorder.switchFlashMode();
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }

    void startTimer() {
        binding.timer.setVisibility(View.VISIBLE);
        cTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS.toMillis(1)) {
            public void onTick(long millisUntilFinished) {
                String seconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                binding.timer.setText(seconds.length() > 1 ? "00:" + seconds : "00:" + "0" + seconds);
            }

            public void onFinish() {
                cameraRecorder.stop();
                binding.btnRecord.setVisibility(View.VISIBLE);
                binding.btnUpload.setVisibility(View.VISIBLE);
                binding.timer.setVisibility(View.GONE);
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }
}