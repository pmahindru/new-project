package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class notification extends AppCompatActivity {
    //job information for the notification
    SharedPreferences preferences2;
    JobPosting_notification data2;
    String jobID;

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userids = null;
    DatabaseReference jobdetails = null;
    DatabaseReference jobapplication = null;

    ListView listView;

    //current user information
    SharedPreferences preferences;
    StoredData data;
    String uID;
    Boolean isEmployer;
    Button homepage;

    //job information for the notification from the review applicant
    SharedPreferences preferences3;
    Notification_ReviewApplicant_To_Employee data3;
    String jobID_reviewapplcaint;


    //current user hired information for the notification from the review applicant
    SharedPreferences preferences4;
    Notification_ReviewApplicant_To_Employee data4;
    String currentuserID_reviewapplcaint;


    //current application key information for the notification from the jobapplcation
    SharedPreferences preferences5;
    Notification_ReviewApplicant_To_Employee data5;
    String application_jobapplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initializeDatabase();
        getuserid();
        onclick();
    }
    private void onclick() {
        homepage = findViewById(R.id.switch2home);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnDashbaord = new Intent(notification.this, DashboardEmployee.class);
                startActivity(returnDashbaord);
            }
        });
    }

    //this is for the userid
    private void getuserid(){
        preferences2 = getSharedPreferences("jobsPrefs", Context.MODE_PRIVATE);
        data2 = new JobPosting_notification(preferences2);
        jobID = data2.getStoredUserID2();

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        isEmployer = data.getUserType();

        ArrayList<String> userIDs = new ArrayList<>();
        ArrayList<String> employeruserIDs = new ArrayList<>();

        //geting all the users id from here
        userids.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for (DataSnapshot snapshot: snapshot1.getChildren()){
                    if (snapshot.exists()){
                        String key = snapshot.getKey();
                        if (snapshot1.child(key).child("employee").getValue().equals(true) && snapshot1.child(key).child("employer").getValue().equals(false)){
                            userIDs.add(snapshot.getKey());
                        }
                        else{
                            employeruserIDs.add(snapshot.getKey());
                        }

                    }
                }
                if (isEmployer){
                    employernotification();
                }
                else{
                    onclickbuttonjobnotification(jobID,userIDs,isEmployer);
                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //notification for the employer
    private void employernotification() {
        listView = findViewById(R.id.list_notification);
        preferences5 = getSharedPreferences("jobsPrefs_fromreviewapplicants", Context.MODE_PRIVATE);
        data5 = new Notification_ReviewApplicant_To_Employee(preferences5);
        application_jobapplication = data5.getStoredUserID4();

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        System.out.println(application_jobapplication+"----------------------------------------------------------------------------");

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();

        if (application_jobapplication != null){
            jobapplication.child(application_jobapplication).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        name.add((String) snapshot.child("firstName").getValue() + snapshot.child("lastName").getValue());
                        location.add(getTheFullAddressOfTheUser((double) snapshot.child("location").child("latitude").getValue(), (double) snapshot.child("location").child("longitude").getValue()));
                        String currentuserid = (String) snapshot.child("currentUserID").getValue();
                        String jobidforthatspecificapplication =(String)  snapshot.child("jobId").getValue();
                        Notification_from_jobapplication notification_from_jobapplication = new  Notification_from_jobapplication(getApplicationContext(), name,location,currentuserid,jobidforthatspecificapplication);

                        jobdetails.child(jobidforthatspecificapplication).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String employerid = (String) snapshot.child("employerID").getValue();
                                    userids.child(employerid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                if (employerid.equals(uID)){
                                                    listView.setAdapter(notification_from_jobapplication);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            messageshow4();
        }
    }

    //notification for the employee
    //this is for the notification when there is new job posting
    private void onclickbuttonjobnotification(String currrent_jobID, ArrayList<String> all_userIds, Boolean isEmployer) {
        listView = findViewById(R.id.list_notification);

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();
        System.out.println(jobID + "---------------------asdasdasds-----------------------------------" );
        if (!currrent_jobID.equals("")) {
            jobdetails.child(currrent_jobID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        name.add((String) snapshot.child("jobTitle").getValue());
                        location.add(getTheFullAddressOfTheUser((double) snapshot.child("jobLocationCoordinates").child("latitude").getValue(), (double) snapshot.child("jobLocationCoordinates").child("longitude").getValue()));
                        Notification_JobPostingClass notification_jobPostingClass = new Notification_JobPostingClass(getApplicationContext(), name, location, currrent_jobID);

                        //show notification to all the users
                        userids.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                for (int i = 0; i < all_userIds.size(); i++) {
                                    if (snapshot1.child(all_userIds.get(i)).exists()){
                                        if (!isEmployer){
                                            listView.setAdapter(notification_jobPostingClass);
                                        }
                                    }
                                }
                                showHiredNotificationFromReviewApplicants();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            showHiredNotificationFromReviewApplicants();
            messageshow();
            System.out.println("done showing job positing notification to all the user and there no new job posting");
        }
    }

    //this is for the notification when the person is hired
    private void showHiredNotificationFromReviewApplicants() {
        //joid from the review applicants
        preferences3 = getSharedPreferences("jobsPrefs_fromreviewapplicants", Context.MODE_PRIVATE);
        data3 = new Notification_ReviewApplicant_To_Employee(preferences3);
        jobID_reviewapplcaint = data3.getStoredUserID3();

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        isEmployer = data.getUserType();


        //current user hired information for the notification from the review applicant
        preferences4 = getSharedPreferences("jobsPrefs_fromreviewapplicants", Context.MODE_PRIVATE);;
        data4 = new Notification_ReviewApplicant_To_Employee(preferences4);
        currentuserID_reviewapplcaint = data4.getStoredUserID2();

        ArrayList<String> name2 = new ArrayList<>();
        ArrayList<String> location2 = new ArrayList<>();
        System.out.println(jobID + "-------------------------------112121-------------------------" );
        if (!jobID_reviewapplcaint.equals("")) {
            jobdetails.child(jobID_reviewapplcaint).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        name2.add((String) snapshot.child("jobTitle").getValue());
                        location2.add(getTheFullAddressOfTheUser((double) snapshot.child("jobLocationCoordinates").child("latitude").getValue(), (double) snapshot.child("jobLocationCoordinates").child("longitude").getValue()));
                        Notification_fromReviewApplicant notification_jobPostingClass2 = new Notification_fromReviewApplicant(getApplicationContext(), name2, location2);

                        if (!isEmployer){
                            System.out.println(jobID + "--------------------------------------------------------" );
                            System.out.println(currentuserID_reviewapplcaint);
                            if (currentuserID_reviewapplcaint.equals(uID)){
                                messageshow3();
                                listView.setAdapter(notification_jobPostingClass2);
                                data3.clearStoredData3();
                            }
                            else {
                                messageshow2();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void messageshow() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("There is no new notification related to Job Posting");
        alert_answer.setPositiveButton("ok", null);
        alert_answer.create();
        alert_answer.show();
    }

    private void messageshow2() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("There is no new notification related to Hired");
        alert_answer.setPositiveButton("ok", null);
        alert_answer.create();
        alert_answer.show();
    }

    private void messageshow3() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("Yay you hired for this job go and check you will received the payment and this job is now automatically in jobHistory");
        alert_answer.setPositiveButton("ok", null);
        alert_answer.create();
        alert_answer.show();
    }

    private void messageshow4() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("There is no application is submitted for this job");
        alert_answer.setPositiveButton("ok", null);
        alert_answer.create();
        alert_answer.show();
    }

    @SuppressLint("SetTextI18n")
    private String getTheFullAddressOfTheUser(double getLatitude, double getLongitude){
        String full_address = null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> get_the_address = geocoder.getFromLocation(getLatitude,getLongitude,1);
            if (get_the_address != null && get_the_address.size() > 0){

                //here we getting the first address
                Address address = get_the_address.get(0);

                //but here we are getting that specific line of the address
                full_address = address.getAddressLine(0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return full_address;
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        jobdetails = database.getReference("JobInformation");
        jobapplication = database.getReference("application");
        userids = database.getReference("users");
    }
}