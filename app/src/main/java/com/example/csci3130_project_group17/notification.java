package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class notification extends AppCompatActivity {


    //job information for the notification
    SharedPreferences preferences2;
    JobPosting_notification data2;
    String jobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        preferences2 = getSharedPreferences("jobsPrefs", Context.MODE_PRIVATE);
        data2 = new JobPosting_notification(preferences2);
        jobID = data2.getStoredUserID2();


        onclickbutton();

        System.out.println(jobID+"----------------------job---------------------------------------------");
        data2.clearStoredData2();
    }

    private void onclickbutton() {

    }
}