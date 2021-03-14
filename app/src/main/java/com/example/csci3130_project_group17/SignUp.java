package com.example.csci3130_project_group17;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static android.R.*;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userstable = null;
    int userid = 0;
    final Boolean[] errorFlag = {false};
    private static final Map<String, Object> user = new HashMap<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Intent intent = getIntent();

        //job show in spinner
        jobsspinner();

        //show the organization field for the employer
        showorganization();

        // take value of button
        Button square_button = (Button)findViewById(R.id.switchbutton);
        square_button.setOnClickListener(this);

        //initiating the Firebase
        initializeDatabase();

    }
    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userstable = database.getReference().child("users");
    }

    private void addtodatabase(Map<String, Object> user) {
        String fname = first_Name();
        String lname = last_Name();
        String email = email_address();
        String password = password_Input();
        String orgName = org_Name();
        //so that we can check if employer and employee is checked or not
        CheckBox employer = findViewById(R.id.employerId);
        CheckBox employee = findViewById(R.id.employeeCheck);

        UUID idOne = UUID.randomUUID();
        userid = userid + 1;
        String count = String.valueOf(idOne);
        System.out.println("I was here!");
        user.put("firstName", fname);
        user.put("lastName", lname);
        user.put("email", email);
        user.put("password", password);
        user.put("employer", employer.isChecked());
        user.put("employee", employee.isChecked());

        user.put("orgName", orgName);
        userstable.child(count).setValue(user);

    }

    protected boolean isInputEmpty(String str){
        return str.isEmpty();
    }

    protected void showorganization(){
        CheckBox employer = findViewById(R.id.employerId);

        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText organisationName = findViewById(R.id.organisationinput);
                Spinner spinner = findViewById(R.id.spinner);
                CheckBox employee = findViewById(R.id.employeeCheck);
                if (((CheckBox) v).isChecked()){
                    spinner.setVisibility(View.VISIBLE);
                    employee.setVisibility(View.INVISIBLE);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                            // your code here
                            String selectedItemText = (String) parentView.getItemAtPosition(position);
                            if(selectedItemText.equals("Business"))
                            {
                                organisationName.setVisibility(View.VISIBLE);
                            }
                            else {
                                organisationName.setVisibility(View.INVISIBLE);

                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }

                    });

                }
                else{
                    ((CheckBox) v).setChecked(false);
                    organisationName.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    employee.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //---------------------------spinner start here -------------------------------------------------
    //The solution to implement spinner behaviour has been used from
    //https://www.tutorialspoint.com/android/android_spinner_control.html
    protected void jobsspinner(){
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Account Type");
        arrayList.add("Individual");
        arrayList.add("Business");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, layout.simple_spinner_item,arrayList){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                }
                return true;
            }
        };
        arrayAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //-------------------------------------------------end here--------------------------------------


    //email check if it is valid or not
    public boolean emailCheck(String email){
        return email.matches("^(.+)@([a-zA-Z0-9]+.[a-zA-Z0-9].+)$");
    }

    //checking if the password is valid or not
    public boolean passwordCheck(String password){
        // regex is taken from the link because there is only way to valid for the password with the specific length
        // specific other requirement too.
        // link where i only copy the regex.
        // https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,15}$");
    }

    public void switchtologin(){
        //considering this is the main activity
        Intent switchintent = new Intent(this,MainActivity.class);
        startActivity(switchintent);
    }

    protected String first_Name() {
        EditText firstName = findViewById(R.id.fnameinput);
        return firstName.getText().toString().trim();
    }

    protected String last_Name() {
        EditText lastName = findViewById(R.id.lnameinput);

        return lastName.getText().toString().trim();
    }
    protected String email_address() {
        EditText emailaddress = findViewById(R.id.emailinput);
        return emailaddress.getText().toString().trim();
    }

    protected String password_Input() {
        EditText passwordInput = findViewById(R.id.passwordinput);
        return passwordInput.getText().toString().trim();
    }

    protected String org_Name() {
        EditText orgName = findViewById(R.id.organisationinput);
        return orgName.getText().toString().trim();
    }

    public void switchToDashboard(){
        //dashboard is not made, which is why there is no functionality to switch to it yet :)
    }

    public void errorMessageDisplay(String error){
        Toast.makeText(SignUp.this,error, Toast.LENGTH_LONG).show();
    }

    public void onClick(View view) {

        String fname = first_Name();
        String lname = last_Name();
        String email = email_address();
        String password = password_Input();

        if(isInputEmpty(fname) || isInputEmpty(lname) || isInputEmpty(email) || isInputEmpty(password)) {
            errorMessageDisplay("One of the fields are empty");
            errorFlag[0] = true;
        }
        else{
            if(!emailCheck(email)) {
                errorMessageDisplay("Email is invalid");
                errorFlag[0] = true;
            } else if(!passwordCheck(password)) {
                errorMessageDisplay("Password is invalid. It should be have a lowercase and upper case alphabet, digit and a special character. It should be 6-15 characters in length.");
                errorFlag[0] = true;
            }
            else  if (emailCheck(email) && passwordCheck(password)){
                userstable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if (Objects.requireNonNull(dataSnapshot.child("email").getValue()).equals(email)){
                                errorFlag[0] = true;
                                switchtologin();
                                break;
                            }
                        }
                        if (snapshot.equals(email) &&  errorFlag[0]){
                            errorMessageDisplay("An account with this email already exists");
                        }
                        else if (!errorFlag[0]){
                            errorMessageDisplay("Your account has been created");
                            errorFlag[0] = false;
                            addtodatabase(user);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }


}