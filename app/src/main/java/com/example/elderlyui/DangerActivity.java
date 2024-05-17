package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class DangerActivity extends AppCompatActivity {

    private ImageButton dangerButton;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_danger);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView exitButton = findViewById(R.id.exit_text);
        dangerButton = findViewById(R.id.dangerButton);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //set the language to Greek
                    int result = tts.setLanguage(new Locale("el")); //set language greek
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(DangerActivity.this,"Text to speech not Supported",Toast.LENGTH_SHORT).show();
                    }
                    speak("Αν βρίσκεστε σε κίνδυνο πατήστε το κόκκινο κουμπί");
                }
            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),MainActivity.class);
                startActivity(myIntent);
            }
        });

        dangerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });

    }

    private void showAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.danger_alert, null);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);

        AlertDialog dialog = alert.create();

        // Get references to the TextViews and Buttons in the custom layout
        TextView alertTitle = alertLayout.findViewById(R.id.alertTitle);
        TextView alertMessage = alertLayout.findViewById(R.id.alertMessage);
        Button positiveButton = alertLayout.findViewById(R.id.positiveButton);
        Button negativeButton = alertLayout.findViewById(R.id.negativeButton);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangerButton.setOnClickListener(null); //disable click
                showDoneMessage();
                dialog.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void showDoneMessage(){
        String message = "Βοήθεια βρίσκεται καθ΄οδόν. Διατηρήστε τη ψυχραιμία σας!";
        TextView doneMessage = findViewById(R.id.warning_text);
        doneMessage.setText(message);
        doneMessage.setVisibility(View.VISIBLE);
        speak(message);
    }

    private void speak(String message){
        if (tts != null) {
            //reads the text in greek
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
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