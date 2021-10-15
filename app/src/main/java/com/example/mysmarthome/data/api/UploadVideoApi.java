package com.example.mysmarthome.data.api;

import com.example.mysmarthome.data.model.UploadVideoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadVideoApi {
    @Multipart
    @POST("/uploadvideo/")
    Call<UploadVideoResponse> uploadVideo(@Part MultipartBody.Part file, @Part("filename") RequestBody name);
}
