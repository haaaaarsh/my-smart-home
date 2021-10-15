package com.example.mysmarthome.ui.practice;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import com.example.mysmarthome.data.model.UploadVideoResponse;
import com.example.mysmarthome.data.repository.UploadVideoRepo;
import com.example.mysmarthome.ui.base.BaseViewModel;

public class PracticeViewModel extends BaseViewModel<PracticeNavigator> {

    private ObservableField<String> gesture = new ObservableField<>();
    private ObservableBoolean mIsLoading = new ObservableBoolean();
    private UploadVideoRepo uploadVideoRepo;
    private LiveData<UploadVideoResponse> uploadVideoResponseLiveData;

    public PracticeViewModel() {
        uploadVideoRepo = new UploadVideoRepo();
        uploadVideoResponseLiveData = uploadVideoRepo.getUploadVideoResponseLiveData();
    }

    public void record() {
        getNavigator().recordClick();
    }

    public void uploadRequest(String path, String name) {
        uploadVideoRepo.uploadVideo(path, name);
    }

    public void upload() {
        setIsLoading(true);
        getNavigator().uploadRequest();
    }

    public LiveData<UploadVideoResponse> getUploadVideoResponseLiveData() {
        return uploadVideoResponseLiveData;
    }

    public ObservableField<String> getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture.set(gesture);
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }
}
