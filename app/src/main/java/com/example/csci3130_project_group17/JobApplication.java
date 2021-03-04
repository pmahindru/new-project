package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobApplication extends AppCompatActivity{

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userstable = null;
    StorageReference storageReference = null;

    EditText resume;
    private Uri datauri;
    int userid = 0;
    final Boolean[] errorFlag = {false};
    private static final Map<String, Object> user = new HashMap<>();

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

        Button square_button = (Button)findViewById(R.id.Apply);
        square_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updatepdffiletodatabase(datauri);
                System.out.println("call this thing or not");
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
    public void selectanduploadresume() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 86 && resultCode == RESULT_OK && data != null && data.getData()!= null){
            resume.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            this.datauri = data.getData();
        }

    }

    private void updatepdffiletodatabase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is uploading");
        progressDialog.show();

        StorageReference reference = storageReference.child("pranav" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressLint("ShowToast")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                assert uri != null;
                PutPdftoDatabse putPdftoDatabse = new PutPdftoDatabse(resume.getText().toString(), uri.toString());
                userstable.child("resume").setValue(putPdftoDatabse);
                Toast.makeText(getApplicationContext(),"all things add to the database",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progess = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("File is uploading......" + (int) progess + "%");
            }
        });
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userstable = database.getReference("application");
        storageReference = FirebaseStorage.getInstance().getReference();
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
