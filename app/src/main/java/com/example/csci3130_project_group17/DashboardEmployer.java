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

        //get stored user data
        StoredData data = new StoredData(getApplicationContext());
        String uID = data.getStoredUserID();

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

    }

    public void switchToCreateJob() {
        Intent createJobIntent = new Intent(this, JobPosting.class);
        startActivity(createJobIntent);
    }

    public void switchToHistory(){
        Intent HistoryIntent = new Intent(this, jobHistory.class);
        startActivity(HistoryIntent);

        setClickListeners();
    }

    private void setClickListeners() {
        Button logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {logout();};
        });
    }

    private void logout() {

        Intent switchToLogin = new Intent(this, LogIn.class);
        startActivity(switchToLogin);

    }


}