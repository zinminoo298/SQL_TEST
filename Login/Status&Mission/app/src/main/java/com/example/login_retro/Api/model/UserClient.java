package com.example.login_retro.Api.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserClient {

//    @POST("auth/login")
//    Call<User> login(@Body Login login);

    @GET("api/v1/users_mission")
    Call<List<Mission_Class>> getName(@Query("id") int id,
                                      @Header("Authorization") String authToken);

    @GET("api/v1/users_mission_last")
    Call<Execute_Mission_Class> getLast(@Query("id") int id,
                                      @Header("Authorization") String authToken);

    @GET("api/v1/ongoing_mission")
    Call<Ongoing_Mission_Class> getOngoing(@Query("id") int id,
                                        @Header("Authorization") String authToken);

    @GET("api/v1/missions")
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);

    @FormUrlEncoded
    @POST("auth/login/")
    Call<User> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("api/v1/drone?id=2")
    Call<Drone_Details> getDetail(@Header("Authorization") String authToken);

    @GET("api/v1/last_navlog?id=2")
    Call<Location> getLocation ();

    @FormUrlEncoded
    @POST("api/v1/missions")
    Call<Create_Mission_Client> mission(
            @Header("Authorization") String authToken,
            @Field("name") String name,
            @Field("weight") String weight,
            @Field("drone_id") int drone_id,
            @Field("location_id") Object location_id,
            @Field("status") String status,
            @Field("user_id") int user_id,
            @Field("mission_type") String mission_type
    );

    @PATCH("api/v1/mission_status_change")
    Call<Execute_Mission_Class> execute(
            @Header("Authorization") String authToken,
            @Query("drone_status") String drone_status,
            @Query("id") int id,
            @Query("mission_status") String mission_status

    );


}