package com.example.csci3130_project_group17;

public class PutPdftoDatabse {
    public String name;
    public String URL;

    public PutPdftoDatabse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public PutPdftoDatabse(String name, String URL){
        this.name = name;
        this.URL = URL;
    }
}
