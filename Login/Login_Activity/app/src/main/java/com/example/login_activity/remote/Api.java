package com.example.login_activity.remote;

public class Api {
    public static final String BASE_URL = "https://teamdronex.com/users/sign_in";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
