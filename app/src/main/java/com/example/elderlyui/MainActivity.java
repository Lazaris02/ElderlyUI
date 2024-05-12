package com.example.elderlyui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private TextView batteryView,timeView,widgetTextView;
    private TextView callText,positionText,jokesText,pillsText,listText,dangerText;

    private ImageButton callIcon,positionIcon,jokesIcon,pillsIcon,listIcon,dangerIcon;
    private AppsContainer[] LeftScreen,RightScreen;
    private boolean isLeft = true; //keeps track of which screen we are on

    private ImageView weatherImg;
    private Handler handler;

    private float x_start,x_end;
    protected String temperatureValue; //passed to other activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        //get all activity components and setup application objects
        batteryView = findViewById(R.id.batteryText);
        timeView = findViewById(R.id.timeText);
        widgetTextView = findViewById(R.id.widgetText);
        weatherImg = findViewById(R.id.weatherImage);

        jokesIcon = findViewById(R.id.jokesApp);
        jokesText = findViewById(R.id.textJokes);

        dangerIcon = findViewById(R.id.dangerApp);
        dangerText = findViewById(R.id.textDanger);

        pillsIcon = findViewById(R.id.pillsApp);
        pillsText = findViewById(R.id.textPills);

        listIcon = findViewById(R.id.listApp);
        listText = findViewById(R.id.textList);

        callIcon = findViewById(R.id.callApp);
        callText = findViewById(R.id.textCall);

        positionIcon = findViewById(R.id.positionApp);
        positionText = findViewById(R.id.textPosition);

        setupAppObjects();

        callIcon.setOnClickListener(new View.OnClickListener(){
            //navigates to the Call activity
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(v.getContext(),CallActivity.class);
                startActivity(myIntent);
            }
        });

        positionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),PositionsActivity.class);
                //pack the variables the activity needs
                myIntent.putExtra("temperature",temperatureValue);
                startActivity(myIntent);
            }
        });
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        handler = new Handler();
        getWeather();
        updateTime.run(); //start updating time on a new thread

    }

    private void setupAppObjects(){
        /*Creates all the AppsContainer objects we need
        * also adds them to either the left or the right array screen*/

        AppsContainer callApp = new AppsContainer(callIcon,callText);
        AppsContainer positionApp = new AppsContainer(positionIcon,positionText);

        AppsContainer jokesApp = new AppsContainer(jokesIcon,jokesText);
        AppsContainer dangerApp = new AppsContainer(dangerIcon,dangerText);

        AppsContainer listApp = new AppsContainer(listIcon,listText);
        AppsContainer pillsApp = new AppsContainer(pillsIcon,pillsText);

        LeftScreen = new AppsContainer[]{callApp, positionApp, jokesApp, pillsApp};
        RightScreen = new AppsContainer[]{listApp,dangerApp};


    }


    public boolean onTouchEvent(MotionEvent touchEvent){
        /*if I swipe left while I am in the 1st page I have to go to the second
        * and if I swipe right while I am in the 2nd page I have to go to the first*/
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x_start = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                x_end = touchEvent.getX();
                if((x_start < x_end && !isLeft) || (x_start > x_end && isLeft)){
                    swapScreen();
                }
                break;
        }
        return false;
    }
    private void swapScreen(){
        /*see which screen we are on from the isLeft variable
        * proceeds to swap accordingly and change the variable afterwards.*/
        if(isLeft){
            isLeft = false;
            /*hide all the left screen apps and make the right apps appear*/
            for (AppsContainer app : LeftScreen){
                app.getAppImg().setVisibility(View.GONE);
                app.getAppText().setVisibility(View.GONE);
            }
            for (AppsContainer app : RightScreen){
                app.getAppImg().setVisibility(View.VISIBLE);
                app.getAppText().setVisibility(View.VISIBLE);
            }
        }else{
            isLeft = true;
            /*else do the reverse*/
            for (AppsContainer app : RightScreen){
                app.getAppImg().setVisibility(View.GONE);
                app.getAppText().setVisibility(View.GONE);
            }
            for (AppsContainer app : LeftScreen){
                app.getAppImg().setVisibility(View.VISIBLE);
                app.getAppText().setVisibility(View.VISIBLE);
            }

        }
    }

    private final Runnable updateTime = new Runnable() {
        //runnable for periodic time updates
        //updates time on a separate thread
        @Override
        public void run() {
            //get current time
            TimeZone timeZone = TimeZone.getTimeZone("Europe/Athens");
            Calendar calendar = Calendar.getInstance(timeZone);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            //format time and update text view
            String time = String.format("%02d:%02d", hour, minute);
            timeView.setText(time);

            // Schedule next update after 1 second - for accuracy
            handler.postDelayed(this, 1000);
        }
    };


    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the current battery level
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float battery = (level / (float) scale) * 100; //percentage
            String batteryStr ="Μπαταρία:"+(int) battery +"%";
            batteryView.setText(batteryStr); //sets text
            if(battery < 20.0){batteryView.setTextColor(Color.RED);}
        }
    };

    private void getWeather(){
        /*makes an API CALL to open-meteo.com and gets the current weather in Athens*/
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=37.9838&longitude=23.7278&current=temperature_2m,is_day,weather_code&timezone=auto&forecast_days=1";
        new WeatherAPICall().execute(apiUrl); // makes the api call and calculates


    }
    private class WeatherAPICall extends AsyncTask<String, Void, JSONObject> {

        private static final String TAG = "WeatherCall";
        private Map<String,String> wmo_codes;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                //create URL object from the first param (string url)
                URL url = new URL(params[0]);

                // create and open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                //build the String from the response
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                inputStream.close();
                urlConnection.disconnect();

                // convert the response to JSON object
                return new JSONObject(sb.toString());
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error making API call", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            // Handle the JSON response here
            if (jsonObject != null) {
                //parse the JSON object and get weather data
                createWmoMap();
                modifyWidget(jsonObject);
            } else {
                Log.e(TAG, "API call failed");
                //handle error
            }
        }

        private void modifyWidget(JSONObject jsonObject){
            /*extracts and changes the main activity text views*/

            //first get the temperature and the temperature measure
            try {
                //get the two sub-json objects
                JSONObject currentInfo = (JSONObject) jsonObject.get("current");
                JSONObject currentUnits = (JSONObject) jsonObject.get("current_units");

                //now I should have both the objects I need to extract from
                String temperature_value = currentInfo.getString("temperature_2m");
                String temperature_metric =currentUnits.getString("temperature_2m");

                //make it a concatinated string
                String temperature = temperature_value + temperature_metric;
                temperatureValue = temperature;
                //extract the other info I need (if it is day or not, the description of the weather)
                Integer isDay = (Integer) currentInfo.get("is_day"); //weather it is morning or not
                String weatherCondition = wmo_codes.get(currentInfo.getString("weather_code"));

                //we dynamically decide on the image we use for the widget
                String imageName = WeatherInfo.decideWeatherIcon(weatherCondition,isDay);

                //convert the name to the actual resource in the drawables folder.
                int resourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (resourceId != 0) {
                    // If the resource exists, set it to the ImageView
                    weatherImg.setImageResource(resourceId);
                } else {
                    // If the resource does not exist, handle the error
                    Log.e("ImageError", "Image with name " + imageName + " not found");
                }

                //modify the text and icon of the widget

                widgetTextView.setText("Αθήνα\nΘερμοκρασία:"+temperature+"\n"+weatherCondition+"!");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        private void createWmoMap(){
            String[] codes = WeatherInfo.getCodes();
            String[] names = WeatherInfo.getNames();

            /*creates a HashMap with key:weather code value:name of weather*/

            wmo_codes = new HashMap<>();
            for(int i=0; i < names.length; i++){
                wmo_codes.put(codes[i],names[i]);
            }
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(updateTime);
    }
}