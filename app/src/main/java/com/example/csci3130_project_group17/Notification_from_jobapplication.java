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

public class Notification_from_jobapplication extends BaseAdapter {
    Context context;
    ArrayList<String> name;
    ArrayList<String> Location;
    LayoutInflater inflater;

    String currentid;
    String currentjobid;

    //initializing the text-view of the job details
    TextView fullname;
    TextView location;
    Button reviewapplication;


    public Notification_from_jobapplication(Context applicationContext, ArrayList<String> name, ArrayList<String> location, String currentuserid, String jobidforthatspecificapplication) {
        this.context = applicationContext;
        this.name = name;
        this.Location = location;
        this.currentid = currentuserid;
        this.currentjobid = jobidforthatspecificapplication;
        inflater = (LayoutInflater.from(applicationContext));
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return name.size();
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
        convertView = inflater.inflate(R.layout.notification_content_employer, null);

        fullname = convertView.findViewById(R.id.full_name_notification_employer);
        location = convertView.findViewById(R.id.location_notification_employer);
        String title = "<b> Job Title </b>" + name.get(position);
        String loca = "<b> Location </b> " + Location.get(position);

        reviewapplication = convertView.findViewById(R.id.reviewjobnotification_employer);
        reviewapplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReviewApplicants.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userId",currentid);
                intent.putExtra("jobId",currentjobid);
                context.startActivity(intent);

            }
        });

        fullname.setText(Html.fromHtml(title));
        location.setText(Html.fromHtml(loca));

        return convertView;
    }
}
