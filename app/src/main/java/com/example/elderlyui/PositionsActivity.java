package com.example.elderlyui;




import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

    private TextView street_text;
    private TextToSpeech tts;
    private String currDate,street_loc,temperature;
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE=100; //for location

    private String message;

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
        TextView date_text = findViewById(R.id.textDate);
        street_text = findViewById(R.id.street_text);
        TextView help_button = findViewById(R.id.help_button); //redirects to the dangerApp
        TextView call_taxi_button = findViewById(R.id.taxiButton); //redirects to callApp with taxiNum
        TextView exit_app =  findViewById(R.id.exit_text);

        //modify date
        String cityTimeZone = "Europe/Athens";
        currDate = getDate(cityTimeZone);
        date_text.setText(currDate);


        //modify temperature
        modifyTemperature();
        getLocation();




        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*redirect to the dangerApp!*/
                shutDownTts();
                Intent myIntent = new Intent(v.getContext(),DangerActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*returns to the main screen*/
                shutDownTts();
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                //pack the variables the activity needs
                startActivity(myIntent);
                finish();
            }
        });

        call_taxi_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*redirects to the call app with a taxi number
                * as parameter*/
                shutDownTts();
                Intent myIntent = new Intent(v.getContext(),CallActivity.class);
                myIntent.putExtra("callerId","Taxi");
                startActivity(myIntent);
                finish();
            }
        });

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //set the language to Greek
                    int result = tts.setLanguage(new Locale("el")); //set language greek
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(PositionsActivity.this,"Text to speech not Supported",Toast.LENGTH_SHORT).show();
                    }else{
                        if(message!=null){
                            speak(message);
                        }
                    }
                }
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

    private void speak(String text) {
        if (tts != null) {
            //reads the text in greek
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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
                                    street_loc = address.getAddressLine(0);
                                    street_text.setText(street_loc);
                                    Log.e("HIIIIIIIIIIIIIIIIIIIIIIIIIIII",location.toString());
                                     message = "Σήμερα είναι " + currDate + " και βρίσκεστε στη τοποθεσία "+street_loc
                                            +". Η θερμοκρασία είναι "+temperature+". Αν βρίσκεστε σε κίνδυνο πατήστε το" +
                                            " κόκκινο κουμπί. Αν χρειάζεστε μεταφορικό μέσο πατήστε το κίτρινο κουμπί!";
                                    speak(message);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }else{
                                Log.e("Error Location","Location is null");
                            }
                        }
                    });
        }
    }

    private void shutDownTts(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
    @Override
    protected void onDestroy() {
        shutDownTts();
        super.onDestroy();
    }
}