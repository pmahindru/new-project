package com.example.csci3130_project_group17;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ActiveJobs extends AppCompatActivity {

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobapplication = null;
    DatabaseReference jobdetails = null;

    //user information
    SharedPreferences preferences;
    StoredData data;
    String uID;

    String Full_address;

    //current user information
    ArrayList<String> jobids = new ArrayList<>();


    //store all information of the job which person already applied to
    ArrayList<String> jobtitle = new ArrayList<>();
    ArrayList<String> jobpayrate = new ArrayList<>();
    ArrayList<String> joblocation = new ArrayList<>();
    String[] arrjobtitle;
    String[] arrjobpayrate;
    String[] arrjolocation;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activejobs);

        Intent intent = getIntent();

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();

        Onclick();

        //initiating the Firebase
        initializeDatabase();

        elementsfromdatabase();

    }

    private void Onclick() {
//        TextView viewchat = findViewById(R.id.viewchat);
//        viewchat.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                swtich2chat();
//            }
//        });

//        // take value of button
//        Button square_button2 = (Button)findViewById(R.id.switch2home);
//        square_button2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                swtich2home();
//            }
//        });
    }

//    private void swtich2chat() {
//        Intent chat = new Intent(this, Chat.class);
//        startActivity(chat);
//    }
//
//    private void swtich2home() {
//        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
//        startActivity(dashboardEmployee);
//    }

    private void elementsfromdatabase() {
        listView = findViewById(R.id.list);
        jobapplication.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    if (snapshot1.child("currentUserID").getValue().equals(uID)){
                        jobids.add((String) snapshot1.child("jobId").getValue());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        jobdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = 0; i < jobids.size(); i++){
                    if (snapshot.child(jobids.get(i)).exists()){
                        jobtitle.add(String.valueOf(snapshot.child(jobids.get(i)).child("jobTitle").getValue()));
                        jobpayrate.add(String.valueOf(snapshot.child(jobids.get(i)).child("jobPayRate").getValue()));
                        getTheFullAddressOfTheUser((double) snapshot.child(jobids.get(i)).child("jobLocationCoordinates").child("latitude").getValue(), (double) snapshot.child(jobids.get(i)).child("jobLocationCoordinates").child("longitude").getValue());
                        joblocation.add(Full_address);
                    }
                }

                System.out.println(jobtitle + " --- "+  jobpayrate + " ------ " + joblocation );

                //  https://abhiandroid.com/ui/arrayadapter-tutorial-example.html
                arrjobtitle = jobtitle.toArray(new String[jobids.size()]);
                arrjobpayrate = jobpayrate.toArray(new String[jobids.size()]);
                arrjolocation = joblocation.toArray(new String[jobids.size()]);

                //adapter is taken from the give link
                //just had a reference
                //https://abhiandroid.com/ui/listview
                Activejobs2 activejobs2 = new Activejobs2(getApplicationContext(),arrjobtitle,arrjobpayrate,arrjolocation);
                listView.setAdapter(activejobs2);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    //reference from the given link
    //http://wintechtutorials.com/blog/android-get-address-latitude-longitude-google-map-tutorial/
    //so basically from this user can see the address but in the database we only adding the Latitude and Longitude.
    //taken from the job application page which was made by Pranav Mahindru
    @SuppressLint("SetTextI18n")
    private void getTheFullAddressOfTheUser(double getLatitude, double getLongitude){
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

        Full_address = full_address;
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        jobapplication = database.getReference("application");
        jobdetails = database.getReference("JobInformation");
    }

}
