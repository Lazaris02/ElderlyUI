package com.example.elderlyui.persistence;

import android.app.Application;

import com.example.elderlyui.models.Pill;
import com.example.elderlyui.models.TefteriItem;

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

    public void removePill(Pill pill) {
        pills.remove(pill);
    }

    private List<TefteriItem> supermarketItemList = new ArrayList<>();

    public List<TefteriItem> getSupermarketItems() {
        return supermarketItemList;
    }

    public void addSupermarketItem(TefteriItem item) {
        supermarketItemList.add(item);
    }

    public void removeSupermarketItem(TefteriItem item) {
        supermarketItemList.remove(item);
    }
}
