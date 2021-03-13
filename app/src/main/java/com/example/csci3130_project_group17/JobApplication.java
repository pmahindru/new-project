package com.example.csci3130_project_group17;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class JobApplication extends AppCompatActivity implements OnMapReadyCallback {

    public static String WELCOME_MESSAGE = "ca.dal.csci3130.a2.welcome";

    //this is for the read and write in the database
    FirebaseDatabase database =  null;
    DatabaseReference userstable = null;
    StorageReference storageReference = null;

    //for the resume and add in the database
    EditText resume;
    private Uri datauri;

    //for adding in the database
    private static final Map<String, Object> user = new HashMap<>();

    //giving the specific id to each user
    UUID idOne = UUID.randomUUID();
    String count = String.valueOf(idOne);

    //for location
    EditText location;
    private GoogleMap mMap;
    LocationManager manager;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";
    Context context;
    Activity activity;
    LatLng currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobapplication);

        Intent intent = getIntent();

        //initiating the Firebase
        initializeDatabase();

        //button function take place
        OnClick();
    }

    private void OnClick() {

        Button square_button = (Button)findViewById(R.id.Apply);
        square_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckEmailAndPasswordIsValid(email_address(), first_Name(), last_Name(),phonenumber(),resume.getText().toString());
            }
        });

        // take value of button
        Button square_button2 = (Button)findViewById(R.id.switch2home);
        square_button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                swtich2home();
            }
        });

        //for the location
        location = findViewById(R.id.Location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocatioOfUser();
            }
        });

        //for the resume
        resume = findViewById(R.id.Resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectanduploadresume();
            }
        });

    }

    private void swtich2home() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
        startActivity(dashboardEmployee);
    }

    private void swtich2ViewJob() {
        Intent viewjobs = new Intent(this, ViewJobs.class);
        startActivity(viewjobs);
    }

    public void initializeDatabase(){
        //initialize your database and related fields here
        database =  FirebaseDatabase.getInstance();
        userstable = database.getReference("application");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    protected String first_Name() {
        EditText firstName = findViewById(R.id.Firstname);
        return firstName.getText().toString().trim();
    }

    protected String last_Name() {
        EditText lastname = findViewById(R.id.Lastname);
        return lastname.getText().toString().trim();
    }

    protected String email_address() {
        EditText emailaddress = findViewById(R.id.Email);
        return emailaddress.getText().toString().trim();
    }

    protected String phonenumber() {
        EditText phonenumber = findViewById(R.id.Phonenumber);
        return phonenumber.getText().toString().trim();
    }

    private void addtodatabase(Map<String, Object> user) {
        String fname = first_Name();
        String lname = last_Name();
        String email = email_address();
        String phonenumber = phonenumber();

        user.put("firstName", fname);
        user.put("lastName", lname);
        user.put("email", email);
        user.put("phone_number", phonenumber);
        user.put("location",currentLocation);

        userstable.child(count).setValue(user);
    }

    protected boolean isInputEmpty(String str){
        return str.isEmpty();
    }

    //email check if it is valid or not
    //taken from the given link
    //https://java2blog.com/validate-phone-number-java/
    public boolean emailCheck(String email){
        return email.matches("^(.+)@([a-zA-Z0-9]+.[a-zA-Z0-9].+)$");
    }

    //phone number check if it is valid or not
    //taken from thr given link
    //https://www.geeksforgeeks.org/java-program-check-valid-mobile-number/
    public boolean Phonenumber(String phone){
        String pattern = "(0/91)?[7-9][0-9]{9}";
        return phone.matches(pattern);
    }

    //resume is uploaded or not
    //regex is modify by me but reference from the given link but i didnt copy and paste the regex
    //https://www.regextester.com/93592
    //https://www.freeformatter.com/regex-tester.html
    public boolean Location(){
        //removing the white space from the address and then check with the regex.
        String locate = location.getText().toString().replaceAll("\\s+","");
        return locate.matches("^\\d+[A-z]+[,][A-z]+[,]+[A-z\\d]+[,][A-z]+");
    }

    //resume is uploaded or not
    //regex is taken from the give link
    //http://findnerd.com/list/view/How-to-Validate-File-extension-before-Upload-using-Regular-Expression-in-JavaScript-and-jQuery/23837/
    public boolean Resume(String resume){
        return resume.matches("([a-zA-Z0-9\\s_\\\\.\\-:])+(.doc|.docx|.pdf)$");
    }

    private void MessageDisplay(String msg){
        //alert square box that shows the answer
        AlertDialog.Builder alert_answer = new AlertDialog.Builder(this);
        // change the integer value into string value
        alert_answer.setMessage(msg);
        alert_answer.setPositiveButton("ok",null);
        alert_answer.create();
        alert_answer.show();
    }

    private void CheckEmailAndPasswordIsValid(String email, String firstname, String lastname, String phonenumber, String resume) {
        if(isInputEmpty(email) || isInputEmpty(firstname) || isInputEmpty(lastname) || isInputEmpty(phonenumber) || isInputEmpty(resume) || currentLocation == null || !Location()) {
            MessageDisplay("One of the fields are empty");
        }
        else{
            if(!emailCheck(email)) {
                MessageDisplay("Email is invalid");
            }
            else if (!Phonenumber(phonenumber)){
                MessageDisplay("Phone number is invalid");
            }
            else if (!Resume(resume)){
                MessageDisplay("Please upload the pdf fle");
            }
            else  if (emailCheck(email) && Resume(resume) && Phonenumber(phonenumber) && !isInputEmpty(firstname) && !isInputEmpty(lastname) && currentLocation != null || Location()){
                addtodatabase(user);
                updatepdffiletodatabase(datauri);
                MessageDisplay("Application is submitted");
                swtich2ViewJob();
            }
        }
    }


    //upload a resume in pdf in the database i took a look from here
    //https://www.youtube.com/watch?v=lY9zSr6cxko
    // ---------------------------- start here -----------------------------------------------
    public void selectanduploadresume() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),86);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 86 && resultCode == RESULT_OK && data != null && data.getData()!= null){
            getNameAndData(data);
        }

    }

    //just took a reference from thr given link
    ////https://stackoverflow.com/questions/39696906/select-pdf-file-from-phone-on-button-click-and-display-its-file-name-on-textview
    @SuppressLint("Recycle")
    private void getNameAndData(Intent data) {
        Context context = this;
        Cursor name = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            name = context.getContentResolver().query(data.getData(),null,null,null);
        }
        if (name != null){
            int namenumber = name.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int size = name.getColumnIndex(OpenableColumns.SIZE);
            name.moveToFirst();
            if (namenumber >= 0 && size >= 0){
                resume.setText(name.getString(namenumber));
            }
            else {
                MessageDisplay("size of the pdf is less than 0 or 0");
            }
        }
        else {
            MessageDisplay("error in pdf file");
        }
        this.datauri = data.getData();
    }

    private void updatepdffiletodatabase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is uploading");
        progressDialog.show();

        StorageReference reference = storageReference.child("Resume/").child(first_Name() + " " + last_Name() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                assert uri != null;
                PutPdftoDatabse putPdftoDatabse = new PutPdftoDatabse(resume.getText().toString(), uri.toString());
                userstable.child(count).child("resume").setValue(putPdftoDatabse);
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progess = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("File is uploading......" + (int) progess + "%");
            }
        });
    }
    // ---------------------------- ends here -----------------------------------------------

    //For the location
    // this code is modified by Pranav Mahindru but taken from the lab.
    // I just copy paste location code from the Lab
    // ---------------------------- Starts here -----------------------------------------------

    public void currentLocatioOfUser() {

        activity = JobApplication.this;
        context = JobApplication.this;

        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission(activity, context);
        } else {
        }

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, listener);
    }

    private void checkLocationPermission(final Activity activity, final Context context) {

        PermissionUtil.checkPermission(activity, context, JobApplication.LOCATION_PERMISSION, JobApplication.LOCATION_PREF,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(JobApplication.this,
                                new String[]{JobApplication.LOCATION_PERMISSION},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining is permission denied previously , but app require it and then request permission

                        MessageDisplay("Permission previously Denied.");

                        ActivityCompat.requestPermissions(JobApplication.this,
                                new String[]{JobApplication.LOCATION_PERMISSION},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionDisabled() {
                        // permission check box checked and permission denied previously .
                        askUserToAllowPermissionFromSetting();
                    }

                    @Override
                    public void onPermissionGranted() {
                        MessageDisplay("Permission Granted.");
                    }
                });
    }

    private void askUserToAllowPermissionFromSetting() {

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Permission Required:");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kindly allow Permission from App Setting, without this permission app could not show maps.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        MessageDisplay("Permission forever Disabled.");
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
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Showing / hiding your current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        mMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        if (currentLocation != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
        }
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            Log.d("Location", "" + location.getLatitude() + "," + location.getLongitude());
            getTheFullAddressOfTheUser(location.getLatitude(), location.getLongitude());

            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    //reference from the given link
    //http://wintechtutorials.com/blog/android-get-address-latitude-longitude-google-map-tutorial/
    //so basically from this user can see the address but in the database we only adding the Latitude and Longitude.
    private void getTheFullAddressOfTheUser(double getLatitude, double getLongitude){
        String full_address = null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address>  get_the_address = geocoder.getFromLocation(getLatitude,getLongitude,1);
            if (get_the_address != null && get_the_address.size() > 0){

                //here we getting the first address
                Address address = get_the_address.get(0);

                //just for the checking that i am gettiing current location or not
                System.out.println("123123  = " + get_the_address.get(0));

                //but here we are getting that specific line of the address
                full_address = address.getAddressLine(0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        location.setText(full_address);
    }
    // ---------------------------- ends here -----------------------------------------------
}
