package com.example.elderlyui;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;


public class CallActivity extends AppCompatActivity {

    private String phoneNumber;
    private TextView phoneText;
    private TextToSpeech tts;

    private HashMap<String,String> favouriteCallers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_call);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initializeFavouriteCallers(); //initializes the favourites list

        phoneText = findViewById(R.id.phoneNumberText);
        /*a favourite caller might be passed*/
        Intent intent = getIntent();
        phoneNumber = findFavourite(intent.getStringExtra("callerId"));
        phoneNumber = (phoneNumber == null) ? "" : phoneNumber;
        phoneText.setText(phoneNumber);

        //get all the views
        setupOnClickListeners();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //set the language to Greek
                    int result = tts.setLanguage(new Locale("el")); //set language greek
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(CallActivity.this,"Text to speech not Supported",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void initializeFavouriteCallers() {
        /*initializes the favourites list from a .txt file*/
        AssetManager assetManager = getAssets();
        favouriteCallers = new HashMap<>();

        try (InputStream inputStream = assetManager.open("favouritePhones.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String name,phone,line;
            while ((line = reader.readLine()) != null) {
                name = line; /// name
                phone = reader.readLine(); // number
                reader.readLine(); // the ///
                favouriteCallers.put(name,phone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String findFavourite(String callerId) {
        return favouriteCallers.get(callerId);
    }

    private void clearDigits(){
        phoneNumber = "";
        phoneText.setText(phoneNumber);
    }

    private void addDigit(String digit){
        //also speaks the digit
        phoneNumber = phoneNumber.concat(digit);
        speak(digit);
        phoneText.setText(phoneNumber);
    }

    private void removeDigit(){
        phoneNumber = (phoneNumber == null || phoneNumber.isEmpty())
                ? null : phoneNumber.substring(0,phoneNumber.length()-1);
        phoneText.setText(phoneNumber);
    }

    private void speak(String text) {
        if (tts != null) {
            //reads the text in greek
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }


    private void setupOnClickListeners(){
        findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("0");
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("1");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("2");
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("3");
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("4");
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("5");
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("6");
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("7");
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("8");
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("9");
            }
        });
        findViewById(R.id.buttonStar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("*");
            }
        });
        findViewById(R.id.buttonHash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDigit("#");
            }
        });
        findViewById(R.id.clearDigit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDigits();
            }
        });
        findViewById(R.id.deleteDigit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDigit();
            }
        });
        findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),CallNumberActivity.class);
                myIntent.putExtra("phone",phoneNumber);
                startActivity(myIntent);
            }
        });

        findViewById(R.id.exit_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}