package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class notification extends AppCompatActivity {


    //user information
    SharedPreferences job_preferences;
    JobPosting_notification job_data;
    String job_uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        job_preferences = getSharedPreferences("current_jobs", Context.MODE_PRIVATE);
        job_data = new  JobPosting_notification(job_preferences);
        job_uID = job_data.getStoredUserID2();

        System.out.println(job_uID+"-------------------------------------------------------------------");

    }


}