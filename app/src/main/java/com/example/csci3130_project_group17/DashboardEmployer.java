package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class DashboardEmployer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);

        //get stored user data
        String uID = getStoredUserID();
    }
    public String getStoredUserID(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        String uID = sp.getString("uID", "");
        return uID;
    }
}