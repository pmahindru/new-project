package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
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

    ListView listView;

    //current user information
    SharedPreferences preferences;
    StoredData data;
    String uID;
    Boolean isEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initializeDatabase();
        getuserid();
    }

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
                    }
                }
                onclickbuttonjobnotification(jobID,userIDs,isEmployer,uID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onclickbuttonjobnotification(String currrent_jobID, ArrayList<String> all_userIds, Boolean isEmployer, String uID) {
        listView = findViewById(R.id.list_notification);

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> location = new ArrayList<>();

        if (!currrent_jobID.equals("")) {
            jobdetails.child(currrent_jobID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        name.add((String) snapshot.child("jobTitle").getValue());
                        location.add(getTheFullAddressOfTheUser((double) snapshot.child("jobLocationCoordinates").child("latitude").getValue(), (double) snapshot.child("jobLocationCoordinates").child("longitude").getValue()));
                        Notification_JobPostingClass notification_jobPostingClass = new Notification_JobPostingClass(getApplicationContext(), name, location, uID);

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

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

//                    data2.clearStoredData2();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            messageshow();
            System.out.println("done showing job positing notification to all the user and there no new job posting");
        }
    }

    private void messageshow() {
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage("There is no new job posting by the employer");
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
        userids = database.getReference("users");
    }
}