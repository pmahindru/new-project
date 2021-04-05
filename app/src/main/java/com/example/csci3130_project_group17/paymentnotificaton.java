package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class paymentnotificaton extends AppCompatActivity {

    //user information
    String uID;
    SharedPreferences preferences;
    StoredData data;
    Boolean isEmployer;


    //current application key information for the notification from the jobapplcation
    SharedPreferences preferences2;
    JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton data2;
    String jobid_pays;
    String userid_pays;
    String status_pays;
    String date_pays;

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobapplication = null;
    DatabaseReference jobdetails = null;
    DatabaseReference userdetails = null;

    ListView paysboth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentemployee);
        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        isEmployer = data.getUserType();

        initializeDatabase();

        if(isEmployer){
            employernotification();
        }
        else {
            employeenotification();
        }

        onclick();
    }

    private void onclick() {
        // take value of button
        Button square_button2 = (Button)findViewById(R.id.switch2home);
        square_button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                swtich2home();
            }
        });
    }

    private void swtich2home() {
        Intent returnDashbaord;
        //switch to employer or employee dashboard depending on user type
        if (isEmployer) {
            returnDashbaord = new Intent(this, DashboardEmployer.class);
        }else{
            returnDashbaord = new Intent(this, DashboardEmployee.class);
        }
        startActivity(returnDashbaord);
    }

    private void employeenotification() {
        paysboth = findViewById(R.id.list_paysemployee);

        preferences2 = getSharedPreferences("bothidsPrefs", Context.MODE_PRIVATE);
        data2 = new JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton(preferences2);
        jobid_pays = data2.getStoredUserId();
        userid_pays = data2.getStoredUserId2();
        status_pays = data2.getStoredUserId3();
        date_pays = data2.getStoredUserId4();

        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        System.out.println(uID + "-------------------------------------------------------------------------------");
        System.out.println(jobid_pays+"---------------------------------------------------------------------------");
        System.out.println(userid_pays+"---------------------------------------------------------------------------");
        System.out.println(status_pays+"---------------------------------------------------------------------------");
        System.out.println(date_pays+"---------------------------------------------------------------------------");

        jobdetails.child(jobid_pays).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String pay = (String) snapshot.child("jobPayRate").getValue();
                    String emplyerid = (String) snapshot.child("employerID").getValue();
                    String emplyeeid = (String) snapshot.child("employeeID").getValue();

                    if (emplyeeid.equals(uID) && userid_pays.equals(uID)){
                        userdetails.child(emplyerid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String fullname = (String) snapshot.child("firstName").getValue() + snapshot.child("lastName").getValue();
                                    employeenotificationofpaymentstheyrecevied  employernotificationofpayments = new employeenotificationofpaymentstheyrecevied (getApplicationContext(), fullname, pay, status_pays, date_pays);
                                    paysboth.setAdapter(employernotificationofpayments);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void employernotification(){
        paysboth = findViewById(R.id.list_paysemployee);

        preferences2 = getSharedPreferences("bothidsPrefs", Context.MODE_PRIVATE);
        data2 = new JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton(preferences2);
        jobid_pays = data2.getStoredUserId();
        userid_pays = data2.getStoredUserId2();
        status_pays = data2.getStoredUserId3();
        date_pays = data2.getStoredUserId4();


        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        System.out.println(uID + "-------------------------------------------------------------------------------");
        System.out.println(jobid_pays+"---------------------------------------------------------------------------");
        System.out.println(userid_pays+"---------------------------------------------------------------------------");
        System.out.println(status_pays+"---------------------------------------------------------------------------");
        System.out.println(date_pays+"---------------------------------------------------------------------------");

        jobdetails.child(jobid_pays).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String pay = (String) snapshot.child("jobPayRate").getValue();
                    String emplyerid = (String) snapshot.child("employerID").getValue();

                    if (emplyerid.equals(uID)){
                        userdetails.child(userid_pays).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String fullname = (String) snapshot.child("firstName").getValue() + snapshot.child("lastName").getValue();
                                    employernotificationofpayments employernotificationofpayments = new employernotificationofpayments(getApplicationContext(), fullname, pay, status_pays, date_pays);
                                    paysboth.setAdapter(employernotificationofpayments);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        jobapplication = database.getReference("application");
        jobdetails = database.getReference("JobInformation");
        userdetails = database.getReference("users");
    }
}
