package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//this code is taken from the lab and modify by Pranav and Tongqi
public class Chat extends AppCompatActivity {

    //chat things
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;

    //user information
    String uID;
    StoredData data;
    SharedPreferences preferences;

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userinfo = null;
    DatabaseReference Jobinfo = null;

    //getting name and the jobinfo
    Boolean check;
    String jobid;
    String firstname_employee;
    String lastname_employee;
    String firstname_employer;
    String lastname_employer;
    String employerid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        String userId = intent.getStringExtra("userId");

        SendButton = findViewById(R.id.Send);
        input = findViewById(R.id.textInput);
        ButtonHome = findViewById(R.id.Home);
        message = new ChatMessage();
        ButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMain();

            }
        });
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);

        //get userID of logged in user
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        initializeDatabase();
        getTheNameOfEmployeeAndEmployer();
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userinfo = database.getReference("users");
        Jobinfo = database.getReference("JobInformation");
    }


    private void getTheNameOfEmployeeAndEmployer(){
        userinfo.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    firstname_employee = (String) snapshot.child("firstName").getValue();
                    lastname_employee = (String) snapshot.child("lastName").getValue();
                    check = (boolean) snapshot.child("employee").getValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        if (check){
            Intent intent = getIntent();
            jobid = intent.getStringExtra("jobId");
            Jobinfo.child(jobid).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        employerid = (String) snapshot.child("employerID").getValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            userinfo.child(employerid).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        firstname_employer = (String) snapshot.child("firstName").getValue();
                        lastname_employer = (String) snapshot.child("lastName").getValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {

        }
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}



