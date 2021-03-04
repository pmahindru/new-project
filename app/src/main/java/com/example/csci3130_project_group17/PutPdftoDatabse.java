package com.example.csci3130_project_group17;

public class PutPdftoDatabse {
    public String name;
    public String url;

    public PutPdftoDatabse() {
    }

    public PutPdftoDatabse(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String URL) {
        this.url = URL;
    }

}
