package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class JokesActivity extends AppCompatActivity {
    /*0 - classic jokes
    * 1 - animal jokes
    * 2 - family jokes
    * 3 - smart jokes*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jokes);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton classicJokes = findViewById(R.id.classicJokes);
        ImageButton animalJokes = findViewById(R.id.animalJokes);
        ImageButton familyJokes = findViewById(R.id.familyJokes);
        ImageButton smartJokes = findViewById(R.id.smartJokesButton);
        TextView exitText = findViewById(R.id.exit_text);

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
                Intent myIntent = new Intent(v.getContext(), JokesListActivity.class);
                myIntent.putExtra("jokesCategory",3);
                startActivity(myIntent);
            }
        });

        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                startActivity(myIntent);
            }
        });

    }


}