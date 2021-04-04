package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class paymentnotificaton extends AppCompatActivity {

    //user information
    String uID;
    SharedPreferences preferences;
    StoredData data;
    Boolean isEmployer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentemployee);

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        isEmployer = data.getUserType();

//        if(isEmployer){
//
//        }
//        else {
//
//        }
    }

}
