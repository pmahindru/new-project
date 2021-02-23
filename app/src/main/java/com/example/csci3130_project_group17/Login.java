package com.example.csci3130_project_group17;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;



public class Login extends AppCompatActivity {
    EditText Email;
    EditText Password;
    TextView Signin;
    TextView editTextEmail;
    TextView editTextPassword;
    Button SignupBtn;
    Button LoginBtn;
    FirebaseAuth database1;
    //FirebaseDatabase database;
    //DatabaseReference userstable;
    ProgressBar progressBar;

    public void switchTooSignUp(View view1) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.login);
        database1 = FirebaseAuth.getInstance();
        SignupBtn = findViewById(R.id.signup);
        //SignupBtn.setOnClickListener(this);
        LoginBtn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is missing");
                    editTextEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Email is missing");
                    editTextPassword.requestFocus();
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);

                database1.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}



