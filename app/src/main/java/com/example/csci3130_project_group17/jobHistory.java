package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class jobHistory extends AppCompatActivity {
    DatabaseReference jobInformation;
    private List<Job> jobs;
    private RecyclerView recyclerView;
    jobHistoryAdapter adapter;
    String uID;
    String state = "open";
    SharedPreferences preferences;
    StoredData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_history);
        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobs = new ArrayList<>();
        Intent intent = getIntent();
        initializeDatabase();


        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        TextView noJobsMessage = findViewById(R.id.noJobsMessageLayout);

        jobInformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Object employeeID = dataSnapshot.child("employeeID").getValue();
                    Object employeeState = dataSnapshot.child("employeeState").getValue();
                    if (employeeID != null && ((dataSnapshot.child("employeeID").getValue()).equals(uID)) && employeeState != null && ((dataSnapshot.child("state").getValue()).equals(state))) {
                        Job job = dataSnapshot.getValue(Job.class);
                        String id = dataSnapshot.getKey().toString();
                        job.setId(id);
                        jobs.add(job);
                    }
                }
                if (jobs.size() <= 0) {
                    TextView noJobsMessage = findViewById(R.id.noJobsMessageLayout);
                    noJobsMessage.setVisibility(View.VISIBLE);
                } else {
                    noJobsMessage.setVisibility(View.INVISIBLE);
                    adapter = new jobHistoryAdapter(jobs);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        ToggleButton jobStateToggle = findViewById(R.id.toggleButtonState);
        jobStateToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals("open")) {
                    state = "closed";
                } else {
                    state = "open";
                }
                jobs.clear();


                jobInformation.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (((dataSnapshot.child("employeeID").getValue()).equals(uID)) && ((dataSnapshot.child("state").getValue()).equals(state))) {
                                Job job = dataSnapshot.getValue(Job.class);
                                String id = dataSnapshot.getKey().toString();
                                job.setId(id);
                                jobs.add(job);
                            }
                        }
                        if (jobs.size() <= 0) {
                            TextView text = findViewById(R.id.noJobsMessageLayout);
                            noJobsMessage.setVisibility(View.VISIBLE);
                        } else {
                            noJobsMessage.setVisibility(View.INVISIBLE);
                            adapter = new jobHistoryAdapter(jobs);
                            recyclerView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            }
        });
    }

    public void initializeDatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }

}