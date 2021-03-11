package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class JobApplication extends AppCompatActivity{

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userstable = null;
    StorageReference storageReference = null;

    //for the resume and add in the database
    EditText resume;
    private Uri datauri;

    //for adding in the database
    private static final Map<String, Object> user = new HashMap<>();

    //giving the specific id to each user
    UUID idOne = UUID.randomUUID();
    String count = String.valueOf(idOne);

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

        Button square_button = (Button)findViewById(R.id.Apply);
        square_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckEmailAndPasswordIsValid(email_address(), first_Name(), last_Name(),phonenumber(),resume.getText().toString());
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

        resume = findViewById(R.id.Resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectanduploadresume();
            }
        });

    }

    private void swtich2home() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
        startActivity(dashboardEmployee);
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

    protected String phonenumber() {
        EditText phonenumber = findViewById(R.id.Phonenumber);
        return phonenumber.getText().toString().trim();
    }

    private void addtodatabase(Map<String, Object> user) {
        String fname = first_Name();
        String lname = last_Name();
        String email = email_address();
        String phonenumber = phonenumber();

        user.put("firstName", fname);
        user.put("lastName", lname);
        user.put("email", email);
        user.put("phone_number", phonenumber);

        userstable.child(count).setValue(user);
    }

    protected boolean isInputEmpty(String str){
        return str.isEmpty();
    }

    //email check if it is valid or not
    //taken from the given link
    //https://java2blog.com/validate-phone-number-java/
    public boolean emailCheck(String email){
        return email.matches("^(.+)@([a-zA-Z0-9]+.[a-zA-Z0-9].+)$");
    }

    //phone number check if it is valid or not
    //taken from thr given link
    //https://www.tutorialspoint.com/how-to-verify-enter-number-is-phone-number-or-not-using-regex-in-android
    public boolean Phonenumber(String phone){
        String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        return phone.matches(pattern);
    }

    //resume is uploaded or not
    public boolean Resume(String resume){
        return resume.matches("([a-zA-Z0-9\\s_\\\\.\\-:])+(.doc|.docx|.pdf)$");
    }

    private void messageshow() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("Application is submitted");
        alert_answer.setPositiveButton("ok", null);
        alert_answer.create();
        alert_answer.show();
    }

    protected void errorMessageDisplay(String error){
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage(error);
        alert_answer.setPositiveButton("ok",null);
        alert_answer.create();
        alert_answer.show();
    }

    private void CheckEmailAndPasswordIsValid(String email, String firstname, String lastname, String phonenumber, String resume) {
        if(isInputEmpty(email) || isInputEmpty(firstname) || isInputEmpty(lastname) || isInputEmpty(phonenumber) || isInputEmpty(resume)) {
            errorMessageDisplay("One of the fields are empty");
        }
        else{
            if(!emailCheck(email)) {
                errorMessageDisplay("Email is invalid");
            }
            else if (!Phonenumber(phonenumber)){
                errorMessageDisplay("Phone number is invalid");
            }
            else if (!Resume(resume)){
                errorMessageDisplay("Please upload the pdf fle");
            }
            else  if (emailCheck(email) && Resume(resume)){
                addtodatabase(user);
                updatepdffiletodatabase(datauri);
                messageshow();
            }
        }
    }


    //upload a resume in pdf in the database i took a look from here
    //https://www.youtube.com/watch?v=lY9zSr6cxko
    // ---------------------------- start here -----------------------------------------------
    public void selectanduploadresume() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),86);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 86 && resultCode == RESULT_OK && data != null && data.getData()!= null){
            getNameAndData(data);
        }

    }

    //just took a reference from thr given link
    ////https://stackoverflow.com/questions/39696906/select-pdf-file-from-phone-on-button-click-and-display-its-file-name-on-textview
    @SuppressLint("Recycle")
    private void getNameAndData(Intent data) {
        Context context = this;
        Cursor name = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            name = context.getContentResolver().query(data.getData(),null,null,null);
        }
        if (name != null){
            int namenumber = name.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int size = name.getColumnIndex(OpenableColumns.SIZE);
            name.moveToFirst();
            if (namenumber >= 0 && size >= 0){
                resume.setText(name.getString(namenumber));
            }
            else {
                errorMessageDisplay("size of the pdf is less than 0 or 0");
            }
        }
        else {
            errorMessageDisplay("error in pdf file");
        }
        this.datauri = data.getData();
    }

    private void updatepdffiletodatabase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is uploading");
        progressDialog.show();

        StorageReference reference = storageReference.child(first_Name() + " " + last_Name() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                assert uri != null;
                PutPdftoDatabse putPdftoDatabse = new PutPdftoDatabse(resume.getText().toString(), uri.toString());
                userstable.child(count).child("resume").setValue(putPdftoDatabse);
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
    // ---------------------------- ends here -----------------------------------------------

}
