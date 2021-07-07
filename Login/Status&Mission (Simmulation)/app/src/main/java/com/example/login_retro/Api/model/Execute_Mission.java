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

public class Execute_Mission extends AppCompatActivity implements View.OnClickListener {
    public Execute_Mission() {
        this.request();
    }
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://teamdronex.com/").addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    private static String name;
    public static String drone_id;
    public  static int id;       //mission id
    private static String weight;
    private static String type;
    TextView Mission_Name;
    TextView Drone_Id;
    TextView Item_Weight;
    TextView Mission_Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute__mission);


        Mission_Name = findViewById(R.id.Name);
        Drone_Id = findViewById(R.id.Drone);
        Item_Weight = findViewById(R.id.Weight);
        Mission_Type = findViewById(R.id.Type);

        findViewById(R.id.btn_execute).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_execute:
                execute();
                break;
        }
    }
public void request(){
    Call<Execute_Mission_Class> call = userClient.getLast(user_id,token);
    call.enqueue(new Callback<Execute_Mission_Class>() {
        @Override
        public void onResponse(Call<Execute_Mission_Class> call, Response<Execute_Mission_Class> response) {

            if (response.isSuccessful()) {

                Toast.makeText(Execute_Mission.this, response.body().getName(), Toast.LENGTH_SHORT).show();
                name = response.body().getName();
                drone_id = response.body().getDrone_id();
                weight = response.body().getWeight();
                type = response.body().getType();
                id = response.body().getMission_id();
                Log.e("Lat","INSIDE getLocation");
                Log.e("name","name"+name);

                Mission_Name.setText("Name : "+name);
                Drone_Id.setText("Drone ID : "+drone_id);
                Item_Weight.setText("Weight : "+weight);
                Mission_Type.setText("Mission type : "+type);

            } else {
                Toast.makeText(Execute_Mission.this, "Last Mission On Screen Now", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Call<Execute_Mission_Class> call, Throwable t) {
            Toast.makeText(Execute_Mission.this, "error", Toast.LENGTH_SHORT).show();

        }

    });
}
    public void execute(){

        String mission_status = "Ongoing";
        String drone_status = "Busy";
        Call<Execute_Mission_Class> call = userClient.execute(token,drone_status,id,mission_status);


        call.enqueue(new Callback<Execute_Mission_Class>() {
            @Override
            public void onResponse(Call<Execute_Mission_Class> call, Response<Execute_Mission_Class> response) {


                if (response.isSuccessful()) {
                    Toast.makeText(Execute_Mission.this, "Mission Executed", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(Execute_Mission.this, "cannot execute mission", Toast.LENGTH_SHORT).show();

                }
                Intent intent = new Intent(Execute_Mission.this,Ongoing_Mission.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Execute_Mission_Class> call, Throwable t) {
                Toast.makeText(Execute_Mission.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
