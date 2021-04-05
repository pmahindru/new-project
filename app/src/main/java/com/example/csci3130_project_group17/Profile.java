package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class Profile extends AppCompatActivity {
    DatabaseReference usersStore;
    DatabaseReference reviewStore;
    private List<Review> reviewList;
    private RecyclerView recyclerView;
    profileReviewAdapter adapter;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView = findViewById(R.id.profileReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        uID = intent.getStringExtra("profileUserID");
        initializeDatabase();


        reviewList = new ArrayList<Review>();

        loadData();
    }

    private void initializeDatabase() {
        usersStore = FirebaseDatabase.getInstance().getReference().child("users");
        reviewStore = FirebaseDatabase.getInstance().getReference().child("reviews");
    }


    private void loadData(){

        reviewStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (((dataSnapshot.child("revieweeID").getValue().toString()).equals(uID))) {
                        Review review = dataSnapshot.getValue(Review.class);
                        reviewList.add(review);
                    }
                }
                adapter = new profileReviewAdapter(reviewList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        usersStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (((dataSnapshot.getKey()).equals(uID))) {
                        TextView pFirstName = findViewById(R.id.profileFirstName);
                        TextView pLastName = findViewById(R.id.profileLastName);
                        pFirstName.setText(dataSnapshot.child("firstName").getValue().toString());
                        pLastName.setText(dataSnapshot.child("lastName").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }


}
