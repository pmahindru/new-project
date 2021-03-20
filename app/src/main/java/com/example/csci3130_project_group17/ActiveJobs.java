package com.example.csci3130_project_group17;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Objects;


public class ActiveJobs extends AppCompatActivity {

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobapplication = null;
    DatabaseReference jobdetails = null;


    //job post related information
    HashMap<String, String> firstname;
    //user information
    SharedPreferences preferences;
    StoredData data;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activejobs);

        Intent intent = getIntent();

        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        System.out.println(uID + "------------------------------------------------------------------------------");

        //initiating the Firebase
        initializeDatabase();

        elementsfromdatabase();

    }

    private void elementsfromdatabase() {
//        jobapplication.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    if (Objects.requireNonNull(dataSnapshot.child("firstName").getValue()).equals()){
//                        errorFlag[0] = true;
//                        switchtologin();
//                        break;
//                    }
//                }
//                if (snapshot.equals(email) &&  errorFlag[0]){
//                    errorMessageDisplay("An account with this email already exists");
//                }
//                else if (!errorFlag[0]){
//                    errorMessageDisplay("Your account has been created");
//                    errorFlag[0] = false;
//                    addtodatabase(user);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        jobapplication = database.getReference("application");
        jobdetails = database.getReference("JobInformation");
    }







}
