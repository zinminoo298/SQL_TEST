package com.example.login_retro.Api.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_retro.R;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.login_retro.Api.model.MainActivity.token;
import static com.example.login_retro.Api.model.Execute_Mission.id;

public class Ongoing_Mission extends AppCompatActivity implements View.OnClickListener {
    public Ongoing_Mission() {
        this.Ongoing();
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
        findViewById(R.id.btn_drone).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_terminate:
                terminate();
                break;

            case R.id.btn_drone:
                track();
                break;
        }
    }

    public void Ongoing() {
        Call<List<Ongoing_Mission_Class>> call = userClient.getOngoing(1, token);
        call.enqueue(new Callback<List<Ongoing_Mission_Class>>() {
            @Override
            public void onResponse(Call<List<Ongoing_Mission_Class>> call, Response<List<Ongoing_Mission_Class>> response) {
                List<Ongoing_Mission_Class> names = response.body();
//                if (response.isSuccessful()) {

                    String[] mission_name = new String[names.size()];
                    String[] id = new String[names.size()];
                    String[] weig = new String[names.size()];
                    String[] ty = new String[names.size()];



                    for (int i=0;i<names.size();i++)
                    {
                        mission_name[i]=names.get(i).getName();
                        id[i]=names.get(i).getDrone_id();
                        weig[i] = names.get(i).getWeight();
                        ty[i] = names.get(i).getType();
                    }
//                    Toast.makeText(Ongoing_Mission.this, response.body().getName(), Toast.LENGTH_SHORT).show();
//                    name = response.body().getName();
//                    drone_id = response.body().getDrone_id();
//                    weight = response.body().getWeight();
//                    type = response.body().getType();
                    Log.e("Lat", "INSIDE getLocation");
                    Log.e("name", "name" +  Arrays.asList(mission_name).toString());



                    if(Arrays.toString(mission_name).isEmpty()){
                        Toast.makeText(Ongoing_Mission.this, "There is no ongoing mission", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Ongoing_Mission.this,Delivery_Drone.class);
                        startActivity(intent);

                    }

               else
                {
                    Mission_Name.setText("Name : " + Arrays.toString(mission_name).replace("[","")
                            .replace("]",""));
                    Drone_Id.setText("Drone ID : " + Arrays.toString(id).replace("[","")
                            .replace("]",""));
                    Item_Weight.setText("Weight : " + Arrays.toString(weig).replace("[","")
                            .replace("]",""));
                    Mission_Type.setText("Mission type : " + Arrays.toString(ty).replace("[","")
                            .replace("]",""));
                }
//                } else {
//                    Toast.makeText(Ongoing_Mission.this, "Last Mission On Screen Now", Toast.LENGTH_SHORT).show();
//
//                }
            }

            @Override
            public void onFailure(Call<List<Ongoing_Mission_Class>> call, Throwable t) {
                Toast.makeText(Ongoing_Mission.this, "error", Toast.LENGTH_SHORT).show();

            }

        });
    }
    public void terminate(){

        String mission_status = "Done";
        String drone_status = "Available";
        Call<Execute_Mission_Class> call = userClient.terminate(token,drone_status,id,mission_status,drone_id);


        call.enqueue(new Callback<Execute_Mission_Class>() {
            @Override
            public void onResponse(Call<Execute_Mission_Class> call, Response<Execute_Mission_Class> response) {


                if (response.isSuccessful()) {
                    Toast.makeText(Ongoing_Mission.this, "Mission Terminated", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(Ongoing_Mission.this, "cannot terminate mission", Toast.LENGTH_SHORT).show();

                }
                Intent intent = new Intent(Ongoing_Mission.this,Delivery_Drone.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Execute_Mission_Class> call, Throwable t) {
                Toast.makeText(Ongoing_Mission.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void track(){
        Intent intent = new Intent(Ongoing_Mission.this,Track_Drone.class);
        startActivity(intent);
    }
}
