package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employee);

        Intent intent = getIntent();

        setClickListeners();
    }

    private void setClickListeners() {
        Button viewJobs = (Button) findViewById(R.id.viewJobsButton);
        Button viewHistory = (Button) findViewById(R.id.employeehistoryButton);
        Button activeJobs = (Button) findViewById(R.id.activeJobsButton);
        Button Logoutuser = findViewById(R.id.logoutButton2);
        Button notification = findViewById(R.id.aNotification);

        viewJobs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToViewJobs();
            }});

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToHistory();
            }
        });

        activeJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActiveJobs();
            }
        });

        Logoutuser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                swtichtologin();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swtichtonotification();
            }
        });
    }

    public void switchToViewJobs() {
        Intent viewJobsIntent = new Intent(this, ViewJobs.class);
        startActivity(viewJobsIntent);

    }

    public void switchToHistory(){
        Intent HistoryIntent = new Intent(this, jobHistory.class);
        startActivity(HistoryIntent);
    }

    public void switchToActiveJobs(){
        Intent activejobs = new Intent(this, ActiveJobs.class);
        startActivity(activejobs);
    }

    public void swtichtologin(){
        Intent switchToLogin = new Intent(this, LogIn.class);
        startActivity(switchToLogin);
        finish();
    }

    public void swtichtonotification(){
        Intent switchTonotification = new Intent(this, notification.class);
        startActivity(switchTonotification);
    }
}