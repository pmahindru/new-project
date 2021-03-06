package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DashboardEmployer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);

        //get stored user data
        StoredData data = new StoredData(getApplicationContext());
        String uID = data.getStoredUserID();
    }

}