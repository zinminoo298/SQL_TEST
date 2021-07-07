package com.example.spinner;

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



public Object ids;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner1);
        List<StringWithTag> list = new ArrayList<StringWithTag>();
        list.add(new StringWithTag("Oldman", null));
        list.add(new StringWithTag("Umpire", "987654"));
        list.add(new StringWithTag("Squad", "ABCDEE"));
        ArrayAdapter<StringWithTag> adap = new ArrayAdapter<StringWithTag>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adap);
        Log.e("Lat", "testing");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag s = (StringWithTag) parent.getItemAtPosition(position);
               ids= s.tag;
                Log.e("Lat","Id"+ids);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



//    public void onItemSelected(AdapterView<?> parant, View v, int pos, long id) {
//        StringWithTag s = (StringWithTag) parant.getItemAtPosition(pos);
//        Object tag = s.tag;
//        Log.e("Lat","INSIDE getLocation"+tag);
//
//    }
}

