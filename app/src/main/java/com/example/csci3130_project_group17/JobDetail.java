package com.example.csci3130_project_group17;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//name the job id in intent extra "JobId" when switch to this activity

public class JobDetail extends AppCompatActivity {

    //private ImageView imageEmployee, imageEmployer;
    private TextView title, bDes, /*pDate, time, */location, payment/*, state, employeeN, employerN*/;
    private DatabaseReference jobInfo;
    private JobInfo job;
    //private UserInfo employee, employer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdescribtion);

        Intent intent = getIntent();
        String jobId = intent.getStringExtra("jobId");

        //imageEmployee = findViewById(R.id.jd_employee);
        //imageEmployer = findViewById(R.id.jd_Employer);
        //employeeN = findViewById(R.id.jd_employee_name);
        //employerN = findViewById(R.id.jd_employer_name);
        title = findViewById(R.id.jd_title);
        bDes = findViewById(R.id.jd_brif_des);
        //pDate = findViewById(R.id.jd_posted_date);
        //time = findViewById(R.id.jd_occur_time);
        location = findViewById(R.id.jd_location);
        payment = findViewById(R.id.jd_payrate);
        //state = findViewById(R.id.jd_state);

        jobInfo = FirebaseDatabase.getInstance().getReference().child("JobInformation").child(jobId);

        readJobInfo();
        //readUserInfo();
        fillInfo();
    }

    public void readJobInfo(){
        jobInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                job = snapshot.getValue(JobInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*public void readUserInfo(){
        DatabaseReference employeeInfo = FirebaseDatabase.getInstance().getReference("users")
                .child(job.getEmployeeID());

        employeeInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employee = snapshot.getValue(UserInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference employerInfo = FirebaseDatabase.getInstance().getReference("users")
                .child(job.getEmployerID());

        employerInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employer = snapshot.getValue(UserInfo.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public void fillInfo(){
        if(job != null){
            title.setText(job.getJobTitle());
            bDes.setText(job.getJobDescription());
            //pDate.setText(job.get());
            //time.setText(job.get);
            location.setText(job.getJobLocation());
            payment.setText(job.getJobPayRate());
            //state.setText(job.getState());
            //employerN.setText(employer.getFirstName()+" "+employer.getLastName());
            //employeeN.setText(employee.getFirstName()+" "+employee.getLastName());
        }
    }


}
