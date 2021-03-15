package com.example.csci3130_project_group17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switch2SignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void switch2JobPosting(View view){
        Intent jobIntent = new Intent(this, JobPosting.class);
        startActivity(jobIntent);
    }

    public void switch2JobDetail(View view){
        Intent intent = new Intent(this, JobDetail.class);
        intent.putExtra("jobId", "5200c54f-45fb-4d0f-a9d4-51b670427395");
        startActivity(intent);
    }

}