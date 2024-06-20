package com.example.elderlyui.models;

public class Pill {
    private String name;
    private String dose;
    private boolean morning;
    private boolean mesimeri;
    private boolean noon;
    private boolean night;

    public Pill() {
        this.name = null;
        this.dose = null;
        this.morning = false;
        this.mesimeri = false;
        this.noon = false;
        this.night = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;



    }

    public void setMesimeri(boolean mesimeri) {
        this.mesimeri = mesimeri;


    }

    public void setNoon(boolean noon) {
        this.noon = noon;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public String getName() {
        return this.name;
    }

    public String getDose() {
        return this.dose;
    }

    public boolean isMesimeri() {
        return mesimeri;
    }

    public boolean isMorning() {
        return morning;
    }

    public boolean isNight() {
        return night;
    }

    public boolean isNoon() {
        return noon;
    }
}
