package com.example.csci3130_project_group17;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;


public class paymentpage extends AppCompatActivity {
    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference usertable = null;

    //user information
    String uID;
    SharedPreferences preferences;
    StoredData data;
    Boolean isEmployer;

    //paypal information from lab
    private static final int PAYPAL_REQUEST_CODE = 555;

    private static PayPalConfiguration config  = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    Button btnPayNow;
    EditText edtAmount;

    String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //storing user id in the uID so that it is easy to get the current user and we can show them
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        uID = data.getStoredUserID();
        isEmployer = data.getUserType();

        if(isEmployer){
            setContentView(R.layout.paymentemployer);
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
            startService(intent);

            edtAmount= findViewById(R.id.Amount);
            btnPayNow = findViewById(R.id.pay_submit_payment);

            btnPayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processPayment();
                }
            });
        }

        Onclick();

        //initiating the Firebase
        initializeDatabase();
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        usertable = database.getReference("users");
    }

    //on click for the homepage
    public void Onclick() {
        // take value of button
        Button square_button2 = (Button)findViewById(R.id.switch2home);
        square_button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                swtich2home();
            }
        });
    }
    public void swtich2home() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
        startActivity(dashboardEmployee);
    }

    private void processPayment() {

        amount = edtAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"CAD",
                "Purchase Goods",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString();
                        System.out.println("Payment Success Retrieving payment object" +paymentDetails);
                        //Log.d("Details",paymentDetails);
                        startActivity(new Intent(this,PaymentStatus.class)
                                .putExtra("PaymentDetails",paymentDetails)
                                .putExtra("Amount",amount));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
}
