package com.example.login_retro.Api.model;

import android.app.Person;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login_retro.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.login_retro.Api.model.MainActivity.token;
import static com.example.login_retro.Api.model.MainActivity.user_id;
import static com.example.login_retro.Api.model.Track_Drone.Lat;

//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client

public class Missions extends AppCompatActivity {
    public Missions(){
        this.getName();
    }
    ListView listView;
    ListView listView1;
    ListView listView2;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://teamdronex.com/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
//    ListView listView = findViewById(R.id.Misssion_List);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);
        listView = (ListView)findViewById(R.id.Misssion_Name);
        listView1= (ListView)findViewById(R.id.Mission_status);
        listView2= (ListView)findViewById(R.id.Mission_drone);

    }

    private void getName() {
        Call <List<Mission_Class>> call = userClient.getName(user_id,token);
        Log.e("res","res"+token);
        Log.e("user","user"+user_id);
        call.enqueue(new Callback<List<Mission_Class>>() {
            @Override
            public void onResponse(Call<List<Mission_Class>> call, Response<List<Mission_Class>> response) {
                Log.e("user","user"+user_id);
                List<Mission_Class> names = response.body();
                Log.e("user","user"+user_id);
                for (Mission_Class n: names){
                    Log.d("name",n.getName());
                }


                String[] dronenames = new String[names.size()];
                String[] dronestatus = new String[names.size()];
                String[] droneids = new String[names.size()];



                for (int i=0;i<names.size();i++)
                {
                    dronenames[i]=names.get(i).getName();
                    dronestatus[i]=names.get(i).getStatus();
                    droneids[i] = names.get(i).getDrone_id();
//                    String[] location_name = new String[location.size];
//                    for(int j=0;j<names.size();j++)
//                    {
//                        location_name[j]=location[i].get(j).getName();
                    Log.e("names","user"+dronenames);
//                    }
                }




                listView.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                dronenames

                        )


                );

                listView1.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                dronestatus
                        )

                );
                listView2.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                droneids
                        )

                );
                Log.e("Lat", "AASDASD" + Lat);
            }

            @Override
            public void onFailure(Call<List<Mission_Class>> call, Throwable t) {
                Toast.makeText(Missions.this, "error", Toast.LENGTH_SHORT).show();
            }
//
        });
    }

}

