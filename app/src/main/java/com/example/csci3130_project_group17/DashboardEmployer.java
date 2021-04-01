package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardEmployer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);

        setClickListeners();
    }

    private void setClickListeners() {

        Button createJobButton = (Button) findViewById(R.id.employerCreateJobButton);
        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCreateJob();
            }
        });

        Button viewHistory = (Button) findViewById(R.id.employerHistoryButton);
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToHistory();
            }
        });

        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                swtichtologin();
            };
        });

        Button activeJobsBttn = findViewById(R.id.activeEmployeesButton);
        activeJobsBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToJobApplications();
            };
        });

        Button notification = findViewById(R.id.employeraNotification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swtichtonotification();
            }
        });
    }

    public void switchToCreateJob() {
        Intent createJobIntent = new Intent(this, JobPosting.class);
        startActivity(createJobIntent);
    }

    public void switchToHistory(){
        Intent HistoryIntent = new Intent(this, jobHistory.class);
        startActivity(HistoryIntent);
    }

    public void swtichtologin(){
        Intent switchToLogin = new Intent(this, LogIn.class);
        startActivity(switchToLogin);
        finish();
    }

    public void switchToJobApplications(){
        Intent HistoryIntent = new Intent(this, ActiveJobs.class);
        startActivity(HistoryIntent);
    }

    public void swtichtonotification(){
        Intent switchTonotification = new Intent(this, notification.class);
        startActivity(switchTonotification);
    }
}