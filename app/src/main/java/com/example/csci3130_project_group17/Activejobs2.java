package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//this class referenced from the https://abhiandroid.com/ui/listview
public class Activejobs2 extends BaseAdapter {
    Context context;
    String[] arrjobtitle;
    String[] arrjobpayrate;
    String[] arrjolocation;
    ArrayList<String> jobids;
    LayoutInflater inflater;
    Boolean isEmployer;
    //initializing the text-view of the job details
    TextView jobtitle;
    TextView location;
    TextView payrate;
    TextView viewchat;


    public Activejobs2(Context applicationContext, String[] arrjobtitle, String[] arrjobpayrate, String[] arrjolocation, ArrayList<String> jobids, Boolean isEmployer) {
        this.context = applicationContext;
        this.arrjobtitle = arrjobtitle;
        this.arrjobpayrate = arrjobpayrate;
        this.arrjolocation = arrjolocation;
        this.jobids=jobids;
        this.isEmployer = isEmployer;
        inflater =  (LayoutInflater.from(applicationContext));
    }

    //they automatically made when i create this class.
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return arrjobtitle.length;
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
    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.activejobs2,null);
        jobtitle = convertView.findViewById(R.id.jobTitle);
        location = convertView.findViewById(R.id.locationfromdatabase);
        payrate = convertView.findViewById(R.id.payrate);
        viewchat = convertView.findViewById(R.id.viewchat);
        if (isEmployer){
            viewchat.setText("View Applications");
        }



        //this thing is taken from the gve link which is bold the specif text
        //https://stackoverflow.com/questions/14371092/how-to-make-a-specific-text-on-textview-bold
        String titlePlain = arrjobtitle[position];
        String title = "<b> Company Name </b> <br>" + arrjobtitle[position];
        String loca = "<b> Location </b> <br>" + arrjolocation[position];
        String Payrate = "<b> PayRate </b> <br>" + arrjobpayrate[position];
        String id = jobids.get(position);

        jobtitle.setText(Html.fromHtml(title));
        location.setText(Html.fromHtml(loca));
        payrate.setText(Html.fromHtml(Payrate));

        viewchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewchat.getText().equals("View Applications")){
                    Intent application = new Intent(context,ViewApplicants.class);
                    application.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    application.putExtra("jobId", id);
                    application.putExtra("jobTitle", titlePlain);
                    context.startActivity(application);
                }else{
                    Intent chat = new Intent(context,Chat.class);
                    chat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    chat.putExtra("jobId", id);
                    context.startActivity(chat);
                }
            }
        });

        return convertView;
    }

}

