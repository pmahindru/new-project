package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class JobPosting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
    }

    protected String getJobTitle() {
        EditText jobTitle = findViewById(R.id.editTextTextPersonName6);
        return jobTitle.getText().toString().trim();
    }

    protected String getJobType() {
        EditText jobType = findViewById(R.id.editTextTextPersonName10);
        return jobType.getText().toString().trim();
    }

    protected String getJobPayRate() {
        EditText jobPayRate = findViewById(R.id.editTextTextPersonName11);
        return jobPayRate.getText().toString().trim();
    }

    protected String getJobLocation() {
        EditText jobLocation = findViewById(R.id.editTextTextPersonName12);
        return jobLocation.getText().toString().trim();
    }

    protected String getJobDescription() {
        EditText jobDescription = findViewById(R.id.inputforDES);
        return jobDescription.getText().toString().trim();
    }

    protected boolean jobTitleIsEmpty(String title) {
        boolean jobTitleIsEmpty = true;
        return jobTitleIsEmpty;
    }

    protected boolean jobTypeIsEmpty(String jobType) {
        boolean jobTypeIsEmpty = true;
        return jobTypeIsEmpty;
    }

    protected boolean jobLocationIsEmpty(String location) {
        boolean jobLocationIsEmpty = true;
        return jobLocationIsEmpty;
    }

    protected boolean jobPayRateIsEmpty(String payRate) {
        boolean jobPayRateIsEmpty = true;
        return jobPayRateIsEmpty;
    }

    protected boolean jobDescriptionIsEmpty(String description) {
        boolean jobDescriptionIsEmpty = true;
        return jobDescriptionIsEmpty;
    }

    protected boolean allFieldsEnteredCheck() {
        boolean allFieldsEntered = false;
        return allFieldsEntered;
    }






}