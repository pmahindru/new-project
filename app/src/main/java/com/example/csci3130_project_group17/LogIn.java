package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class LogIn extends AppCompatActivity{
    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference logintable = null;
    final Boolean[] errorFlag = {false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent intent = getIntent();

        //initiating the Firebase
        initializeDatabase();

        //button function take place
        OnClick();
    }
    public void switchToDashboard(){
        //dashboard is not made, which is why there is no functionality to switch to it yet :)
    }

    public void switchToSignup(){
        //dashboard is not made, which is why there is no functionality to switch to it yet :)
        Intent SignupIntent = new Intent(this, SignUp.class);
        startActivity(SignupIntent);
    }

    private void OnClick() {
        // take value of button
        Button square_button = (Button)findViewById(R.id.switchtodashboard);
        square_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = email_address();
                String password = password_Input();
                CheckEmailAndPasswordIsValid(email, password);
            }
        });

        // take value of button
        Button square_button2 = (Button)findViewById(R.id.switch2Signup);
        square_button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switchToSignup();
            }
        });
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        logintable = database.getReference().child("users");
    }

    protected boolean isInputEmpty(String str){
        return str.isEmpty();
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

    protected void errorMessageDisplay(String error){
        Toast.makeText(LogIn.this,error, Toast.LENGTH_LONG).show();
    }

    private void messageshow() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("Please check you Email and Password is incorrect \nIf you dont have an account please SignUp");
        alert_answer.setPositiveButton("ok", null);
        alert_answer.create();
        alert_answer.show();
    }

    protected String email_address() {
        EditText emailaddress = findViewById(R.id.email);
        return emailaddress.getText().toString().trim();
    }

    protected String password_Input() {
        EditText passwordInput = findViewById(R.id.password);
        return passwordInput.getText().toString().trim();
    }

    private void CheckEmailAndPasswordIsValid(String email, String password) {
        if(isInputEmpty(email) || isInputEmpty(password)) {
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
                CheckEmailAndPasswordInDatabase(email, password);
            }
        }
    }

    private void CheckEmailAndPasswordInDatabase(String email, String password) {
        logintable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (Objects.requireNonNull(dataSnapshot.child("email").getValue()).equals(email) && Objects.requireNonNull(dataSnapshot.child("password").getValue()).equals(password)){
                        errorFlag[0] = true;
                        errorMessageDisplay("go to the dashboard");
                        break;
                    }
                }
                if (!errorFlag[0]) {
                    messageshow();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
