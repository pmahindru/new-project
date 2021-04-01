package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewApplicants extends AppCompatActivity {

    private RecyclerView recyclerView;
    ApplicantAdapter adapter;
    Query db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        String jobId = null;

        if(getIntent().getExtras() != null){
                Intent i = getIntent();

                jobId = i.getStringExtra("jobId");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_job_applicants);

        db = FirebaseDatabase.getInstance().getReference("application").orderByChild("jobId").equalTo(jobId);

        recyclerView = findViewById(R.id.recyclerView2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Applicant> options = new FirebaseRecyclerOptions.Builder<Applicant>().setQuery(db, Applicant.class).build();

        adapter = new ApplicantAdapter(options);

        recyclerView.setAdapter(adapter);

        Button home = findViewById(R.id.switch2home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DashboardEmployer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}