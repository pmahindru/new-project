package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//this code is taken from the lab and modify by Pranav and Tongqi
public class Chat extends AppCompatActivity {

    //chat things
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;

    //user information
    String uID;
    StoredData data;
    SharedPreferences preferences;

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userinfo = null;
    DatabaseReference Jobinfo = null;
    DatabaseReference currentmessage = null;
    DatabaseReference currentmessage2 = null;

    //getting name and the jobinfo
    Boolean check;

    String jobid;

    //all the namse of the emplyoer and employee in the arrylist
    private final ArrayList<String> chatwithemployee = new ArrayList<>();
    private final ArrayList<String> chatwithemployer = new ArrayList<>();

    String employerid;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //get userID of logged in user
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        check = data.getUserType();

        //method calling
        initializeDatabase();
        getTheNameOfEmployeeAndEmployer();
        Onclick();
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
        if (check) {
            returnDashbaord = new Intent(this, DashboardEmployer.class);
        }else{
            returnDashbaord = new Intent(this, DashboardEmployee.class);
        }
        startActivity(returnDashbaord);
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userinfo = database.getReference("users");
        Jobinfo = database.getReference("JobInformation");
        currentmessage = database.getReference("Messages");
        currentmessage2 = database.getReference("Messages");
    }

    public void getTheNameOfEmployeeAndEmployer(){
        //if it is the employer then get the name of the employer
        if (check){
            Intent intent = getIntent();
            userId = intent.getStringExtra("userId");

            //checking for the employee full name
            userinfo.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        chatwithemployee.add((String) snapshot.child("firstName").getValue());
                        chatwithemployee.add((String) snapshot.child("lastName").getValue());
                        SaveMessageInTheDatabase(chatwithemployee);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        //if it is employee then get the name of the employee
        else {
            Intent intent = getIntent();
            jobid = intent.getStringExtra("jobId");

            //first getting the employer ID
            Jobinfo.child(jobid).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        employerid = (String) snapshot.child("employerID").getValue();
                        //checking for the null
                        assert employerid != null;
                        //checking for the employer full name
                        userinfo.child(employerid).addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    chatwithemployer.add((String) snapshot.child("firstName").getValue());
                                    chatwithemployer.add((String) snapshot.child("lastName").getValue());
                                    SaveMessageInTheDatabase(chatwithemployer);
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

    //save the chat in the database
    public void SaveMessageInTheDatabase(ArrayList<String>chatwith_) {

        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);


        String Chatwith_Full_Name_User = chatwith_.get(0) + "-" + chatwith_.get(1);

        //checking for the current full name
        checkUserIsEmployeeOrEmployer(Chatwith_Full_Name_User);
    }

    //check if the current user exist and check for the employee and employer too
    private void checkUserIsEmployeeOrEmployer(String chatwith_Full_Name_User) {
        userinfo.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String curr_username = (String) snapshot.child("firstName").getValue() + "-" + (String) snapshot.child("lastName").getValue();
                    if (check) {
                        employerChatWithEmployee(curr_username, chatwith_Full_Name_User);
                    }
                    else {
                        employeeChatWithEmployer(curr_username, chatwith_Full_Name_User);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //employee Chat With Employer
    private void employeeChatWithEmployer(String curr_username, String chatwith_Full_Name_User) {
        currentmessage2 = currentmessage.child(curr_username + "_" + chatwith_Full_Name_User);
        currentmessage = currentmessage.child(chatwith_Full_Name_User + "_" + curr_username);
        System.out.println(currentmessage + " --------------------------------------- " + currentmessage2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", curr_username);
                    System.out.println(map);
                    currentmessage.push().setValue(map);
                    currentmessage2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });
        currentmessage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(curr_username)) {
                    addMessageBox(message, 1);
                } else {
                    addMessageBox(message, 2);
                }
            }
            /**
             * This method is triggered when the data at a child location has changed.
             *
             * @param snapshot          An immutable snapshot of the data at the new data at the child location
             * @param previousChildName The key name of sibling location ordered before the child. This will
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            /**
             * This method is triggered when a child is removed from the location to which this listener was
             * added.
             *
             * @param snapshot An immutable snapshot of the data at the child that was removed.
             */
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            /**
             * This method is triggered when a child location's priority changes. See {@link
             * DatabaseReference#setPriority(Object)} and <a
             * href="https://firebase.google.com/docs/database/android/retrieve-data#data_order"
             * target="_blank">Ordered Data</a> for more information on priorities and ordering data.
             *
             * @param snapshot          An immutable snapshot of the data at the location that moved.
             * @param previousChildName The key name of the sibling location ordered before the child
             */
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase rules. For more information on securing
             * your data, see: <a href="https://firebase.google.com/docs/database/security/quickstart"
             * target="_blank"> Security Quickstart</a>
             *
             * @param error A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //employer Chat With Employee
    private void employerChatWithEmployee(String curr_username, String chatwith_Full_Name_User) {
        currentmessage2 = currentmessage.child(curr_username + "_" + chatwith_Full_Name_User);
        currentmessage = currentmessage.child(chatwith_Full_Name_User + "_" + curr_username);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText =  messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", curr_username);
                    currentmessage.push().setValue(map);
                    currentmessage2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });
        currentmessage.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(curr_username)) {
                    addMessageBox(message, 1);
                } else {
                    addMessageBox(message, 2);
                }
            }
            /**
             * This method is triggered when the data at a child location has changed.
             *
             * @param snapshot          An immutable snapshot of the data at the new data at the child location
             * @param previousChildName The key name of sibling location ordered before the child. This will
             */
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            /**
             * This method is triggered when a child is removed from the location to which this listener was
             * added.
             *
             * @param snapshot An immutable snapshot of the data at the child that was removed.
             */
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            /**
             * This method is triggered when a child location's priority changes. See {@link
             * DatabaseReference#setPriority(Object)} and <a
             * href="https://firebase.google.com/docs/database/android/retrieve-data#data_order"
             * target="_blank">Ordered Data</a> for more information on priorities and ordering data.
             *
             * @param snapshot          An immutable snapshot of the data at the location that moved.
             * @param previousChildName The key name of the sibling location ordered before the child
             */
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase rules. For more information on securing
             * your data, see: <a href="https://firebase.google.com/docs/database/security/quickstart"
             * target="_blank"> Security Quickstart</a>
             *
             * @param error A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //show chat in the screen
    @SuppressLint("ResourceAsColor")
    public void addMessageBox(String message, int type){
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        scrollView = findViewById(R.id.scrollView);
        TextView textView = new TextView(Chat.this);
        // color of the text is taken from the given link https://stackoverflow.com/questions/4499208/android-setting-text-view-color-from-java-code
        textView.setTextColor(this.getResources().getColor(R.color.black));
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        lp2.setMargins(0,15,0,15);
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}



