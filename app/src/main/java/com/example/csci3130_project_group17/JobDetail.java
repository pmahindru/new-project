package com.example.csci3130_project_group17;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class JobDetail extends AppCompatActivity {

    //job post related information
    HashMap<String, String> jobPost;

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference jobdetails = null;

    TextView type;
    TextView location;
    TextView payrate;
    TextView description;
    ImageView imageView;
    Button employer_profile;
    Button applybutton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdescribtion);

        Intent intent = getIntent();
        jobPost = (HashMap<String, String>) intent.getSerializableExtra("jobPost");

        initializeDatabase();
        job_deatial(jobPost);
        onclickbutton(jobPost);
    }

    private void onclickbutton(HashMap<String, String> jobPost) {
        employer_profile = findViewById(R.id.employerprofilebutton_jobdescription);
        employer_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Megh Gandhi is not complete with the profile screen so this button is not working then. Sorry
            }
        });

        applybutton = findViewById(R.id.apply_jobdescription);
        applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent swtichtoapplicationpage = new Intent(JobDetail.this, JobApplication.class);
                swtichtoapplicationpage.putExtra("JobKey", jobPost.get("jobPostId"));
                startActivity(swtichtoapplicationpage);
            }
        });
    }

    private void job_deatial(HashMap<String, String> jobPost) {
        type = findViewById(R.id.type_jobdescription);
        location = findViewById(R.id.Location_jobdescription);
        payrate = findViewById(R.id.payrate_jobdescription);
        description = findViewById(R.id.description_jobdescription);
        imageView = findViewById(R.id.image_jobdescription);

        jobdetails.child(jobPost.get("jobPostId")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    //this thing is taken from the gve link which is bold the specif text
                    //https://stackoverflow.com/questions/14371092/how-to-make-a-specific-text-on-textview-bold
                    String jobtitle = "<b> Job Title: </b>" + snapshot.child("jobTitle").getValue();
                    String loca = "<b> Location: </b>" + getTheFullAddressOfTheUser((double) snapshot.child("jobLocationCoordinates").child("latitude").getValue(), (double) snapshot.child("jobLocationCoordinates").child("longitude").getValue());
                    String Payrate = "<b> PayRate: </b>" + snapshot.child("jobPayRate").getValue();
                    String jobdescription = "<b> Description: </b> <br>" + snapshot.child("jobDescription").getValue();
                    String image = (String) snapshot.child("imageurl").getValue();
                    System.out.println(snapshot.child("imageurl").getValue()+"--------------------------------------------------------------------------------------");

                    type.setText(Html.fromHtml(jobtitle));
                    location.setText(Html.fromHtml(loca));
                    payrate.setText(Html.fromHtml(Payrate));
                    description.setText(Html.fromHtml(jobdescription));
                    Glide.with(JobDetail.this).load(image).into(imageView);
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
    }


}
