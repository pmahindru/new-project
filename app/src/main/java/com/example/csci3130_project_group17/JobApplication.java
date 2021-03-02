package com.example.csci3130_project_group17;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobApplication extends AppCompatActivity{

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userstable = null;
    EditText resume;
    int userid = 0;
    final Boolean[] errorFlag = {false};
    private static final Map<String, Object> user = new HashMap<>();

    //upload things to the database
    Button square_button = (Button)findViewById(R.id.Apply);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobapplication);

        Intent intent = getIntent();

        //initiating the Firebase
        initializeDatabase();

        //button function take place
        OnClick();

    }

    private void OnClick() {

        resume = findViewById(R.id.Resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectanduploadresume();
            }
        });

        square_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        // take value of button
        Button square_button2 = (Button)findViewById(R.id.switch2home);
        square_button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                swtich2home();
            }
        });
    }

    //upload a resume in pdf in the database i took a look from here
    //https://www.youtube.com/watch?v=lY9zSr6cxko
    private void selectanduploadresume() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData()!= null){
            resume.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            square_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    updatepdffiletodatabase(data.getData());
                }
            });
        }

    }

    private void updatepdffiletodatabase(Uri data) {

    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userstable = database.getReference().child("Application");
    }

    protected String first_Name() {
        EditText firstName = findViewById(R.id.Firstname);
        return firstName.getText().toString().trim();
    }

    protected String last_Name() {
        EditText lastname = findViewById(R.id.Lastname);
        return lastname.getText().toString().trim();
    }

    protected String email_address() {
        EditText emailaddress = findViewById(R.id.Email);
        return emailaddress.getText().toString().trim();
    }

    private void addtodatabase(Map<String, Object> user) {
        String fname = first_Name();
        String lname = last_Name();
        String email = email_address();


        UUID idOne = UUID.randomUUID();
        userid = userid + 1;
        String count = String.valueOf(idOne);
        System.out.println("I was here!");
        user.put("firstName", fname);
        user.put("lastName", lname);
        user.put("email", email);

        userstable.child(count).setValue(user);
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

    private void swtich2home() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
        startActivity(dashboardEmployee);
    }



//    public void onClick(View view) {

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
//    }

}
