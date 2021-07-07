package com.example.login_retro.Api.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.login_retro.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.login_retro.Api.model.MainActivity.token;
import static com.example.login_retro.Api.model.MainActivity.user_id;

public class Create_Mission extends AppCompatActivity implements View.OnClickListener {
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://teamdronex.com/")
            .addConverterFactory(GsonConverterFactory.create());


    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    public Object location_id;
    public class StringWithTag {
        public String string;
        public Object tag;

        public StringWithTag(String stringPart, Object tagPart) {
            string = stringPart;
            tag = tagPart;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private Spinner spinner;
    private EditText editTextName;
    private EditText editTextWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__mission);

        findViewById(R.id.btn_mission).setOnClickListener(this);
        editTextName = findViewById(R.id.editTextName);
        editTextWeight = findViewById(R.id.editTextWeight);

        spinner = findViewById(R.id.spinner1);
//        List<String> locations = new ArrayList<>();
//        locations.add("2");
//
//        ArrayAdapter<String> dataAdapter;
//        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,locations);
//
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
        List<StringWithTag> list = new ArrayList<StringWithTag>();
        list.add(new StringWithTag("NW Main Field Edge", "2"));
        list.add(new StringWithTag("AIT", "3"));
        list.add(new StringWithTag("CSIM Back", "2852"));
        ArrayAdapter<StringWithTag> adap = new ArrayAdapter<StringWithTag>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adap);
//        Log.e("Lat", "testing");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag s = (StringWithTag) parent.getItemAtPosition(position);
                location_id= s.tag;
                Log.e("Lat","Id"+location_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mission:
                mission();
                break;
    }
}

    private void mission() {


            String name = editTextName.getText().toString();
            String weight = editTextWeight.getText().toString();
//            String location_id = spinner.getSelectedItem().toString();
            String status = "Saved";
            int drone_id = 2;
//            int location_id = 2;


            if (name.isEmpty()) {
                editTextName.setError("Item Name is required");
                editTextName.requestFocus();
                return;
            }

            if (weight.isEmpty()) {
                editTextWeight.setError("Weight is required");
                editTextWeight.requestFocus();
                return;
            }
//        Login login = new Login("johndoe@example.com", "aaaaaa");
//        Call<User> call = userClient.login(login);
            Call<Create_Mission_Client> call = userClient.mission(token,name,weight,drone_id,location_id,status,user_id,"Delivery");


            call.enqueue(new Callback<Create_Mission_Client>() {
                @Override
                public void onResponse(Call<Create_Mission_Client> call, Response<Create_Mission_Client> response) {


                    if (response.isSuccessful()) {
//                    User loginResponse = response.body();
//                    Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Create_Mission.this, "login Successful", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this,Drones.class);
//                        startActivity(intent);
                        Toast.makeText(Create_Mission.this, "Mission Created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Create_Mission.this,Execute_Mission.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Create_Mission.this, "cannot create mission", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Create_Mission_Client> call, Throwable t) {
                    Toast.makeText(Create_Mission.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
//        Intent intent = new Intent(this,Drones.class);
//        startActivity(intent);


        }
    }

