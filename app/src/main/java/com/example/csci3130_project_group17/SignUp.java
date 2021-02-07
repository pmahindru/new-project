package com.example.csci3130_project_group17;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

//    String firstName;
//    String lastName;
//    String email;
//    String password;
//    String organistion;
//    Boolean employer;

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

//    FirebaseDatabase database =  null;
//    DatabaseReference userNameRef = null;
//    DatabaseReference emailRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

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
        EditText firstName = findViewById(R.id.fnameinput);
        String fname = String.valueOf(firstName);
        EditText lastName = findViewById(R.id.lnameinput);
        String lname = String.valueOf(lastName);
        EditText emailaddress = findViewById(R.id.emailinput);
        String email = String.valueOf(emailaddress);
        EditText passwordInput = findViewById(R.id.passwordinput);
        String password = String.valueOf(passwordInput);
        EditText organisationName = findViewById(R.id.organisationinput);
        String orgname = String.valueOf(organisationName);
        Boolean employer = ((CheckBox)findViewById(R.id.employerId)) .isChecked();

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
        //Toast.makeText(MainActivity.this,"Firebaseconnection success", Toast.LENGTH_LONG).show();
    }
}