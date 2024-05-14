package com.example.elderlyui;




import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class PositionsActivity extends AppCompatActivity {

    private TextView city_text;
    private TextView street_text;


    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE=100; //for location


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_positions);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*manual location request */
        LocationRequest locationRequest = new  LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(100)
                .build();


        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null) {
                    return;
                }
            }
        };

        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                .requestLocationUpdates(locationRequest,locationCallback,null);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        checkForLocationPermission();

        //get all the views I need
        city_text = findViewById(R.id.city_text);
        TextView date_text = findViewById(R.id.textDate);
        street_text = findViewById(R.id.street_text);
        TextView help_button = findViewById(R.id.help_button); //redirects to the dangerApp
        TextView call_taxi_button = findViewById(R.id.taxiButton); //redirects to callApp with taxiNum
        TextView exit_app =  findViewById(R.id.exit_text);

        //modify Date
        String cityTimeZone = "Europe/Athens";
        date_text.setText(getDate(cityTimeZone));


        //modify temperature
        modifyTemperature();
        getLocation();
        //read the screen?

        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*redirect to the dangerApp!*/
                Intent myIntent = new Intent(v.getContext(),DangerActivity.class);
                startActivity(myIntent);
            }
        });

        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*returns to the main screen*/
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                //pack the variables the activity needs
                startActivity(myIntent);
            }
        });

        call_taxi_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*redirects to the call app with a taxi number
                * as parameter*/
                Intent myIntent = new Intent(v.getContext(),CallActivity.class);
                myIntent.putExtra("taxiNumber","8888888");
                startActivity(myIntent);
            }
        });

    }
    private String getDate(String cityTimeZone){
        /*gets the current date in greek and returns it as a String*/
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE \n dd-MM-yy",new Locale("el", "GR"));
        //timezone for the specific place
        sdf.setTimeZone(TimeZone.getTimeZone(cityTimeZone));
        Log.e("THE DATE",sdf.format(currentDate));
        //return the formatted date in greek
        return sdf.format(currentDate);

    }

    private void modifyTemperature(){
        //modify temperature
        TextView temperature_text = findViewById(R.id.temperatureText);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String temperature = null;
        if(b!=null){
            temperature = b.getString("temperature");
        }
        temperature_text.setText(temperature);
    }

    private void checkForLocationPermission(){
        //checks if location permission was given
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //if permission is not granted , ask for it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE); //the request location permission code
        } else {
            Toast.makeText(PositionsActivity.this,"Location Permission granted",Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocation(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //if the permission for location has been granted

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            /*get last location takes the last cached location
                            * that means that the device needs to have one cached*/
                            if(location != null){
                                Locale greek = new Locale("el", "GR");
                                Geocoder geocoder = new Geocoder(PositionsActivity.this,greek);
                                try {
                                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    Address address = addressList.get(0); //extract the info we need
                                    String city = address.getLocality()+" "+address.getCountryName();
                                    city_text.setText(city);
                                    street_text.setText(address.getAddressLine(0));


                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }else{

                            }
                        }
                    });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}