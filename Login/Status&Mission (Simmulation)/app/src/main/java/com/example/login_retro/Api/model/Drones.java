package com.example.login_retro.Api.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.login_retro.R;

import java.util.ArrayList;

public class Drones extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drones);

        listView = (ListView)findViewById(R.id.drone_list);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Drone_1");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

//        String[] values = new String[]{"Delivery Drone"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.activity_list_item,android.R.id.text1,values);


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(),Delivery_Drone.class);
                startActivity(intent);
            }
        });






    }

}
