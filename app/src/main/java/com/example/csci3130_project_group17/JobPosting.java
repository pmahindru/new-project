package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobPosting extends AppCompatActivity {

    private EditText JobTitle, TYPE, PAY, Location, Description;
    private Button Creat, home;
    DatabaseReference jobInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
        JobTitle = findViewById(R.id.editTextTextPersonName6);
        TYPE = findViewById(R.id.editTextTextPersonName10);
        PAY = findViewById(R.id.editTextTextPersonName11);
        Location = findViewById(R.id.editTextTextPersonName12);
        Description = findViewById(R.id.inputforDES);
        Creat = findViewById(R.id.create);
        home = findViewById(R.id.Home);

        initializedatabase();
    }

    public void initializedatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }

    protected String getJobTitle() {
        EditText title = JobTitle;
        return title.getText().toString().trim();
    }

    protected String getJobType() {
        EditText type = TYPE;
        return type.getText().toString().trim();
    }

    protected Double getJobPayRate() {
        EditText rate = PAY;
        Double ans = Double.parseDouble(rate.getText().toString());
        return ans;
    }

    protected String getJobLocation(){
        EditText location = Location;
        return location.getText().toString().trim();
    }
    protected String getJobDescription() {
        EditText description = Description;
        return description.getText().toString().trim();
    }

    protected boolean allFieldsEnteredCheck(){
        if(getJobTitle()!=null&&getJobType()!=null&&getJobPayRate()!=null&&getJobDescription()!=null){
            return true;
        }
        return false;
    }

    protected void setErrorMessage(){
        //condition when no input
        if(getJobType()==null){
            Toast.makeText(getBaseContext(),"Enter Job Type",Toast.LENGTH_SHORT).show();
        }
        if(getJobTitle()==null){
            Toast.makeText(getBaseContext(),"enter job title",Toast.LENGTH_SHORT).show();
        }
        if(getJobPayRate()==null){
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
            jobInformation.child("jobSescription").setValue(getJobDescription());
    }










}