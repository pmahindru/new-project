package com.example.csci3130_project_group17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class employernotificationofpayments extends BaseAdapter {
    Context context;
    String fullname;
    String pay;
    String status_pays;
    String date_pays;
    LayoutInflater inflater;

    ArrayList<String> arrayListname = new ArrayList<>();
    ArrayList<String> arrayListpay = new ArrayList<>();
    ArrayList<String> arrayListstatus = new ArrayList<>();
    ArrayList<String> arrayListdate = new ArrayList<>();

    TextView employeename, payrate, date, status;

    public employernotificationofpayments(Context applicationContext, String fullname, String pay, String status_pays, String date_pays) {
        this.context = applicationContext;
        this.fullname = fullname;
        this.pay = pay;
        this.status_pays = status_pays;
        this.date_pays = date_pays;
        inflater =  (LayoutInflater.from(applicationContext));

        arrayListname.add(fullname);
        arrayListpay.add(pay);
        arrayListstatus.add(status_pays);
        arrayListdate.add(date_pays);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return arrayListname.size();
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
        convertView = inflater.inflate(R.layout.paymentemployer2, null);
        employeename = convertView.findViewById(R.id.employeename);
        payrate = convertView.findViewById(R.id.payrate_paidbyemployer);
        date = convertView.findViewById(R.id.currentdate);
        status = convertView.findViewById(R.id.status);

        employeename.setText(arrayListname.get(position));
        payrate.setText(arrayListpay.get(position));
        date.setText(arrayListdate.get(position));
        status.setText(arrayListstatus.get(position));

        return convertView;
    }
}
