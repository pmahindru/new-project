package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class LeaveReview extends AppCompatActivity {
    DatabaseReference users;
    DatabaseReference reviews;

    String jobIdForReview;
    String jobEmployerID;
    String jobEmployeeID;
    String revieeName;
    SharedPreferences preferences;
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


        Intent i = getIntent();
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        isEmployer = data.getUserType();

        jobIdForReview = i.getStringExtra("jobId");
        System.out.println(jobIdForReview);
        jobEmployerID = i.getStringExtra("employerId");
        System.out.println("cur user: \t" + uID);
        jobEmployeeID = i.getStringExtra("employeeId");
        System.out.println("employer: \t" + jobEmployerID);
        System.out.println("employer: \t" + jobEmployeeID);
        System.out.println("reviewee: \t" + getRevieweeID(jobEmployerID,jobEmployeeID));


        initalizeDatabase();


        submitButton = findViewById(R.id.submitReviewButton);
        radios = findViewById(R.id.reviewRadios);
        radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioID = radios.getCheckedRadioButtonId();
                selectedRadio = findViewById(radioID);
                rating = Integer.parseInt(selectedRadio.getText().toString());
                System.out.println(rating);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = getComment();
                String revieweeID = getRevieweeID(jobEmployerID, jobEmployeeID);
                Review review = new Review(uID, revieweeID, rating, comment);
                saveReviewToDatabase(review);
                System.out.println(revieweeID);

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
    /**
    private  void getReviewerName(String userID){
/**
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey().toString();
                    if (((dataSnapshot.child(id).getValue()).equals(userID))) {
                        String firstName = dataSnapshot.child(userID).child("firstName").getValue().toString();
                        if (isEmployer && firstName.isEmpty()){
                            revieeName = dataSnapshot.child(userID).child("orgName").getValue().toString();
                        }else{
                            String lastName = dataSnapshot.child(userID).child("lastName").getValue().toString();
                            revieeName = firstName + " " +lastName;
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
*/
    public void saveReviewToDatabase(Review review){
        String reviewID = UUID.randomUUID().toString();

        reviews.child(reviewID).setValue(review);
    }



}