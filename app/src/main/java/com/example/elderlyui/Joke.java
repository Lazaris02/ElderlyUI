package com.example.elderlyui;

public class Joke {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body;

    public Joke(String title,String body){
        this.title = title;
        this.body = body;
    }
}
