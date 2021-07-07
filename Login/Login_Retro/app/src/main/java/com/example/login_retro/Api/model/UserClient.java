package com.example.login_retro.Api.model;

import android.util.Log;

import com.example.login_retro.Api.model.Login;
import com.example.login_retro.Api.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("auth/login")
    Call<User> login(@Body Login login);

    @GET("api/v1/missions")
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);
}