package com.example.csci3130_project_group17;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class JobPosting extends AppCompatActivity implements View.OnClickListener {

    private Button createButton, ButtonHome, imageButton;
    private String errorMessage;
    DatabaseReference jobInformation;
    location location;

    Context context;
    Activity activity;


    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";
    String uID;


    //storing new jobs for the notification
    JobPosting_notification appData_notification;
    SharedPreferences data_notification;
    String jobID_notification = null;
    String jobID = UUID.randomUUID().toString();

    StorageReference storageReference;
    Uri FilePathUri;
    uploadinfo imageUploadInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);

        Intent jobIntent = getIntent();
        data_notification = getSharedPreferences("jobsPrefs", Context.MODE_PRIVATE);
        appData_notification = new JobPosting_notification(data_notification);

        StoredData data = new StoredData(getApplicationContext());
        uID = data.getStoredUserID();

        storageReference = FirebaseStorage.getInstance().getReference("jobimages");

        createButton = findViewById(R.id.createJobButton);
        createButton.setOnClickListener(this);
        ButtonHome = findViewById(R.id.homeButton);
        ButtonHome.setOnClickListener(this);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);

        initializedatabase();
        locateUser();
    }

    private void imagepost() {
        //method for choosing image from cellphone
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"),7);

    }

    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }
    //method for upload image and jobname
    public void Fileuploader(){
        String error = getErrorMessage();
        StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExtension(FilePathUri));
        reference.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //text of successfully upload the image
                Toast.makeText(getApplicationContext(), "Image Successfully Uploaded", Toast.LENGTH_LONG).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                assert uri != null;
                imageUploadInfo = new uploadinfo(uri.toString());
            }
        });
    }

    //display image on the image view bar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                Fileuploader();
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    //inital database
    public void initializedatabase() {
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }

    //get method
    protected String getJobTitle() {
        EditText title = findViewById(R.id.jobTitleInput);
        return title.getText().toString().trim();
    }

    protected String getJobType() {
        EditText type = findViewById(R.id.jobTypeInput);
        return type.getText().toString().trim();
    }

    protected String getJobPayRate() {
        EditText rate = findViewById(R.id.jobPayRateInput);

        return rate.getText().toString().trim();
    }

    protected String getJobLocation() {
        EditText location = findViewById(R.id.jobLocationInput);
        return location.getText().toString().trim();
    }

    protected String getJobDescription() {
        EditText description = findViewById(R.id.jobDescriptionInput);
        return description.getText().toString().trim();
    }



    //missing or invalid input
    protected boolean jobTitleIsEmpty(String s) {
        return s.isEmpty();
    }

    public boolean jobTypeIsEmpty(String s) {
        return s.isEmpty();
    }

    public boolean jobLocationIsEmpty(String s) {
        return s.isEmpty();
    }

    public boolean jobPayRateIsEmpty(String s) {

        return s.isEmpty();
    }

    public boolean jobDescriptionIsEmpty(String s) {
        return s.isEmpty();
    }

    protected String getErrorMessage() {
        //clear error message
        errorMessage = "";
        //determine if any fields are empty
        if (jobTitleIsEmpty(getJobTitle())) {
            errorMessage = "Enter Job Title";
        } else if (jobTypeIsEmpty(getJobType())) {
            errorMessage = "Enter Job Type";
        } else if (jobPayRateIsEmpty(getJobPayRate())) {
            errorMessage = "Enter Pay Rate";
        } else if (jobLocationIsEmpty(getJobLocation())) {
            errorMessage = "Enter Job Location";
        } else if (getJobDescription().isEmpty()) {
            errorMessage = "Enter Job Description";
        }
        //determine if invalid characters entered
        else {
            if ((!isletter(getJobTitle())) || (!isletter(getJobType()))) {
                errorMessage = "Only letters allowed";
            }
        }

        return errorMessage;
    }

    protected boolean isletter(String input) {
        return input.matches("^[A-Za-z\\s]+$");
    }

    protected void saveJobToDatabase() {
        Job job = new Job(getJobTitle(),getJobType(),getJobDescription(),getJobLocation(),getJobPayRate(),"open","",uID,imageUploadInfo.getImageURL());
        job.setJobLocationCoordinates(location);
        jobID_notification = jobID;
        appData_notification.storedjobID(jobID_notification);
        System.out.println(jobID+"------------------------------------i am done with this shit");
        jobInformation.child(jobID).setValue(job);
    }

    protected void switchToMain() {
        Intent intent = new Intent(this, DashboardEmployer.class);
        startActivity(intent);
    }

    protected void switchToJobPage() {

    }

    public void locateUser() {
        //contents from here onwards are taken from the tutorial on google maps api
        activity = JobPosting.this;
        context = JobPosting.this;

        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission(activity, context, LOCATION_PERMISSION, LOCATION_PREF);
        } else {
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    //This method is used from the tutorial on google maps api
    private void checkLocationPermission(final Activity activity, final Context context, final String Permission, final String prefName) {

        PermissionUtil.checkPermission(activity, context, Permission, prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(JobPosting.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining is permission denied previously , but app require it and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(JobPosting.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionDisabled() {
                        // permission check box checked and permission denied previously .
                        askUserToAllowPermissionFromSetting();
                    }

                    @Override
                    public void onPermissionGranted() {
                    }
                });
    }

    //This method is used from the tutorial on google maps api
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    //This method is used from the tutorial on google maps api
    private void askUserToAllowPermissionFromSetting() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Permission Required:");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kindly allow permission to access your location, without this permission we can't save the location of your job.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        showToast("Permission forever Disabled.");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void onClick(View v) {
        //determine which button was pressed
        switch (v.getId()) {
            case R.id.createJobButton:
                String error = getErrorMessage();
                //if there is an error message, notify user of error
                if (!error.isEmpty()) {
                    Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                } else {
                    //if no errors, publish job in database and notify user of success
                    turnLocationNametoCoordinates();
                    Toast.makeText(getBaseContext(), "Job Successfully Created", Toast.LENGTH_LONG).show();
                }
                //switch to job page
                switchToJobPage();
                break;
            case R.id.homeButton:
                //switch to home page
                switchToMain();
                break;
            case R.id.imageButton:
                //switch to add image page
                imagepost();
                break;
        }
    }

    public void turnLocationNametoCoordinates() {

        String address = getJobLocation();

        // The code below has been used from https://stackoverflow.com/questions/9698328/how-to-get-coordinates-of-an-address-in-android/9698408#9698408 on 12/03/2021 to convert user input to location coordinates
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
          if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                location = new location(latitude,longitude);
                saveJobToDatabase();
            } else {
                showToast("Invalid location. Please enter the street and city of your location.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}