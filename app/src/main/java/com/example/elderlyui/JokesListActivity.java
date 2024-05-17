package com.example.elderlyui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class JokesListActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private final String[] jokeCategories = {"classicJokes.txt","animalJokes.txt",
            "familyJokes.txt","smartJokes.txt"};
    private Joke[] jokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jokes_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //set the language to Greek
                    int result = tts.setLanguage(new Locale("el")); //set language greek
                    if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(JokesListActivity.this,"Text to speech not Supported",Toast.LENGTH_SHORT).show();
                    }
                    String message = "Πατήστε πάνω σε έναν από τους τίτλους για να " +
                            "διαβάσετε το ανέκδοτο!";
                    speak(message);
                }
            }
        });

        //we receive input of which jokes we need
        int jokeType = getIntent().getIntExtra("jokesCategory",0);
        setupJokes(jokeCategories[jokeType]);
        ListView jokesList = (ListView) findViewById(R.id.jokesList);
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),jokes);
        jokesList.setAdapter(adapter);
    }
    private void setupJokes(String jokeFile){
        //read from file and create all the Joke objects
        //every joke is split with /// and first line == title
        List<Joke> listJokes = new ArrayList<Joke>();
        AssetManager assetManager = getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = assetManager.open(jokeFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            boolean isTitle = true;
            String currTitle = "";
            String line;
            while ((line = reader.readLine()) != null) {

                if(isTitle){
                    currTitle = line;
                    isTitle = false;
                    continue;
                }
                if(line.trim().equals("///")){
                    listJokes.add(new Joke(currTitle,stringBuilder.toString()));
                    isTitle = true;
                    stringBuilder.setLength(0); // clear string builder
                    continue;
                }
                stringBuilder.append(line).append("\n");
            }
            jokes = new Joke[listJokes.size()];
            listJokes.toArray(jokes);
            //we need array for the customAdapter
        } catch (IOException e) {
            e.printStackTrace();
        }
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