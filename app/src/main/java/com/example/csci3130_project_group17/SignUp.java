package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SignUp extends AppCompatActivity {

    String firstName;
    String lastName;
    String email;
    String password;
    String organistion;
    Boolean employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // take value of button
        Button square_button = (Button)findViewById(R.id.switchbutton);

    }
    public void addToDatabase(){

    }

    public boolean userProfileCheck(){
        return false;
    }
    public boolean emailCheck(){
        return false;
    }
    public boolean passwordCheck(){
        return false;
    }
    public boolean employerCheck(){
        return false;
    }
    public boolean fullNameCheck(){
        return false;
    }
    public void switchToDashboard(){
    }
    public void initializeDatabase(){
    }
    public void errorMessageDisplay(){

    }
}