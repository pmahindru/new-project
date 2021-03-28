package com.example.csci3130_project_group17;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static androidx.core.content.ContextCompat.startActivity;

//Adapted from the tutorial at: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

public class ApplicantAdapter extends FirebaseRecyclerAdapter<Applicant, ApplicantAdapter.ApplicantsViewHolder>{


    public ApplicantAdapter(@NonNull FirebaseRecyclerOptions<Applicant> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(ApplicantsViewHolder holder, int position, Applicant model) {

        holder.firstName.setText(model.getFirstName());

        holder.lastName.setText(model.getLastName());

        setOnClickListeners(holder, model);
    }

    private void setOnClickListeners(ApplicantsViewHolder holder, Applicant model) {

        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(v.getContext(), Chat.class);
                chatIntent.putExtra("userId", model.getCurrentUserID());
                startActivity(v.getContext(), chatIntent, null);
            }
        });

        holder.reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewIntent = new Intent(v.getContext(), ReviewApplicants.class);
                reviewIntent.putExtra("userId", model.getCurrentUserID());
                reviewIntent.putExtra("jobId", model.getjobId());
                startActivity(v.getContext(), reviewIntent, null);

            }
        });

    }


    @NonNull
    @Override
    public ApplicantAdapter.ApplicantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_card, parent, false);

        return new ApplicantAdapter.ApplicantsViewHolder(view);
    }

    class ApplicantsViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName;
        Button chatButton, reviewButton;

        public ApplicantsViewHolder(@NonNull View itemView){
            super(itemView);

            firstName = itemView.findViewById(R.id.applicantFirstName);
            lastName = itemView.findViewById(R.id.applicantLastName);
            chatButton = itemView.findViewById(R.id.chatButton);
            reviewButton = itemView.findViewById(R.id.reviewButton);

        }
    }
}
