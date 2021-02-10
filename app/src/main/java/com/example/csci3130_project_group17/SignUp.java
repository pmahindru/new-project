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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.*;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userstable = null;
    int userid = 1;
    Boolean result;

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

    public void addToDatabase(){

    }

    protected boolean isInputEmpty(String str){
        return str.isEmpty();
    }

    //could be replaced by a single is empty function
    //firstname check if it is empty or not
    protected boolean isEmptyfirstname(String firstName){
        return firstName.isEmpty();
    }

    //lastname check if it is empty or not
    protected boolean isEmptylastname(String lastName){
        return lastName.isEmpty() ;
    }

    //email check if it is empty or not
    protected boolean isEmptyemail(String email){
        return email.isEmpty();
    }

    //password check if it is empty or not
    protected boolean isEmptypassword(String password){
        return password.isEmpty();
    }



    // the organisation is supposed to show only when the bussiness option in the spinner is used, we need to add that
    protected void showorganization(){
        CheckBox employer = findViewById(R.id.employerId);
        Spinner spinner = findViewById(R.id.spinner);

        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText organisationName = findViewById(R.id.organisationinput);
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
                                organisationName.setVisibility(View.VISIBLE); // add part to show organisation name only when spinner option is checked
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
    //this thing is reference from the given link
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

    // just need to add in the part where we report if the
    public boolean userProfileCheck(String email){
        //not done have to complete
//        result = true;
//        userstable.equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    result = false;
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
        return result;
    }

    //check for the employer and employee check s empty or not
    public boolean employerCheck(){
        return false;
    }

    public void switchToDashboard(){
    }

    public void errorMessageDisplay(String error){
        Toast.makeText(SignUp.this,error, Toast.LENGTH_LONG).show();

    }

    public void onClick(View view) {

        //all inputs are here
        EditText firstName = findViewById(R.id.fnameinput);
        EditText lastName = findViewById(R.id.lnameinput);
        EditText emailaddress = findViewById(R.id.emailinput);
        EditText passwordInput = findViewById(R.id.passwordinput);

        //so that we can check if employer and employee is checked or not
        CheckBox employer = findViewById(R.id.employerId);
        CheckBox employee = findViewById(R.id.employeeCheck);

        //convert input into string format so that we check for it
        String fname = firstName.getText().toString();
        String lname = lastName.getText().toString();
        String email = emailaddress.getText().toString();
        String password = passwordInput.getText().toString();

        Boolean errorFlag = false;

        if(isInputEmpty(fname) || isInputEmpty(lname) || isInputEmpty(email) || isInputEmpty(password)) {
            errorMessageDisplay("One of the fields are empty");
            errorFlag = true;
        }
        else{
            //add all the checks for the
            if(!emailCheck(email)) {
                errorMessageDisplay("Email is invalid");
                errorFlag = true;
            } else if(!passwordCheck(password)) {
                errorMessageDisplay("Password is invalid. It should be have a lowercase and upper case alphabet, digit and a special character. It should be 6-15 characters in length.");
                errorFlag = true;
            }

        }

        if(!errorFlag) {
            String count = String.valueOf(userid);
            System.out.println("I was here!");
            Map<String, Object> user = new HashMap<>();
            user.put("firstName", fname);
            user.put("lastName", lname);
            user.put("email", email);
            user.put("password", password);
            user.put("employer", employer.isChecked());
            user.put("employee", employee.isChecked());
            user.put("orgName", "lol");

            //maybe add userid here?

            userstable.child(count).setValue(user);
            userid = userid + 1;

        }
    }
}