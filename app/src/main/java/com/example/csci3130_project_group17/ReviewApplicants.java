package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ReviewApplicants extends AppCompatActivity {
    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobapplication = null;
    DatabaseReference jobdetails = null;

    TextView name;
    TextView email;
    TextView number;
    TextView loca;
    TextView resume;

    //geting job id and user id of that job and closed the status and then notify that user and after employer need to pay
    Notification_ReviewApplicant_To_Employee appData_notification_revviewapplicant;
    SharedPreferences data_notification_revviewapplicant;
    String jobID_notification_revviewapplicant = null;
    String currentuserID_notification_revviewapplicant = null;
    String userid;

    //geting job id and user id of that job and closed the status and then notify that user and after employer need to pay
    JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton appData_forpaymentpage;
    SharedPreferences data__forpaymentpage;
    String jobID__forpaymentpage = null;
    String currentuserID_forpaymentpage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewapplicants);
        name = findViewById(R.id.Firstname_employee);
        email = findViewById(R.id.Email_employee);
        number = findViewById(R.id.Phone_employee);
        loca = findViewById(R.id.Location_employee);
        resume = findViewById(R.id.Resume_employee);

        data_notification_revviewapplicant = getSharedPreferences("jobsPrefs_fromreviewapplicants", Context.MODE_PRIVATE);
        appData_notification_revviewapplicant = new Notification_ReviewApplicant_To_Employee(data_notification_revviewapplicant);

        data__forpaymentpage = getSharedPreferences("bothidsPrefs",Context.MODE_PRIVATE);
        appData_forpaymentpage = new JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton(data__forpaymentpage);

        Intent data = getIntent();
        userid = data.getStringExtra("userId");
        //button function take place
        OnClick();
        initializeDatabase();
        userifno();
    }

    private void OnClick() {
        // take value of button
        Button square_button2 = (Button) findViewById(R.id.switch2home);
        square_button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                swtich2home();
            }
        });

        Button profile = (Button) findViewById(R.id.employeeProfileApplicants) ;
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToProfile(userid);
            }
        });


        Button hire = (Button) findViewById(R.id.Hire_review_application);
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data2 = getIntent();
                String jobid = data2.getStringExtra("jobId");
                Intent data = getIntent();
                String currentjobhired_userid = data.getStringExtra("userId");
                jobID__forpaymentpage = jobid;
                appData_forpaymentpage.storedjobID(jobid);
                currentuserID_forpaymentpage = userid;
                appData_forpaymentpage.storedjobID2(currentjobhired_userid);

                closedJobPosting();
                Intent intent = new Intent(ReviewApplicants.this,paymentpage.class);
                intent.putExtra("currentjobId_topay",jobid);
                intent.putExtra("currentuserId_topay",currentjobhired_userid);
                startActivity(intent);
            }
        });
    }
    private void swtich2home() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployer.class);
        startActivity(dashboardEmployee);
    }

    private void closedJobPosting() {
        Intent data2 = getIntent();
        String jobid = data2.getStringExtra("jobId");
        jobapplication.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (Objects.requireNonNull(snapshot.child("currentUserID").getValue()).equals(userid) && Objects.requireNonNull(snapshot.child("jobId").getValue()).equals(jobid)){
                        String current_jobid = (String) snapshot.child("jobId").getValue();
                        jobdetails.child(current_jobid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    jobdetails.child(current_jobid).child("employeeID").setValue(userid);
                                    jobdetails.child(current_jobid).child("state").setValue("closed");
                                    jobID_notification_revviewapplicant = snapshot.getKey();
                                    appData_notification_revviewapplicant.storedjobID3(jobID_notification_revviewapplicant);
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

    private void userifno() {
        Intent data = getIntent();
        String userid = data.getStringExtra("userId");
        Intent data2 = getIntent();
        String jobid = data2.getStringExtra("jobId");


        jobapplication.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (Objects.requireNonNull(snapshot.child("currentUserID").getValue()).equals(userid) && Objects.requireNonNull(snapshot.child("jobId").getValue()).equals(jobid)){
                        String fullanme = "<b> Full Name:  </b>" + snapshot.child("firstName").getValue() + " " + snapshot.child("lastName").getValue();
                        String email1 = "<b> Email: </b>" + snapshot.child("email").getValue();
                        String phonenumber = "<b> Phone number: </b>" + snapshot.child("phone_number").getValue();
                        String resume_name = "<b> Resume: </b>" + snapshot.child("resume").child("name").getValue();
                        String resume_url = (String) snapshot.child("resume").child("url").getValue();
                        getTheFullAddressOfTheUser((double) snapshot.child("location").child("latitude").getValue(), (double) snapshot.child("location").child("longitude").getValue());

                        currentuserID_notification_revviewapplicant = (String) snapshot.child("currentUserID").getValue();
                        appData_notification_revviewapplicant.storedjobID2(currentuserID_notification_revviewapplicant);

                        name.setText(Html.fromHtml(fullanme));
                        email.setText(Html.fromHtml(email1));
                        number.setText(Html.fromHtml(phonenumber));
                        resume.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resume_url));
                                startActivity(intent);
                            }
                        });
                        resume.setText(Html.fromHtml(resume_name));
                    }
                }
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
    public void getTheFullAddressOfTheUser(double getLatitude, double getLongitude){
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

        String location = "<b> Location: </b>" + full_address;
        loca.setText(Html.fromHtml(location));
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        jobapplication = database.getReference("application");
        jobdetails = database.getReference("JobInformation");
    }

    public void switchToProfile(String uID){
        Intent ProfileIntent = new Intent(this, Profile.class);
        ProfileIntent.putExtra("profileUserID", uID);
        startActivity(ProfileIntent);
    }
}
