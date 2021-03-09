package com.example.csci3130_project_group17;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {
    private Button SendButton, ButtonHome;
    private View input;
    DatabaseReference ChatInformation;

    ChatMessage message;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SendButton = findViewById(R.id.Send);
        input = findViewById(R.id.textInput);
        ButtonHome = findViewById(R.id.Home);
        message = new ChatMessage();

        initializedatabase();
    }

    //inital database
    public void initializedatabase(){
        ChatInformation = FirebaseDatabase.getInstance().getReference();
    }

    protected void switchToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Send:
                EditText input = (EditText)findViewById(R.id.textInput);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
                displayChatMessages();

            case R.id.Home:
                switchToMain();
                break;

        }

    }


    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.chatcontent);



        FirebaseListAdapter<ChatMessage>firebaseListAdapter = new FirebaseListAdapter<ChatMessage>(Chat.this,ChatMessage.class,
                android.R.layout.simple_list_item_1,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
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

        listOfMessages.setAdapter(firebaseListAdapter);
    }


}