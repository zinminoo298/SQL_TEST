package com.example.login_retro.Api.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StateSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_retro.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.login_retro.Api.model.MainActivity.token;
import static com.example.login_retro.Api.model.Track_Drone.*;

public class Delivery_Drone extends AppCompatActivity implements View.OnClickListener {


    public Delivery_Drone(){
        this.getDetail();
    }

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://teamdronex.com/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    TextView Name_Detail;
    TextView Status_Detail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__drone);

        Name_Detail = findViewById(R.id.Name_Detail);
        Status_Detail = findViewById(R.id.Status_Detail);
        findViewById(R.id.btn_track).setOnClickListener(this);
        findViewById(R.id.btn_mission).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
        findViewById(R.id.btn_web).setOnClickListener(this);
        findViewById(R.id.btn_ongoing).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_track:
                track();
                break;

            case R.id.btn_mission:
                mission();
                break;

            case R.id.btn_list:
                mission_list();
                break;

            case R.id.btn_web:
                web();
                break;

            case R.id.btn_ongoing:
                ongoing();
                break;
        }
    }
    private static String name;
    private static String status;



//    MainActivity mainActivity = new MainActivity();
//    String token = mainActivity.getToken();

    private void getDetail() {
        Log.e("name","name"+token);
        Call<Drone_Details> call = userClient.getDetail(token);


        call.enqueue(new Callback<Drone_Details>() {
            @Override
            public void onResponse(Call<Drone_Details> call, Response<Drone_Details> response) {

                if (response.isSuccessful()) {
                    
                    Toast.makeText(Delivery_Drone.this, response.body().getName(), Toast.LENGTH_SHORT).show();
                    name = response.body().getName();
                    status = response.body().getStatus();
                    Log.e("Lat","INSIDE getLocation");
                    Log.e("Lat","status"+ status);
                    Log.e("name","name"+name);

                    Name_Detail.setText("Name : "+name);
                    Status_Detail.setText("Status : "+status);

                } else {
                    Toast.makeText(Delivery_Drone.this, "login not correct", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Drone_Details> call, Throwable t) {
                Toast.makeText(Delivery_Drone.this, "error", Toast.LENGTH_SHORT).show();

            }

        });

    }


    private void track(){
        Intent intent = new Intent(Delivery_Drone.this, Track_Drone.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        Track_Drone.getLocation();
    }

    private void mission(){
        Intent intent = new Intent(Delivery_Drone.this, Create_Mission.class);
        startActivity(intent);
    }

    private void mission_list(){
        Intent intent = new Intent(Delivery_Drone.this, Missions.class);
        startActivity(intent);
    }
    private void web(){
        Intent intent = new Intent(Delivery_Drone.this, Web.class);
        startActivity(intent);
    }
    private void ongoing(){
        Intent intent = new Intent(Delivery_Drone.this, Ongoing_Mission.class);
        startActivity(intent);
    }
}
