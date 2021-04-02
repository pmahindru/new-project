package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

public class Preference extends AppCompatActivity {

    //set varible
    private TextView textView1;
    private TextView textView2;
    private EditText editText1;
    private EditText editText2;
    private Button applyRatingBtn;
    private Button applySalaryBtn;
    private Button saveRatingBtn;
    private Button saveSalaryBtn;
    private Button homeBtn;
    private Switch switch1;
    private Switch switch2;

    //set constance
    public static final String SharedPreference = "sharedPrefs";
    public static final String TEXT1 = "text1";
    public static final String TEXT2 = "text2";
    public static final String SWITCH1 = "switch1";
    public static final String SWITCH2 = "switch2";

    private String text1;
    private String text2;
    private boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        //set value for buttons
        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);
        editText1 = (EditText) findViewById(R.id.edittext);
        editText2 = (EditText) findViewById(R.id.edittext2);
        applyRatingBtn = (Button) findViewById(R.id.applyRatingBtn);
        applySalaryBtn = (Button) findViewById(R.id.applySalaryBtn);
        saveRatingBtn = (Button) findViewById(R.id.saveRatingBtn);
        saveSalaryBtn = (Button) findViewById(R.id.saveSalaryBtn);
        homeBtn = (Button) findViewById(R.id.homeBtn);
        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);

        applyRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView1.setText(editText1.getText().toString());
            }
        });
        applySalaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setText(editText2.getText().toString());
            }
        });

        saveRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRatingData();
            }
        });
        saveSalaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSalaryData();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToHome();
            }
        });

        loadData();
        updateViews();
    }
    //go back to the employee page
    private void switchToHome() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
        startActivity(dashboardEmployee);
    }
    public void saveRatingData() {
        //set sharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT1, textView1.getText().toString());
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();
        //message when sucessfully add data
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    public void saveSalaryData() {
        //set sharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT2, textView2.getText().toString());
        editor.putBoolean(SWITCH2, switch2.isChecked());

        editor.apply();
        //message when sucessfully add data
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        //set sharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPreference, MODE_PRIVATE);
        text1 = sharedPreferences.getString(TEXT1, "");
        text2 = sharedPreferences.getString(TEXT2, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
        switchOnOff = sharedPreferences.getBoolean(SWITCH2, false);
    }
    //
    public void updateViews() {
        textView1.setText(text1);
        textView2.setText(text2);
        switch1.setChecked(switchOnOff);
        switch2.setChecked(switchOnOff);
    }
}
//reference:
//Coding in Flow. How to Save variables in SharedPreferences - Android Studio tutorial. (2017, October 07). Retrieved April 02, 2021, from https://www.youtube.com/watch?v=fJEFZ6EOM9o&amp;t=16s
//reference:
//Android Coding. How to save data in Shared preference in Android Studio | SharedPreference | Android coding. (2019, January 11). Retrieved April 02, 2021, from https://www.youtube.com/watch?v=zrlemc3UefY