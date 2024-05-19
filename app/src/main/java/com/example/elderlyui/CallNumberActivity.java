package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class CallNumberActivity extends AppCompatActivity {
    TextToSpeech tts;
    String callerId;

    boolean isMuted = false;
    boolean isSpeaker = false;

    TextView microphoneText,speakerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_call_number);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        microphoneText = findViewById(R.id.mic_text);
        speakerText = findViewById(R.id.speaker_text);

        Intent intent = getIntent();
        String phoneNum = intent.getStringExtra("phone");
        String fav_id = intent.getStringExtra("favouriteName");

        //set the caller id -- could also do images etc.

        callerId = (fav_id == null) ? phoneNum : fav_id;
        TextView caller = findViewById(R.id.callerId);
        caller.setText(callerId);

        //init the text to speech

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //set the language to Greek
                    int result = tts.setLanguage(new Locale("el")); //set language greek
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(CallNumberActivity.this,"Text to speech not Supported",Toast.LENGTH_SHORT).show();
                    }
                    String message = "Καλείτε το τηλέφωνο: " + callerId;
                    speak(message);
                }
            }
        });

        findViewById(R.id.close_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("callerNumber",phoneNum);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });

        findViewById(R.id.mic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMuted = !isMuted;
                String mic_text,mic_voice;
                if(isMuted){
                    mic_text = "Βγάλτε τη\n Σίγαση";
                    mic_voice = "Είστε σε Σίγαση!";
                }else{
                    mic_text = "Βάλτε Σίγαση";
                    mic_voice = "Αφαιρέσατε τη Σίγαση";
                }
                microphoneText.setText(mic_text);
                speak(mic_voice);
            }
        });

        findViewById(R.id.speaker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpeaker = !isSpeaker;
                String speaker_text,speaker_voice;
                if(isSpeaker){
                    speaker_text = "Βγάλτε \nΑνοιχτή\n Ακρόαση";
                    speaker_voice = "Είστε σε ανοιχτή ακρόαση!";
                }else{
                    speaker_text = "Βάλτε \nΑνοιχτή\n Ακρόαση";
                    speaker_voice = "Αφαιρέσατε την ανοιχτή ακρόαση";
                }
                speakerText.setText(speaker_text);
                speak(speaker_voice);
            }
        });
    }

    private void speak(String text) {
        if (tts != null) {
            //reads the text in greek
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
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