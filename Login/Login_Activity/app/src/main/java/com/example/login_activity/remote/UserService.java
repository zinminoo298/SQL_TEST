package com.example.login_activity.remote;

import com.example.login_activity.model.ResObj;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public abstract class UserService {
    @GET("sign_in/{email}/{password}")
    abstract Call sign_in(@Path("emial") String email, @Path("password") String password);
}
