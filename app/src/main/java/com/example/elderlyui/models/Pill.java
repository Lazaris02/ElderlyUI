package com.example.elderlyui.models;

public class Pill {
    private String name;
    private String dose;
    private String times;
    public Pill(String name,String dose,String times){
        this.name=name;
        this.dose=dose;
        this.times=times;
    }
    public Pill(){
        this.name=null;
        this.dose=null;
        this.times=null;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDose(String dose){
        this.dose=dose;
    }
    public void setTimes(String times){
        this.times=times;
    }
    public String getName(){
        return this.name;
    }
    public String getDose(){
        return this.dose;
    }
    public String getTimes(){
        return this.times;
    }
}
