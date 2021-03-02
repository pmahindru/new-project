package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardEmployer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);

        setClickListeners();
    }

    private void setClickListeners() {
        Button logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {logout();};
        });
    }

    private void logout() {

        Intent switchToLogin = new Intent(this, LogIn.class);
        startActivity(switchToLogin);

    }


}