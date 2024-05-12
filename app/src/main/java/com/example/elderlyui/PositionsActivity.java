package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PositionsActivity extends AppCompatActivity {

    private TextView city_text;
    private TextView street_text;
    private TextView help_button;
    private TextView call_taxi_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_positions);

        // Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //get all the views I need
        TextView city_text = findViewById(R.id.city_text);
        TextView date_text = findViewById(R.id.textDate);
        TextView street_text = findViewById(R.id.street_text);
        TextView help_button = findViewById(R.id.help_button); //redirects to the dangerApp
        TextView call_taxi_button = findViewById(R.id.taxiButton); //redirects to callApp with taxiNum


        //modify Date
        String cityTimeZone = "Europe/Athens"; //passed from previous activity
        date_text.setText(getDate(cityTimeZone));

        //modify temperature
        modifyTemperature();

        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*redirect to the dangerApp!*/
            }
        });

        call_taxi_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*redirects to the call app with a taxi number
                * as parameter*/
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
}