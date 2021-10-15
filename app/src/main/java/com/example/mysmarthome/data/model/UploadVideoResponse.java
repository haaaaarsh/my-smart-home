package com.example.mysmarthome.data.model;

import com.google.gson.annotations.SerializedName;

public class UploadVideoResponse {

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	public int getSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}