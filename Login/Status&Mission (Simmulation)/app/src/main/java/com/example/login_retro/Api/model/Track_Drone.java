package com.example.login_retro.Api.model;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.login_retro.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.login_retro.Api.model.Delivery_Drone.name;

public class Track_Drone extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String lat;
    private String lng;
    public static Double Lat;
    private Double Lng;

    private Marker marker;

    //
    public Track_Drone() {
        this.getLocation();
//        this.onMapReady(GoogleMap googleMap);
    }


    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getLocation();

            handler.postDelayed(this, 3000);
        }
    };

//Start


    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://teamdronex.com/").addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track__drone);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        handler.postDelayed(runnable, 3000);
//        this.getLocation();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void getLocation() {
        Call<Location> call = userClient.getLocation();

        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {

                if (response.isSuccessful()) {



                    try {
                        mMap.clear();

                        lat = response.body().getGps_latitude();
                        lng = response.body().getGps_longitude();
                        Log.e("Lat", "INSIDE getLocation");
                        Log.e("Lat", "lat" + lat);
                        Lat = Double.parseDouble(lat);
                        Log.e("Lat", "Lat_D" + lat);
                        Lng = Double.parseDouble(lng);

                        LatLng sydney = new LatLng(Lat, Lng);
                        MarkerOptions a = new MarkerOptions().position(sydney).title(name);
                        marker = mMap.addMarker(a);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
                        mMap.animateCamera(zoom);
                    }
                    catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(Track_Drone.this, "login not correct", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(Track_Drone.this, "error", Toast.LENGTH_SHORT).show();

            }

        });

    }

    //logger.error("BOOM!", e);
//    LOGGER.info("Got an exception. " + e.getMessage());
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("result", "smt " + Lat);
//        getLocation();
//        Call<Location> call = userClient.getLocation();
//
//        call.enqueue(new Callback<Location>() {
//            @Override
//            public void onResponse(Call<Location> call, Response<Location> response) {
//
//                if (response.isSuccessful()) {
//
//
//                    lat = response.body().getGps_latitude();
//                    lng = response.body().getGps_longitude();
//                    Log.e("Lat", "INSIDE getLocation");
//                    Log.e("Lat", "lat" + lat);
//                    Lat =Double.parseDouble(lat);
//                    Log.e("Lat", "Lat_D" + lat);
//                    Lng =Double.parseDouble(lng);
//
//
//
//
//                } else {
//                    Toast.makeText(Track_Drone.this, "login not correct", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Location> call, Throwable t) {
//                Toast.makeText(Track_Drone.this, "error", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
        Log.e("result", "smt " + Lat);
//        double Lat = Double.parseDouble(lat);
//        double Lng = Double.parseDouble(lng);


        mMap = googleMap;

//        if(marker!=null){
//            marker.remove();
//        }
//        LatLng sydney = new LatLng(Lat, Lng);
//
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Home"));
//        MarkerOptions a = new MarkerOptions().position(sydney).title("Home");
//        marker = mMap.addMarker(a);
////            void updateCabLocation (final LatLng sydney) {
////
//////            Marker myMarker;
////            if(marker == null) {
////                marker = mMap.addMarker(new MarkerOptions().position(sydney));
////            }else {
////                marker.setPosition(sydney);
////            }
////            }
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
//        mMap.animateCamera(zoom);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//       super.onDestroy();
//
//    }
}
