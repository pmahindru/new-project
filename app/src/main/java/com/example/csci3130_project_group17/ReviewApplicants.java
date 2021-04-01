package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ReviewApplicants extends AppCompatActivity {
    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobapplication = null;

    TextView name;
    TextView email;
    TextView number;
    TextView loca;
    TextView resume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewapplicants);
        name = findViewById(R.id.Firstname_employee);
        email = findViewById(R.id.Email_employee);
        number = findViewById(R.id.Phone_employee);
        loca = findViewById(R.id.Location_employee);
        resume = findViewById(R.id.Resume_employee);


        //button function take place
        OnClick();
        initializeDatabase();
        userifno();
    }

    private void OnClick() {
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
        Intent dashboardEmployee = new Intent(this, DashboardEmployer.class);
        startActivity(dashboardEmployee);
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
    }
}
