package com.example.mysmarthome.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mysmarthome.BuildConfig;
import com.example.mysmarthome.data.api.UploadVideoApi;
import com.example.mysmarthome.data.model.UploadVideoResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadVideoRepo {

    private UploadVideoApi uploadVideoApi;
    private MutableLiveData<UploadVideoResponse> responseLiveData;

    public UploadVideoRepo() {
        responseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        uploadVideoApi = new retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UploadVideoApi.class);

    }

    public void uploadVideo(String path, String name) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("filename", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), name);

        uploadVideoApi.uploadVideo(fileToUpload, filename)
                .enqueue(new Callback<UploadVideoResponse>() {
                    @Override
                    public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {
                        if (response.body() != null) {
                            responseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                        String error = t.getMessage();
                        UploadVideoResponse response = new UploadVideoResponse();
                        response.setSuccess(0);
                        response.setMessage(error);
                        responseLiveData.postValue(response);
                    }
                });
    }

    public LiveData<UploadVideoResponse> getUploadVideoResponseLiveData() {
        return responseLiveData;
    }
}