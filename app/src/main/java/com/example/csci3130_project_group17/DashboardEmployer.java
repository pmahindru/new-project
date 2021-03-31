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

        Button profileBttn = findViewById(R.id.employerProfileButton);
        profileBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToProfile();
            };
        });
    }

    public void switchToCreateJob() {
        Intent createJobIntent = new Intent(this, JobPosting.class);
        startActivity(createJobIntent);
    }

    public void switchToHistory(){
        Intent HistoryIntent = new Intent(this, jobHistory.class);
        HistoryIntent.putExtra("userType", "EMPLOYER");
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

    public void switchToProfile(){
        Intent ProfileIntent = new Intent(this, Profile.class);
        startActivity(ProfileIntent);
    }
}