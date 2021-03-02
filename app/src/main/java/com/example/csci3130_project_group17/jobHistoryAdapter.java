package com.example.csci3130_project_group17;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class jobHistoryAdapter extends FirebaseRecyclerAdapter<Job, jobHistoryAdapter.historyViewholder> {

    //constructor
    public jobHistoryAdapter(@NonNull FirebaseRecyclerOptions<Job> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull historyViewholder holder, int position, @NonNull Job model) {
        //bind view holders to Job model fields
        holder.title.setText(model.getJobTitle());
        holder.name.setText("employer name");
        holder.totalPay.setText(model.getJobPayRate());
    }

    @NonNull
    @Override
    public historyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use history item template to show data
        View historyItemTemplate = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_history_item, parent, false);
        return new jobHistoryAdapter.historyViewholder(historyItemTemplate);
    }

    public class historyViewholder extends RecyclerView.ViewHolder{

        TextView title, name, totalPay;
        //get references to history item layout template views
        public historyViewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.jobTitleLayout);
            name = itemView.findViewById(R.id.jobNameLayout);
            totalPay = itemView.findViewById(R.id.jobTotalLayout);

        }

    }

}
