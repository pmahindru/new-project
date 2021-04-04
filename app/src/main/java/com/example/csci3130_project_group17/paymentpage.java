package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentpage extends AppCompatActivity {

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference usertable = null;

    //user information
    String uID;
    SharedPreferences preferences;
    StoredData data;

    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        System.out.println(uID);

        //initiating the Firebase
        initializeDatabase();
        setupforlayout();
        Onclick();

    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        usertable = database.getReference("users");
    }

    //on click for the homepage
    public void Onclick() {
//        // take value of button
//        Button square_button2 = (Button)findViewById(R.id.switch2home);
//        square_button2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                swtich2home();
//            }
//        });
    }
//
//    public void swtich2home() {
//        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
//        startActivity(dashboardEmployee);
//    }


    private void setupforlayout() {
        usertable.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    check = (boolean) snapshot.child("employee").getValue();
                }
                System.out.println(check);

                if (check){
                    setContentView(R.layout.paymentemployee);
                }
                else {
                    setContentView(R.layout.paymentemployer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
