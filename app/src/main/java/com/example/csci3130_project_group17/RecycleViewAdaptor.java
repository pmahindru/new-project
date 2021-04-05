package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.ViewHolder> {

    public ArrayList<HashMap<String,String>> jobs;
    public ArrayList<String> test;

    public RecycleViewAdaptor(ArrayList<HashMap<String,String>> jobPosts) {
        this.jobs = jobPosts;
    }

    public RecycleViewAdaptor() {
        this.test = new ArrayList<>();
        test.add("Please set your current location to see jobs near you");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(this.jobs!=null) {
            holder.jobTitle.setText(jobs.get(position).get("jobTitle"));
            holder.location.setText(jobs.get(position).get("jobLocation"));
            holder.payRate.setText(jobs.get(position).get("jobPayRate"));
            holder.applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // redirect to job application page
                    Intent sendIntent = new Intent(v.getContext(), JobDetail.class);
                    sendIntent.putExtra("jobPost", jobs.get(position));
                    sendIntent.putExtra("jobEmployerID", jobs.get(position).get("jobEmployerID"));
                    
                    v.getContext().startActivity(sendIntent);

                }
            });
        }
        else {
            holder.jobTitle.setText(test.get(position));
            holder.applyButton.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        int count;
        if(jobs!=null)
        {
            count = jobs.size();
        }
        else {
            count = test.size();
        }
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView jobTitle;
        TextView location;
        TextView payRate;
        Button applyButton;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            location = itemView.findViewById(R.id.locationText);
            payRate = itemView.findViewById(R.id.payId);
            applyButton = itemView.findViewById(R.id.jobPostingApplybutton);
            context = itemView.getContext();
         }
    }
}
