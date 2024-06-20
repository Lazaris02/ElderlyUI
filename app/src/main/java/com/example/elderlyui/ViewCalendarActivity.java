package com.example.elderlyui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elderlyui.adapters.PillAdapter;
import com.example.elderlyui.models.Pill;
import com.example.elderlyui.persistence.MyApp;

import java.util.List;

public class ViewCalendarActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PillAdapter pillAdapter;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);



        // Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.view_calendar);

        ImageView exitText = findViewById(R.id.exit_button);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyApp myApp = (MyApp) getApplicationContext();
        List<Pill> pillList = myApp.getPills();

        pillAdapter = new PillAdapter(pillList);
        recyclerView.setAdapter(pillAdapter);

        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
