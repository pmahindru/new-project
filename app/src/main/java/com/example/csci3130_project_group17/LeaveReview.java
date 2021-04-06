package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.UUID;

public class LeaveReview extends AppCompatActivity {
    DatabaseReference users;
    DatabaseReference reviews;

    String jobIdForReview;
    String jobEmployerID;
    String jobEmployeeID;
    String reviewerName;
    StoredData data;
    String uID;
    Boolean isEmployer;

    Button submitButton;
    RadioGroup radios;
    RadioButton selectedRadio;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_review);

        //get ids for employer, employee
        Intent i = getIntent();
        jobIdForReview = i.getStringExtra("jobId");
        jobEmployerID = i.getStringExtra("employerId");
        jobEmployeeID = i.getStringExtra("employeeId");

        //get stored data about user
        data = StoredData.getInstance();
        data.setAppContext(getApplicationContext());
        uID = data.getStoredUserID();
        isEmployer = data.getUserType();

        initalizeDatabase();

        submitButton = findViewById(R.id.submitReviewButton);
        radios = findViewById(R.id.reviewRadios);
        radios.setOnCheckedChangeListener((group, checkedId) -> {
            int radioID = radios.getCheckedRadioButtonId();
            selectedRadio = findViewById(radioID);
            rating = Integer.parseInt(selectedRadio.getText().toString());
            System.out.println(rating);
        });

        //get name of reviewer
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String currentUserID = dataSnapshot.getKey();
                    if(Objects.requireNonNull(currentUserID).equals(uID)){
                        String firstName = Objects.requireNonNull(dataSnapshot.child("firstName").getValue()).toString();
                        String lastName = Objects.requireNonNull(dataSnapshot.child("lastName").getValue()).toString();
                        String orgName = Objects.requireNonNull(dataSnapshot.child("orgName").getValue()).toString();
                        //if user has not set first or last name return org name
                        if (firstName.isEmpty() && lastName.isEmpty()){
                            reviewerName = orgName;
                            return;
                        }else{
                            reviewerName = firstName + " " + lastName;
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //get review info and sabe to database on submit click
        submitButton.setOnClickListener(v -> {
            String comment = getComment();
            String revieweeID = getRevieweeID(jobEmployerID, jobEmployeeID);
            Review review = new Review(uID, reviewerName,jobIdForReview,revieweeID, rating, comment);
            if (commentNotEmpty()){
                saveReviewToDatabase(review);
                Toast.makeText(getBaseContext(), "Review Submitted Successfully.", Toast.LENGTH_LONG).show();
                switchBackToHistory();
            } else{
                Toast.makeText(getBaseContext(), "Please Enter A Comment!", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void initalizeDatabase(){
        users = FirebaseDatabase.getInstance().getReference().child("users");
        reviews = FirebaseDatabase.getInstance().getReference().child("reviews");
    }

    private String getComment() {
        EditText comment = findViewById(R.id.reviewCommentInput);
        return comment.getText().toString().trim();
    }

    public String getRevieweeID(String jobEmployerID, String jobEmployeeID){
        if (isEmployer){
            return jobEmployeeID;
        }else{
            return jobEmployerID;
        }
    }

    public void saveReviewToDatabase(Review review){
        String reviewID = UUID.randomUUID().toString();
        reviews.child(reviewID).setValue(review);
    }

    public boolean commentNotEmpty(){
        EditText commentBox = findViewById(R.id.reviewCommentInput);
        String comment = commentBox.getText().toString().trim();
        if (comment.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public void switchBackToHistory(){
        Intent intent = new Intent(this, jobHistory.class);
        startActivity(intent);
    }



}