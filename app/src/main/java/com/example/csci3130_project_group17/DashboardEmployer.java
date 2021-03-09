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
        Button createJobButton= (Button) findViewById(R.id.employerCreateJobButton);
        createJobButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToCreateJob();
            }
        });
    }
    private void switchToCreateJob(){
        Intent CreateJobIntent = new Intent(this, JobPosting.class);
        startActivity(CreateJobIntent);

    }

}