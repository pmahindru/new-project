package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobPosting extends AppCompatActivity implements View.OnClickListener {

    private EditText JobTitle, TYPE, PAY, Location, Description;
    private Button Creat, home;
    DatabaseReference jobInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);

        Creat = findViewById(R.id.create);
        Creat.setOnClickListener(this);
        home = findViewById(R.id.Home);
        home.setOnClickListener(this);

        initializedatabase();
    }

    //inital database
    public void initializedatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }



    //get method
    protected String getJobTitle() {
        EditText title = findViewById(R.id.editTextTextPersonName6);
        return title.getText().toString().trim();
    }

    protected String getJobType() {
        EditText type =  findViewById(R.id.editTextTextPersonName10);
        return type.getText().toString().trim();
    }

    protected String getJobPayRate() {
        EditText rate = findViewById(R.id.editTextTextPersonName11);

        return rate.getText().toString().trim();
    }

    protected String getJobLocation(){
        EditText location = findViewById(R.id.editTextTextPersonName12);
        return location.getText().toString().trim();
    }
    protected String getJobDescription() {
        EditText description = findViewById(R.id.inputforDES);
        return description.getText().toString().trim();
    }



    //missing or erro input
    protected boolean jobTitleIsEmpty(String s){
        return s.isEmpty();
    }

    public boolean jobTypeIsEmpty(String s){
        return s.isEmpty();
    }

    public boolean jobLocationIsEmpty(String s){
        return s.isEmpty();
    }

    public boolean jobPayRateIsEmpty(String s){

        return s.isEmpty();
    }

    public boolean jobDescriptionIsEmpty(String s){
        return s.isEmpty();
    }

    protected void setErrorMessage(){
        //condition when no input
        if(jobTypeIsEmpty(getJobType())){
            Toast.makeText(getBaseContext(),"Enter Job Type",Toast.LENGTH_SHORT).show();
        }
        if(jobTitleIsEmpty(getJobTitle())){
            Toast.makeText(getBaseContext(),"enter job title",Toast.LENGTH_SHORT).show();
        }
        if(jobPayRateIsEmpty(getJobPayRate())){
            Toast.makeText(getBaseContext(),"enter job rate",Toast.LENGTH_SHORT).show();
        }
        if(getJobDescription()==null){
            Toast.makeText(getBaseContext(),"enter job description",Toast.LENGTH_SHORT).show();
        }

        //condition when erro input
        if(!isletter(getJobType())||!isletter(getJobType())||!isletter(getJobDescription())){
            Toast.makeText(getBaseContext(),"wrong input",Toast.LENGTH_SHORT).show();
        }


    }

    protected boolean isletter(String input){
        return input.matches("[A-Za-z]");
    }

    protected void setJobInformation_toDatabase(){
            jobInformation.child("jobTitle").setValue(getJobTitle());
            jobInformation.child("jobType").setValue(getJobType());
            jobInformation.child("jobPayRate").setValue(getJobPayRate());
            jobInformation.child("jobLocation").setValue(getJobLocation());
            jobInformation.child("jobDescription").setValue(getJobDescription());
    }


    protected void switchToJobPage(){
        setJobInformation_toDatabase();
        publishJob();
    }

    protected void publishJob(){
        //switch to the job page
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.create:
                setErrorMessage();
                //switch to job page
                switchToJobPage();
                break;
            case R.id.Home:
                //switch to home page
                break;
        }
    }










}