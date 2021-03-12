package com.example.csci3130_project_group17;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewJobs extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager manager;

    DatabaseReference jobInformation;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter recycleViewAdaptor;

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";

    Context context;
    Activity activity;
    int radius = 5;
    int undecidedRadius = 0;

    private Circle mCircle;
    private Marker mMarker;

    LatLng currentLocation;

    ArrayList<HashMap<String,String>> jobsList = new ArrayList<HashMap<String, String>>();

    //ArrayList<Places> placesList = new ArrayList<>();
    //PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleViewAdaptor = new RecycleViewAdaptor();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleViewAdaptor);

        setClickListeners();
        initializeDatabase();
        pullJobs();

    }

    private void initializeJobPostings() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleViewAdaptor = new RecycleViewAdaptor(jobsList);

        recyclerView.setAdapter(recycleViewAdaptor);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void initializeDatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }

    protected void changeRadius() {
        radius = undecidedRadius;
    }

    private void setClickListeners() {
        Button locateButton = (Button) findViewById(R.id.locateButton);

        locateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                locateUser();
            }});


        Button locationCancelButton = (Button) findViewById(R.id.locationApplyButton);

        locationCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showJobPosts();
            }});

        Button locationApplyButton = (Button) findViewById(R.id.locationApplyButton);

        locationApplyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeRadius();
                showJobPosts();
            }});


        SeekBar rangeInput = (SeekBar) findViewById(R.id.rangeInput);
        rangeInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                undecidedRadius = progress;
                mMap.clear();
                drawMarkerWithCircle(currentLocation);
            }
        });
    }

    private void drawMarkerWithCircle(LatLng position) {
        mMap.clear();
        double radiusInMeters = radius * 1000.0;  // increase decrease this distancce as per your requirements
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);
    }

    public void pullJobs() {

        jobInformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Store all the job posts in the jobslist arraylist as a hashmap
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    jobsList.add((HashMap<String, String>) postSnapshot.getValue());
                }


                //all methods that require anything to do with the data retrieved will be called here

                System.out.println(jobsList);
                for(HashMap<String,String> jobItem: jobsList) {
                    System.out.println(jobItem.get("jobTitle"));

                }

                initializeJobPostings();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                String TAG = "JobRetrievalError";
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });





    }

    public void showJobPosts() {
        View mapInfo =  findViewById(R.id.mapLayer);
        RecyclerView jobPostings = findViewById(R.id.recyclerView);

        mapInfo.setVisibility(View.INVISIBLE);
        jobPostings.setVisibility(View.VISIBLE);

    }

    public void locateUser() {

        View mapInfo =  findViewById(R.id.mapLayer);
        RecyclerView jobPostings = findViewById(R.id.recyclerView);

        jobPostings.setVisibility(View.INVISIBLE);
        mapInfo.setVisibility(View.VISIBLE);

        activity = ViewJobs.this;
        context = ViewJobs.this;

        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission(activity, context, LOCATION_PERMISSION, LOCATION_PREF);
        } else {
        }

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

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

        mapFragment.getMapAsync(this);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
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
            drawMarkerWithCircle(currentLocation);
        }
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            Log.d("Location", "" + location.getLatitude() + "," + location.getLongitude());

            mMap.clear();
            drawMarkerWithCircle(currentLocation);

            if (mMap != null) {
               /* if (isFirstLaunch) {
                    MarkerOptions mOptions = new MarkerOptions().position(currentLocation);
                    myMarker = mMap.addMarker(mOptions);
                    isFirstLaunch = false;
                } else {
                    myMarker.setPosition(currentLocation);
                }*/
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


    private void checkLocationPermission(final Activity activity, final Context context, final String Permission, final String prefName) {

        PermissionUtil.checkPermission(activity, context, Permission, prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(ViewJobs.this,
                                new String[]{Permission},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining is permission denied previously , but app require it and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(ViewJobs.this,
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
                        showToast("Permission Granted.");
                    }
                });
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void askUserToAllowPermissionFromSetting() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
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
}
