package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
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

public class ActiveJobs extends AppCompatActivity{

    Context appContext;
    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobapplication = null;
    DatabaseReference jobdetails = null;

    //user information
    SharedPreferences preferences;
    StoredData data;
    String uID;
    Boolean isEmployer;

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
        isEmployer = data.getUserType();
        appContext = getApplicationContext();
        Onclick();

        //initiating the Firebase
        initializeDatabase();
        if (isEmployer){
            employerJobs();
        } else {
            elementsfromdatabase();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    //on click for the homepage
    private void Onclick() {
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

    private void employerJobs(){
        listView = findViewById(R.id.list);
        jobdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String employerID = snapshot1.child("employerID").getValue().toString();
                    String state = snapshot1.child("state").getValue().toString();
                    if ((state.equals("open")) && (employerID.equals(uID))) {
                        String jobID = snapshot1.getKey();
                        jobids.add(jobID);
                        String jobTitle = snapshot1.child("jobTitle").getValue().toString();
                        jobtitle.add(jobTitle);
                        jobpayrate.add(snapshot1.child("jobPayRate").getValue().toString());
                        double latLocation = (double) snapshot1.child("jobLocationCoordinates").child("latitude").getValue();
                        double longLocation = (double) snapshot1.child("jobLocationCoordinates").child("longitude").getValue();
                        getTheFullAddressOfTheUser(latLocation,longLocation);
                        joblocation.add(Full_address);

                    }


                }

                //  https://abhiandroid.com/ui/arrayadapter-tutorial-example.html
                arrjobtitle = jobtitle.toArray(new String[jobids.size()]);
                arrjobpayrate = jobpayrate.toArray(new String[jobids.size()]);
                arrjolocation = joblocation.toArray(new String[jobids.size()]);

                //adapter is taken from the give link
                //just had a reference
                //https://abhiandroid.com/ui/listview
                Activejobs2 activejobs2 = new Activejobs2(appContext,arrjobtitle,arrjobpayrate,arrjolocation, jobids ,isEmployer);
                listView.setAdapter(activejobs2);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void elementsfromdatabase() {
        listView = findViewById(R.id.list);
        jobapplication.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    if (snapshot1.child("currentUserID") !=null) {
                        if ((snapshot1.child("currentUserID").getValue().equals(uID))) {
                            jobids.add((String) snapshot1.child("jobId").getValue());
                        }
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

                //  https://abhiandroid.com/ui/arrayadapter-tutorial-example.html
                arrjobtitle = jobtitle.toArray(new String[jobids.size()]);
                arrjobpayrate = jobpayrate.toArray(new String[jobids.size()]);
                arrjolocation = joblocation.toArray(new String[jobids.size()]);

                //adapter is taken from the give link
                //just had a reference
                //https://abhiandroid.com/ui/listview
                Activejobs2 activejobs2 = new Activejobs2(ActiveJobs.this, arrjobtitle,arrjobpayrate,arrjolocation, jobids,isEmployer);
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
