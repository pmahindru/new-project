package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

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

        //get stored user data
        StoredData data = new StoredData(getApplicationContext());
        String uID = data.getStoredUserID();

        setClickListeners();
    }

    private void setClickListeners() {
        Button viewJobs = (Button) findViewById(R.id.viewJobsButton);
        Button viewHistory = (Button) findViewById(R.id.employeehistoryButton);
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


        Button logout = findViewById(R.id.logoutButton2);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {logout();};
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

    private void logout() {



        Intent switchToLogin = new Intent(this, LogIn.class);
        startActivity(switchToLogin);

    }
}