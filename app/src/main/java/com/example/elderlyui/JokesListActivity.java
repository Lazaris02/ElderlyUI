package com.example.elderlyui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JokesListActivity extends AppCompatActivity {
    private final String[] jokeCategories = {"classicJokes.txt","animalJokes.txt",
            "familyJokes.txt","smartJokes.txt"};
    List<Joke> jokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes_list);

        //we receive input of which jokes we need
        int jokeType = getIntent().getIntExtra("jokesCategory",0);
        setupJokes(jokeCategories[jokeType]);
    }
    private void setupJokes(String jokeFile){
        //read from file and create all the Joke objects
        //every joke is split with /// and first line == title
        jokes = new ArrayList<Joke>();
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
                    jokes.add(new Joke(currTitle,stringBuilder.toString()));
                    isTitle = true;
                    stringBuilder.setLength(0); // clear string builder
                    continue;
                }
                stringBuilder.append(line).append("\n");
            }
            Log.e("EVERYTHING GOOD", jokes.get(0).getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}