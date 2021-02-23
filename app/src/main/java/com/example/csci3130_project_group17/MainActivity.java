package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switch2SignUp(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void switch2JobPosting(View view){
        Intent jobIntent = new Intent(this, JobPosting.class);
        startActivity(jobIntent);
    }
    public void switch2Login(View view){
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

}