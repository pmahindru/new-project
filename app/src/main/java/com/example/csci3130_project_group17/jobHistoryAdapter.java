package com.example.csci3130_project_group17;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import java.util.Objects;


public class jobHistoryAdapter extends RecyclerView.Adapter{
    private List<Job> jobsList;
    private Context context;
    private String uID;
    DatabaseReference users;
    DatabaseReference jobInformation;
    DatabaseReference reviews;

    //constructor
    public jobHistoryAdapter(List<Job> jobsList, Context context, String uID) {
        this.jobsList = jobsList;
        this.context = context;
        this.uID = uID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        users= FirebaseDatabase.getInstance().getReference().child("users");
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
        reviews = FirebaseDatabase.getInstance().getReference().child("reviews");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_history_item,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryViewHolder historyViewHolder = (HistoryViewHolder)holder;
        Job job = jobsList.get(position);

        getEmployerName(job.getEmployerID(),historyViewHolder);
        //fill vieww holder with job data
        historyViewHolder.title.setText(job.getJobTitle());
        historyViewHolder.rate.setText("$"+job.getJobPayRate()+"/hr");
        historyViewHolder.state.setText("State: " + job.getState());
        changeVisibilityOfCloseBttn(historyViewHolder, job);
        changeVisibilityOfReviewBttn(historyViewHolder,job, uID);
        closeBttnOnclick(historyViewHolder, job);
        reviewBttnOnclick(historyViewHolder,job);
    }



    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public void getEmployerName(String id, HistoryViewHolder holder){
        Query query = users.orderByChild(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    //get name of user if employer has individual account and organization name if business account
                    if (Objects.requireNonNull(child.getKey()).equals(id)){
                        String fName = child.child("firstName").getValue(String.class);
                        String lName = child.child("lastName").getValue(String.class);
                        String orgName = child.child("orgName").getValue(String.class);
                        if (Objects.requireNonNull(orgName).isEmpty()){
                            holder.name.setText("Employer: "+fName + " " + lName);
                        }else{
                            holder.name.setText("Employer: "+orgName);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changeVisibilityOfCloseBttn(HistoryViewHolder historyViewHolder, Job job) {
        if (job.getState().equals("open")){
            historyViewHolder.closeBttn.setVisibility(View.VISIBLE);
        }
        else{
            historyViewHolder.closeBttn.setVisibility(View.GONE);
        }
    }

    private void changeVisibilityOfReviewBttn(HistoryViewHolder historyViewHolder, Job job, String uID) {
        if (job.getState().equals("open")){
            historyViewHolder.reviewBttn.setVisibility(View.GONE);
        }
        else{
            historyViewHolder.reviewBttn.setVisibility(View.VISIBLE);
        }

        reviews.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String reviewerID = Objects.requireNonNull(dataSnapshot.child("reviewerID").getValue()).toString();
                    String jobID = Objects.requireNonNull(dataSnapshot.child("jobID").getValue()).toString();
                    //if user has already submitted review for this job, hide review button
                    if (reviewerID.equals(uID) && jobID.equals(job.getId())){
                        historyViewHolder.reviewBttn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void closeBttnOnclick(HistoryViewHolder historyViewHolder, Job job) {
        historyViewHolder.closeBttn.setOnClickListener(view -> {
            jobInformation.child(job.getId()).child("state").setValue("closed");
            jobsList.remove(job);
            notifyDataSetChanged();
        });
    }

    private void reviewBttnOnclick(HistoryViewHolder historyViewHolder, Job job) {
        String jobID = job.getId();
        String employerID = job.getEmployerID();
        String employeeID = job.getEmployeeID();
        historyViewHolder.reviewBttn.setOnClickListener(v -> {
            Intent reviewFormIntent = new Intent(context,LeaveReview.class);
            reviewFormIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            reviewFormIntent.putExtra("jobId", jobID);
            reviewFormIntent.putExtra("employerId", employerID);
            reviewFormIntent.putExtra("employeeId", employeeID);
            context.startActivity(reviewFormIntent);
        });

    }


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView title,name, state, rate;
        Button closeBttn, reviewBttn;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jobTitleLayout);
            name = itemView.findViewById(R.id.jobNameLayout);
            state = itemView.findViewById(R.id.jobStateLayout);
            rate = itemView.findViewById(R.id.jobTotalLayout);
            closeBttn = itemView.findViewById(R.id.closeJobButton);
            reviewBttn = itemView.findViewById(R.id.reviewButtonHistory);
        }
    }
}
