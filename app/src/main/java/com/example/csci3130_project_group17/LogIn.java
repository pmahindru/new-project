package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity{
    private DatabaseReference databaseReference;
    private User user;
    private final TextView err = (TextView)findViewById(R.id.logIn_fail_msg_field);
    private final TextView pw = (TextView)findViewById(R.id.logIn_password);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button confirm = findViewById(R.id.logIn_button_confirm);
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.logIn_email);
                EditText password = findViewById(R.id.logIn_password);

                getUserByEmail(email.getText().toString());

                if(user == null){
                    logInFail();
                }
                else{
                    user.logInCheck(password.getText().toString());
                }
            }
        });

        Button signUp = findViewById(R.id.logIn_button_signup);
        Intent switchToSignUp = new Intent(this, SignUp.class);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(switchToSignUp);
            }
        });
    }

    //if the email and password don't match, clear password textbox and display error massage
    @SuppressLint("SetTextI18n")
    private void logInFail() {
        err.setText("email and password not match, or the email doesn't exist");
        pw.setText("");
    }

    //get user information from database by email
    public User getUserByEmail(String email){
        databaseReference.equalTo(email, "email")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                user = null;
            }
        });

        return user;
    }

}
