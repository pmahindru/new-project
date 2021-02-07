package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.R.*;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

//    String firstName;
//    String lastName;
//    String email;
//    String password;
    String organistion;
//    Boolean employer;

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    FirebaseDatabase database =  null;
    DatabaseReference userNameRef = null;
    DatabaseReference emailRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //show the organization field for the employer
        showorganization();

        //job show in spinner
        jobsspinner();

        // take value of button
        Button square_button = (Button)findViewById(R.id.switchbutton);
        square_button.setOnClickListener(this);

        //initiating the Firebase
        initializeDatabase();

    }

    public void addToDatabase(){

    }

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

    protected void showorganization(){
        CheckBox employer = findViewById(R.id.employerId);

        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText organisationName = findViewById(R.id.organisationinput);
                if (((CheckBox) v).isChecked()){
                    organisationName.setVisibility(View.VISIBLE);
                }
                else{
                    ((CheckBox) v).setChecked(false);
                    organisationName.setVisibility(View.INVISIBLE);
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


    public boolean userProfileCheck(){
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

    public void onClick(View view) {

        //all inputs are here
        EditText firstName = findViewById(R.id.fnameinput);
        EditText lastName = findViewById(R.id.lnameinput);
        EditText emailaddress = findViewById(R.id.emailinput);
        EditText passwordInput = findViewById(R.id.passwordinput);

        //convert input into string format so that we check for it
        String fname = String.valueOf(firstName);
        String lname = String.valueOf(lastName);
        String email = String.valueOf(emailaddress);
        String password = String.valueOf(passwordInput);

//        String userName = getUserName();
//        String emailAddress = getEmailAddress();
//        String errorMessage = new String();

//        if (isEmptyUserName(userName)) {
//            errorMessage = getResources().getString(R.string.EMPTY_USER_NAME);
//        }
//        else {
//            //check for valid user name and valid email email address
//            if(!isAlphanumericUserName(userName)){
//                errorMessage = getResources().getString(R.string.NON_ALPHA_NUMERIC_USER_NAME);
//            }
//            else if (!isValidEmailAddress(emailAddress)){
//                errorMessage = getResources().getString(R.string.INVALID_EMAIL_ADDRESS);
//            }
//            else {
//                errorMessage = getResources().getString(R.string.EMPTY_STRING);
//            }
//        }

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
        Toast.makeText(SignUp.this,"Firebaseconnection success", Toast.LENGTH_LONG).show();
    }
}