package com.example.csci3130_project_group17;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        for (int i = 0; i < 100; i++) {
            this.test.add("Test" + i);
        }
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

        if(this.jobs!=null)
        {
            holder.jobTitle.setText(jobs.get(position).get("jobTitle"));

            holder.location.setText(jobs.get(position).get("jobLocation"));

            holder.payRate.setText(jobs.get(position).get("jobPayRate"));

            holder.applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // redirect to job application page
                    System.out.println("clicked the apply button");
                }
            });

        }
        else {
            holder.jobTitle.setText(test.get(position));
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView jobTitle;
        TextView location;
        TextView payRate;
        Button applyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            location = itemView.findViewById(R.id.locationText);
            payRate = itemView.findViewById(R.id.payId);
            applyButton = itemView.findViewById(R.id.jobPostingApplybutton);

         }
    }
}
