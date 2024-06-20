package com.example.elderlyui.persistence;

import android.app.Application;

import com.example.elderlyui.models.Pill;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private List<Pill> pills;

    @Override
    public void onCreate() {
        super.onCreate();
        pills = new ArrayList<>();
    }
    public void addPill(Pill pill){
        pills.add(pill);
    }

    public List<Pill> getPills() {
        return pills;
    }
}
