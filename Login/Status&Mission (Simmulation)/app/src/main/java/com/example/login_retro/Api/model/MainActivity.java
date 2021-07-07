package com.example.login_retro.Api.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_retro.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Defining the Retrofit using Builder
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://teamdronex.com/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);



    private EditText editTextEmail;
    private EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_login).setOnClickListener(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
//            case R.id.btn_secret:
//                getSecret();
//                break;

        }

    }
//    public String token;
//    private static String token;

    public static String token;
    public static int user_id;
//    {
//        get { return token; }
//        set { name = value; }
//
//    }

//    public String getToken(){
//        return this.token;
//    }

    public void login() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

//        Login login = new Login("johndoe@example.com", "aaaaaa");
//        Call<User> call = userClient.login(login);
        Call<User> call = userClient.login(email,password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                if (response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "login Successful", Toast.LENGTH_SHORT).show();

                    token = response.body().getToken();
                    user_id = response.body().getId();
                    Log.e("token","token"+token);
                    Log.e("token","token"+user_id);
                    Intent intent = new Intent(MainActivity.this,Drones.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "login not correct", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
//        Intent intent = new Intent(this,Drones.class);
//        startActivity(intent);


    }

//    private void getSecret() {
//        Call<ResponseBody> call = userClient.getSecret(token);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "token is not correct", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }


}




