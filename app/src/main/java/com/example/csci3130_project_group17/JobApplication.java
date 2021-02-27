package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class JobApplication extends AppCompatActivity  implements View.OnClickListener{

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
        setContentView(R.layout.jobapplication);

        Intent intent = getIntent();

        // take value of button
        Button square_button = (Button)findViewById(R.id.Apply);
        square_button.setOnClickListener(this);

        //initiating the Firebase
        initializeDatabase();
    }
    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userstable = database.getReference().child("users");
    }

//    private void addtodatabase(Map<String, Object> user) {
//        String fname = first_Name();
//        String lname = last_Name();
//        String email = email_address();
//        String password = password_Input();
//        String orgName = org_Name();
//        //so that we can check if employer and employee is checked or not
//        CheckBox employer = findViewById(R.id.employerId);
//        CheckBox employee = findViewById(R.id.employeeCheck);
//
//        UUID idOne = UUID.randomUUID();
//        userid = userid + 1;
//        String count = String.valueOf(idOne);
//        System.out.println("I was here!");
//        user.put("firstName", fname);
//        user.put("lastName", lname);
//        user.put("email", email);
//        user.put("password", password);
//        user.put("employer", employer.isChecked());
//        user.put("employee", employee.isChecked());
//
//        user.put("orgName", orgName);
//        userstable.child(count).setValue(user);
//    }
//
//    protected boolean isInputEmpty(String str){
//        return str.isEmpty();
//    }
//
//    //email check if it is valid or not
//    public boolean emailCheck(String email){
//        return email.matches("^(.+)@([a-zA-Z0-9]+.[a-zA-Z0-9].+)$");
//    }
//
//    //checking if the password is valid or not
//    public boolean passwordCheck(String password){
//        // regex is taken from the link because there is only way to valid for the password with the specific length
//        // specific other requirement too.
//        // link where i only copy the regex.
//        // https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
//        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,15}$");
//    }
//
//    protected String first_Name() {
//        EditText firstName = findViewById(R.id.fnameinput);
//        return firstName.getText().toString().trim();
//    }
//
//    protected String last_Name() {
//        EditText lastName = findViewById(R.id.lnameinput);
//
//        return lastName.getText().toString().trim();
//    }
//    protected String email_address() {
//        EditText emailaddress = findViewById(R.id.emailinput);
//        return emailaddress.getText().toString().trim();
//    }
//
//    protected String password_Input() {
//        EditText passwordInput = findViewById(R.id.passwordinput);
//        return passwordInput.getText().toString().trim();
//    }
//
//    protected String org_Name() {
//        EditText orgName = findViewById(R.id.organisationinput);
//        return orgName.getText().toString().trim();
//    }
//
//
//    public void errorMessageDisplay(String error){
//        Toast.makeText(JobApplication.this,error, Toast.LENGTH_LONG).show();
//    }

    public void onClick(View view) {

//        String fname = first_Name();
//        String lname = last_Name();
//        String email = email_address();
//        String password = password_Input();
//
//        if(isInputEmpty(fname) || isInputEmpty(lname) || isInputEmpty(email) || isInputEmpty(password)) {
//            errorMessageDisplay("One of the fields are empty");
//            errorFlag[0] = true;
//        }
//        else{
//            if(!emailCheck(email)) {
//                errorMessageDisplay("Email is invalid");
//                errorFlag[0] = true;
//            } else if(!passwordCheck(password)) {
//                errorMessageDisplay("Password is invalid. It should be have a lowercase and upper case alphabet, digit and a special character. It should be 6-15 characters in length.");
//                errorFlag[0] = true;
//            }
//            else  if (emailCheck(email) && passwordCheck(password)){
//                userstable.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                            if (Objects.requireNonNull(dataSnapshot.child("email").getValue()).equals(email)){
//                                errorFlag[0] = true;
//                                break;
//                            }
//                        }
//                        if (snapshot.equals(email) &&  errorFlag[0]){
//                            errorMessageDisplay("An account with this email already exists");
//                        }
//                        else if (!errorFlag[0]){
//                            errorMessageDisplay("Your account has been created");
//                            errorFlag[0] = false;
//                            addtodatabase(user);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//            }
//        }
    }
}
