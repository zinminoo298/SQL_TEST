package com.example.login_retro.Api.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import static com.example.login_retro.Api.model.MainActivity.user_id;

public class Ongoing_Mission extends AppCompatActivity implements View.OnClickListener {

    public Ongoing_Mission() {
        this.Request();
    }

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://teamdronex.com/").addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    private static String name;
    private static String drone_id;
    private static String weight;
    private static String type;
    TextView Mission_Name;
    TextView Drone_Id;
    TextView Item_Weight;
    TextView Mission_Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing__mission);

        Mission_Name = findViewById(R.id.name);
        Drone_Id = findViewById(R.id.drone);
        Item_Weight = findViewById(R.id.weight);
        Mission_Type = findViewById(R.id.type);

        findViewById(R.id.btn_terminate).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_terminate:
                terminate();
                break;
        }
    }

    public void Request() {
        Call<Ongoing_Mission_Class> call = userClient.getOngoing(user_id, token);
        call.enqueue(new Callback<Ongoing_Mission_Class>() {
            @Override
            public void onResponse(Call<Ongoing_Mission_Class> call, Response<Ongoing_Mission_Class> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(Ongoing_Mission.this, response.body().getMission_name(), Toast.LENGTH_SHORT).show();
                    name = response.body().getMission_name();
                    drone_id = response.body().getDrone_id();
                    weight = response.body().getItem_weight();
                    type = response.body().getMissin_type();
                    Log.e("Lat", "INSIDE getLocation");
                    Log.e("name", "name" + name);

                    Mission_Name.setText("Name : " + name);
                    Drone_Id.setText("Drone ID : " + drone_id);
                    Item_Weight.setText("Weight : " + weight);
                    Mission_Type.setText("Mission type : " + type);

                    if(Mission_Name.getText().toString().isEmpty())
                    {
                        Toast.makeText(Ongoing_Mission.this, "There is no ongoing mission", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Ongoing_Mission.this,Delivery_Drone.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Ongoing_Mission.this, "Last Mission On Screen Now", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Ongoing_Mission_Class> call, Throwable t) {
                Toast.makeText(Ongoing_Mission.this, "error", Toast.LENGTH_SHORT).show();

            }

        });
    }
    public void terminate(){
        Intent intent = new Intent(Ongoing_Mission.this,Delivery_Drone.class);
        startActivity(intent);
    }
}
