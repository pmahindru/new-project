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




//    public void switch2SignUp(View view) {
//        Intent intent = new Intent(this, SignUp.class);
//        startActivity(intent);
//    }
public class LogIn extends AppCompatActivity implements View.OnClickListener{
    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference logintable = null;
    int userid = 0;
    final Boolean[] errorFlag = {false};
    private static final Map<String, Object> user = new HashMap<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Intent intent = getIntent();

        // take value of button
        Button square_button = (Button)findViewById(R.id.switchtodashboard);
        square_button.setOnClickListener(this);

        //initiating the Firebase
        initializeDatabase();

    }
    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        logintable = database.getReference().child("login");
    }

    private void addtodatabase(Map<String, Object> user) {
        String email = email_address();
        String password = password_Input();

        UUID idOne = UUID.randomUUID();
        userid = userid + 1;
        String count = String.valueOf(idOne);
        System.out.println("I was here!");

        user.put("email", email);
        user.put("password", password);
        logintable.child(count).setValue(user);
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

//    public void switchtoSignup(){
//        //considering this is the main activity
//        Intent switchintent = new Intent(this, SignUp.class);
//        startActivity(switchintent);
//    }

    protected String email_address() {
        EditText emailaddress = findViewById(R.id.email);
        return emailaddress.getText().toString().trim();
    }

    protected String password_Input() {
        EditText passwordInput = findViewById(R.id.password);
        return passwordInput.getText().toString().trim();
    }

    public void switchToDashboard(){
        //dashboard is not made, which is why there is no functionality to switch to it yet :)
    }

    public void errorMessageDisplay(String error){
        Toast.makeText(LogIn.this,error, Toast.LENGTH_LONG).show();
    }

    public void onClick(View view) {

        String email = email_address();
        String password = password_Input();

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
                logintable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if (!Objects.requireNonNull(dataSnapshot.child("email").getValue()).equals(email)){
                                errorFlag[0] = false;
                                //after i got the sigun and all other pages i can intent easily
//                                switchtoSignup();
                                break;
                            }
                        }
                        if (snapshot.equals(email) &&  errorFlag[0]){
                            errorMessageDisplay("An account with this email already exists");
                        }
                        else if (!errorFlag[0]){
                            errorMessageDisplay("adding in the database and go to the dashboard");
                            errorFlag[0] = false;
                            addtodatabase(user);
                            switchToDashboard();
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
