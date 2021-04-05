package com.example.csci3130_project_group17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.Calendar;

public class PaymentStatus  extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus, date;
    Button homepage;
    PaymentModel responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);
        date = findViewById(R.id.Date);
        homepage = findViewById(R.id.switch2home);

        Intent intent = getIntent();

        GsonBuilder builder = new GsonBuilder();
        Gson mGson = builder.create();

        responseData = mGson.fromJson(intent.getStringExtra("PaymentDetails"), PaymentModel.class);
        showDetails(intent.getStringExtra("Amount"));
    }

    private void showDetails(String paymentAmount) {
        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        System.out.println(currentdate);

        txtId.setText("Transaction ID -- "+responseData.getResponse().getId());
        txtStatus.setText("Status -- "+responseData.getResponse().getState());
        date.setText("Date -- " + currentdate);
        txtAmount.setText("Amount -- $" + paymentAmount);

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentStatus.this,DashboardEmployer.class);
                startActivity(intent);
            }
        });

    }
}
