package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.*;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

//    String firstName;
//    String lastName;
//    String email;
//    String password;
//    String organistion;
//    Boolean employer;

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    FirebaseFirestore database =  null;

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

    public void addToDatabase(Map<String, Object> user){
        String TAG = "databaseError";
        database.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

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



    // the organisation is supposed to show only when the bossiness option in the spinner is used, we need to add that
    protected void showorganization(){
        CheckBox employer = findViewById(R.id.employerId);
        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText organisationName = findViewById(R.id.organisationinput);
                Spinner spinner = findViewById(R.id.spinner);
                CheckBox employee = findViewById(R.id.employeeCheck);
                if (!employer.isChecked() && !employee.isChecked()){
                    employee.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUp.this,"You have to choose one thing from the checkbox", Toast.LENGTH_LONG).show();
                }
                else{
                    if (((CheckBox) v).isChecked()) {
                        organisationName.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);
                        employee.setVisibility(View.INVISIBLE);
                    }
                    else{
                        ((CheckBox) v).setChecked(false);
                        organisationName.setVisibility(View.INVISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
                        employee.setVisibility(View.VISIBLE);
                    }
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
        arrayList.add("individual");
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
        //
        spinner.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //show organisation part
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
        Boolean result = false;
        database.collection("user")
                .whereEqualTo("email", email)
                .get();
        return result;
    }

    public boolean employerCheck(CheckBox employer, CheckBox employee){
        //checking the checkbox (isEmpty)
        return (employer.isChecked() || employee.isChecked());
    }

    //last thing to do
    public void switchToDashboard(){
    }

    public void initializeDatabase(){
        //initializing the database
        this.database = FirebaseFirestore.getInstance();
    }

    public void errorMessageDisplay(String error){
        //show the error message to the user.
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

        //String errorMessage = "";
        Boolean errorFlag = false;

        if(isInputEmpty(fname) || isInputEmpty(lname) || isInputEmpty(email) || isInputEmpty(password)) {
            errorMessageDisplay("One of the fields are empty");
            errorFlag = true;
        } else {
            //add all the checks for the
            if(emailCheck(email) && passwordCheck(password)) {
                if(userProfileCheck(email)) {
                    // Move them to login page
                    errorFlag = true;
                    // Alert dialog to tell user that profile is already in the db

                } else {
                    errorFlag = false;
                }

            } else if(!emailCheck(email)) {
                errorMessageDisplay("Email is invalid");
                errorFlag = true;
            } else if(!passwordCheck(password)) {
                errorMessageDisplay("Password is invalid. It should be have a lowercase and upper case alphabet, digit and a special character. It should be 6-15 characters in length.");
                errorFlag = true;
            }
            else if (!employerCheck(employer,employee)){
                errorMessageDisplay("checkbox is empty");
                errorFlag = true;
            }
        }

        if(!errorFlag) {
            Map<String, Object> user = new HashMap<>();
            user.put("firstName", fname);
            user.put("lastName", lname);
            user.put("email", email);
            user.put("password", password);
            user.put("employer", employer.isChecked());
            user.put("employee", employer.isChecked());
            user.put("orgName", "lol");

            //maybe add userid here?

            addToDatabase(user);
        }




//        if (errorMessage.isEmpty()) {
//            //no errors were found!
//            //much of the business logic goes here!
//            setStatusMessage(errorMessage);
//            if (isAlphanumericUserName(userName) && isValidEmailAddress(emailAddress)) {
//                saveUserNameToFirebase(userName);
//                saveEmailToFirebase(emailAddress);
//                switch2WelcomeWindow(userName,emailAddress);
//            }
//        }
//        else {
//            setStatusMessage(errorMessage);
//        }

        //below line is only for check (below line is taken from the firebase lab)
        //Toast.makeText(SignUp.this,"Sup", Toast.LENGTH_LONG).show();
    }
}