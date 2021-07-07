package com.example.login_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = (TextView) findViewById(R.id.txtUsername);

        Bundle extras = getIntent().getExtras();
        String email;

        if(extras != null){
            email = extras.getString("username");
            txtUsername.setText("Welcome " + email);
        }

    }
}