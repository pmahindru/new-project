package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardEmployee extends AppCompatActivity {
    StoredData data;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employee);
        //get userID of logged in user
        data = StoredData.getInstance();
        data.setAppContext(getApplicationContext());
        uID = data.getStoredUserID();

        Intent intent = getIntent();

        setClickListeners();
    }

    private void setClickListeners() {
        Button viewJobs = (Button) findViewById(R.id.viewJobsButton);
        Button viewHistory = (Button) findViewById(R.id.employeehistoryButton);
        Button activeJobs = (Button) findViewById(R.id.activeJobsButton);
        Button Logoutuser = findViewById(R.id.logoutButton2);
        Button notification = findViewById(R.id.aNotification);
        Button payment = findViewById(R.id.employeePaymentsButton);
        Button profile = findViewById(R.id.employeeProfileButton);

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

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swtichtopaymentpage();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToProfile();
            }
        });
    }

    private void swtichtopaymentpage() {
        Intent viewJobsIntent = new Intent(this, paymentnotificaton.class);
        startActivity(viewJobsIntent);
        Button profileBttn = findViewById(R.id.employeeProfileButton);
        profileBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToProfile();
            };
        });

    }

    public void switchToViewJobs() {
        Intent viewJobsIntent = new Intent(this, ViewJobs.class);
        startActivity(viewJobsIntent);

    }

    public void switchToHistory(){
        Intent HistoryIntent = new Intent(this, jobHistory.class);
        HistoryIntent.putExtra("userType", "EMPLOYEE");
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

    public void switchToProfile(){
        Intent ProfileIntent = new Intent(this, Profile.class);
        ProfileIntent.putExtra("profileUserID", uID);
        startActivity(ProfileIntent);
    }
}