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
        setContentView(R.layout.login);
    }
    public void switch2JobPosting(View view){
        Intent jobIntent = new Intent(this, JobPosting.class);
        startActivity(jobIntent);
    }

    public void switchLogIn(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}