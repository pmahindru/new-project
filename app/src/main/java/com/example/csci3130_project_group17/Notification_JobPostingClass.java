package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Notification_JobPostingClass extends BaseAdapter {
    Context context;
    ArrayList<String> jobname;
    ArrayList<String> Location;
    LayoutInflater inflater;

    //initializing the text-view of the job details
    TextView jobtitle;
    TextView location;
    Button reviewjob;
    String jobid;


    public Notification_JobPostingClass(Context applicationContext, ArrayList<String> name, ArrayList<String> location, String currrent_jobID) {
        this.context = applicationContext;
        this.jobname = name;
        this.Location = location;
        inflater =  (LayoutInflater.from(applicationContext));
        this.jobid = currrent_jobID;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return jobname.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */


    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.notification_content, null);
        jobtitle = convertView.findViewById(R.id.jobtitle_notification);
        location = convertView.findViewById(R.id.location_notification);
        String title = "<b> Job Title </b>" + jobname.get(position);
        String loca = "<b> Location </b> " + Location.get(position);

        reviewjob = convertView.findViewById(R.id.reviewjobnotification);
        reviewjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,JobDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("jobID2",jobid);
                context.startActivity(intent);
            }
        });


        jobtitle.setText(Html.fromHtml(title));
        location.setText(Html.fromHtml(loca));

        return convertView;
    }
}
