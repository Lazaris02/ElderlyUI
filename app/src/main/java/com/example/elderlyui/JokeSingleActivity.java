package com.example.elderlyui;



import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import java.util.Locale;

public class JokeSingleActivity extends AppCompatActivity {
    TextToSpeech tts;
    TextView titleText , jokeContent;
    String title,joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_joke_single);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //get the joke and the title
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        joke = intent.getStringExtra("joke");

        titleText = findViewById(R.id.jokeTitle);
        jokeContent = findViewById(R.id.theJoke);

        titleText.setText(title);
        jokeContent.setText(joke);

        TextView exitButton = findViewById(R.id.exit_text);
        ImageButton backArrow = findViewById(R.id.backArrow);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //set the language to Greek
                    int result = tts.setLanguage(new Locale("el")); //set language greek
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(JokeSingleActivity.this,"Text to speech not Supported",Toast.LENGTH_SHORT).show();
                    }
                    String message = title+"."+joke;
                    speak(message);
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), JokesActivity.class);
                startActivity(myIntent);
                finish();
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