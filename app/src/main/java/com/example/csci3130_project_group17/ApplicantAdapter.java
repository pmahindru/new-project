package com.example.csci3130_project_group17;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

//Adapted from the tutorial at: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

public class ApplicantAdapter extends FirebaseRecyclerAdapter<Applicant, ApplicantAdapter.ApplicantsViewHolder>{

    public ApplicantAdapter(@NonNull FirebaseRecyclerOptions<Applicant> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  ApplicantsViewHolder holder, int position, @NonNull  Applicant model) {

        holder.firstName.setText(model.getFirstName());

        holder.lastName.setText(model.getLastName());
    }


    @NonNull

    @Override
    public ApplicantAdapter.ApplicantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicant_card, parent, false);

        return new ApplicantAdapter.ApplicantsViewHolder(view);
    }

    class ApplicantsViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName;

        public ApplicantsViewHolder(@NonNull View itemView){
            super(itemView);

            firstName = itemView.findViewById(R.id.applicantFirstName);
            lastName = itemView.findViewById(R.id.applicantLastName);
        }
    }
}
