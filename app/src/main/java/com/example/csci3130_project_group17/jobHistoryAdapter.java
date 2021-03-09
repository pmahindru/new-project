package com.example.csci3130_project_group17;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class jobHistoryAdapter extends RecyclerView.Adapter{
    private List<Job> jobsList;

    //constructor
    public jobHistoryAdapter(List<Job> jobsList) {
        this.jobsList = jobsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_history_item,parent,false);
        HistoryViewHolder historyViewHolder = new HistoryViewHolder(view);
        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryViewHolder historyViewHolder = (HistoryViewHolder)holder;
        Job job = jobsList.get(position);
        historyViewHolder.title.setText(job.getJobTitle());
        historyViewHolder.name.setText("Employer: " +job.getEmployerName());
        historyViewHolder.rate.setText("$"+job.getJobPayRate()+"/hr");
        historyViewHolder.state.setText("State: " + job.getState());
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView title,name, state, rate;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jobTitleLayout);
            name = itemView.findViewById(R.id.jobNameLayout);
            state = itemView.findViewById(R.id.jobStateLayout);
            rate = itemView.findViewById(R.id.jobTotalLayout);
        }
    }
}
