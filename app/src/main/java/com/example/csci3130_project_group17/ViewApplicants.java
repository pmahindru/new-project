package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewApplicants extends AppCompatActivity {

    private RecyclerView recyclerView;
    ApplicantAdapter adapter;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_job_applicants);

        db = FirebaseDatabase.getInstance().getReference().child("application");



        recyclerView = findViewById(R.id.recyclerView2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Applicant> options = new FirebaseRecyclerOptions.Builder<Applicant>().setQuery(db, Applicant.class).build();

        adapter = new ApplicantAdapter(options);

        recyclerView.setAdapter(adapter);
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