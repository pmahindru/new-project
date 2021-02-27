package com.example.csci3130_project_group17;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout relativeLayout;
        TextView jobTitle;
        TextView location;
        TextView payRate;
        Button applyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            location = itemView.findViewById(R.id.locationText);
            payRate = itemView.findViewById(R.id.payId);
            applyButton = itemView.findViewById(R.id.locateButton);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
