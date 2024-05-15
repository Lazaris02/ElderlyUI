package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


public class JokesActivity extends AppCompatActivity {
    /*0 - classic jokes
    * 1 - animal jokes
    * 2 - family jokes
    * 3 - smart jokes*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        ImageButton classicJokes = findViewById(R.id.classicJokes);
        ImageButton animalJokes = findViewById(R.id.animalJokes);
        ImageButton familyJokes = findViewById(R.id.familyJokes);
        ImageButton smartJokes = findViewById(R.id.smartJokes);

        classicJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), JokesListActivity.class);
                myIntent.putExtra("jokesCategory",0);
                startActivity(myIntent);
            }
        });

        animalJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), JokesListActivity.class);
                myIntent.putExtra("jokesCategory",1);
                startActivity(myIntent);
            }
        });

        familyJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), JokesListActivity.class);
                myIntent.putExtra("jokesCategory",2);
                startActivity(myIntent);
            }
        });

        smartJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), JokesActivity.class);
                myIntent.putExtra("jokesCategory",3);
                startActivity(myIntent);
            }
        });
    }


}