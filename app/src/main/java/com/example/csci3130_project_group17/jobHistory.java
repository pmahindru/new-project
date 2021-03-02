package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class jobHistory extends AppCompatActivity {
    DatabaseReference jobInformation;
    private RecyclerView historyRecView;
    jobHistoryAdapter adapter;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_history);

        Intent intent = getIntent();
        initializeDatabase();
        //TODO: replace uID w/ logged in user ID once that functionality is implemented
        uID = "5200c54f-45fb-4d0f-a9d4-51b670427395";

        historyRecView = findViewById(R.id.historyRecyclerView);
        setHistoryRecView(historyRecView);


    }

    public void initializeDatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }

    public void setHistoryRecView(RecyclerView historyRecView){
        historyRecView.setLayoutManager(
                new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Job> options
                = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(jobInformation.orderByChild("empID_state").equalTo(uID+"_closed"), Job.class)
                .build();

        adapter = new jobHistoryAdapter(options);
        historyRecView.setAdapter(adapter);
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