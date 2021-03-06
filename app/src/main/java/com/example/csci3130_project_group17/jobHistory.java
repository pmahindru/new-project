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
import java.util.Objects;

public class jobHistory extends AppCompatActivity {
    DatabaseReference jobInformation;
    DatabaseReference users;
    private List<Job> jobs;
    private RecyclerView recyclerView;
    jobHistoryAdapter adapter;
    String uID;
    String state = "open";
    Boolean isEmployer = false;
    String userIDSearchTerm = "employeeID";
    StoredData data;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_history);
        recyclerView = findViewById(R.id.notification_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobs = new ArrayList<>();
        Intent intent = getIntent();
        initializeDatabase();

        //get userID of logged in user
        data = StoredData.getInstance();
        data.setAppContext(getApplicationContext());
        uID = data.getStoredUserID();
        context = getApplicationContext();
        checkIfEmployer();
        System.out.println(userIDSearchTerm);

        TextView noJobsMessage = findViewById(R.id.noJobsMessageLayout);

        //get list of jobs from user depending toggle button
        loadData(noJobsMessage);

        ToggleButton jobStateToggle = findViewById(R.id.toggleButtonState);

        //change type of jobs to be shown
        jobStateToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStateView();
                //clear previous jobs shown
                jobs.clear();
                //get updated list of jobs
                loadData(noJobsMessage);
            }


        });
    }

    private void initializeDatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
        users = FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void checkIfEmployer() {
        users.child(uID).child("employer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isEmployer = snapshot.getValue(Boolean.class);
                if (isEmployer){
                    System.out.println("is employer");
                    userIDSearchTerm = "employerID";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadData(TextView noJobsMessage ){
        jobInformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //get list of jobs
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                        if (((Objects.requireNonNull(dataSnapshot.child(userIDSearchTerm).getValue())).equals(uID)) && ((Objects.requireNonNull(dataSnapshot.child("state").getValue())).equals(state))) {
                            Job job = dataSnapshot.getValue(Job.class);
                            Objects.requireNonNull(job).setJobLocationCoordinates(dataSnapshot.child("jobLocationCoordinates").getValue(location.class));
                            job.setId(id);
                            jobs.add(job);
                        }
                }
                changeEmptyMessageVisability(noJobsMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    private void changeStateView() {
        if (state.equals("open")) {
            state = "closed";
        } else {
            state = "open";
        }
    }

    //show message if user has no jobs
    private void changeEmptyMessageVisability(TextView noJobsMessage) {
        if (jobs.size() <= 0) {
            noJobsMessage.setVisibility(View.VISIBLE);
        } else {
            noJobsMessage.setVisibility(View.INVISIBLE);
            adapter = new jobHistoryAdapter(jobs, context, uID);
            recyclerView.setAdapter(adapter);
        }
    }

}
