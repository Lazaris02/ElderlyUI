package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PillsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.pills);
        ImageView exitText=findViewById(R.id.exit_text);
        ImageView addPill=findViewById(R.id.add_text);
        ImageView removePill=findViewById(R.id.remove_text);
        ImageView viewCalendar=findViewById(R.id.program_text);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),MainActivity.class);
                startActivity(myIntent);
            }
        });
        addPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent=new Intent(view.getContext(),AddPillsActivity.class);
                startActivity(myIntent);
            }
        });
    }

}
