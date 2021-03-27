package com.example.csci3130_project_group17;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Chat extends AppCompatActivity {
    private Button SendButton, ButtonHome;
    private View input;
    private FirebaseListAdapter<ChatMessage> adapter;

    ChatMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        SendButton = findViewById(R.id.Send);
        input = findViewById(R.id.textInput);
        ButtonHome = findViewById(R.id.Home);
        message = new ChatMessage();
        ButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMain();

            }
        });

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.textInput);
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("chats")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),"tim"
                                )
                        );

                // Clear the input
                input.setText("");
                displayChatMessages();
                Toast.makeText(getBaseContext(), "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }



    protected void switchToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




    


    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.chatcontent);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chats");

        /*
        FirebaseListOptions<ChatMessage> options =
                new FirebaseListOptions.Builder<ChatMessage>()
                        .setQuery(query,ChatMessage.class)
                        .setLayout(R.layout.chatmessage)
                        .build();

        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        */
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                ChatMessage newPost = dataSnapshot.getValue(ChatMessage.class);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listOfMessages.setAdapter(adapter);
    }


}