package com.example.spinner_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private Spinner spinner;
//    public class Operator {
//        String name;
//        String code;
//
//
//
//        @Override
//        public String toString() {
//            return name;
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
//        final List<Operator> operators = new ArrayList(3);
//        operators.add(new Operator("Select operator", null));
//        operators.add(new Operator("Aircel", "AC"));
//        operators.add(new Operator("Airtel", "AC"));
//
//        final ArrayAdapter<String> operatorsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, operators);
//        spinner.setAdapter(operatorsAdapter);
        final List<String> ids = new ArrayList<String>();
        ids.add(new String ("1"));
        List<String> name = new ArrayList<String>();
        name.add(new String("Zin"));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String sid =ids.get(position);//This will be the student id.
                Log.e("name","name"+sid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}
