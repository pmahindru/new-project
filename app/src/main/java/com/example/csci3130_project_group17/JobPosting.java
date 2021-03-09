package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;

public class JobPosting extends AppCompatActivity implements View.OnClickListener {

    private Button createButton, ButtonHome, imageButton;
    private String errorMessage;
    DatabaseReference jobInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);

        Intent jobIntent = getIntent();

        createButton = findViewById(R.id.createJobButton);
        createButton.setOnClickListener(this);
        ButtonHome = findViewById(R.id.homeButton);
        ButtonHome.setOnClickListener(this);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);

        initializedatabase();
    }

    //inital database
    public void initializedatabase() {
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }


    //get method
    protected String getJobTitle() {
        EditText title = findViewById(R.id.jobTitleInput);
        return title.getText().toString().trim();
    }

    protected String getJobType() {
        EditText type = findViewById(R.id.jobTypeInput);
        return type.getText().toString().trim();
    }

    protected String getJobPayRate() {
        EditText rate = findViewById(R.id.jobPayRateInput);

        return rate.getText().toString().trim();
    }

    protected String getJobLocation() {
        EditText location = findViewById(R.id.jobLocationInput);
        return location.getText().toString().trim();
    }

    protected String getJobDescription() {
        EditText description = findViewById(R.id.jobDescriptionInput);
        return description.getText().toString().trim();
    }


    //missing or invalid input
    protected boolean jobTitleIsEmpty(String s) {
        return s.isEmpty();
    }

    public boolean jobTypeIsEmpty(String s) {
        return s.isEmpty();
    }

    public boolean jobLocationIsEmpty(String s) {
        return s.isEmpty();
    }

    public boolean jobPayRateIsEmpty(String s) {

        return s.isEmpty();
    }

    public boolean jobDescriptionIsEmpty(String s) {
        return s.isEmpty();
    }

    protected String getErrorMessage() {
        //clear error message
        errorMessage = "";
        //determine if any fields are empty
        if (jobTitleIsEmpty(getJobTitle())) {
            errorMessage = "Enter Job Title";
        } else if (jobTypeIsEmpty(getJobType())) {
            errorMessage = "Enter Job Type";
        } else if (jobPayRateIsEmpty(getJobPayRate())) {
            errorMessage = "Enter Pay Rate";
        } else if (jobLocationIsEmpty(getJobLocation())) {
            errorMessage = "Enter Job Location";
        } else if (getJobDescription().isEmpty()) {
            errorMessage = "Enter Job Description";
        }
        //determine if invalid characters entered
        else {
            if ((!isletter(getJobTitle())) || (!isletter(getJobType()))) {
                errorMessage = "Only letters allowed";
            }
        }

        return errorMessage;
    }

    protected boolean isletter(String input) {
        return input.matches("^[A-Za-z\\s]+$");
    }

    protected void saveJobToDatabase() {
        String jobID = UUID.randomUUID().toString();
        jobInformation.child(jobID).child("jobTitle").setValue(getJobTitle());
        jobInformation.child(jobID).child("jobType").setValue(getJobType());
        jobInformation.child(jobID).child("jobPayRate").setValue(getJobPayRate());
        jobInformation.child(jobID).child("jobLocation").setValue(getJobLocation());
        jobInformation.child(jobID).child("jobDescription").setValue(getJobDescription());
    }

    protected void switchToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void switchToAddImage() {
        Intent intent1 = new Intent(this, AddImage.class);
        startActivity(intent1);
    }

    protected void switchToJobPage() {

    }

    public void onClick(View v) {
        //determine which button was pressed
        switch (v.getId()) {
            case R.id.createJobButton:
                String error = getErrorMessage();
                //if there is an error message, notify user of error
                if (!error.isEmpty()) {
                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    //if no errors, publish job in database and notify user of success
                    saveJobToDatabase();
                    Toast.makeText(getBaseContext(), "Job Successfully Created", Toast.LENGTH_LONG).show();
                }
                //switch to job page
                switchToJobPage();
                break;
            case R.id.homeButton:
                //switch to home page
                switchToMain();
                break;
            case R.id.imageButton:
                //switch to add image page
                switchToAddImage();
                break;
        }
    }
}