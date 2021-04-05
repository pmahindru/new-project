package com.example.csci3130_project_group17;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ViewJobs extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    //Map related variables
    private GoogleMap mMap;
    LocationManager manager;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static final String LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String LOCATION_PREF = "locationPref";

    Context context;
    Activity activity;
    int radius = 8;
    int undecidedRadius = 0;

    private Circle mCircle;
    private Marker mMarker;

    CircleOptions circleOptions;
    LatLng currentLocationCoordinates = null;

    //Database related field
    DatabaseReference jobInformation;

    //Recyclcer view related fields
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter recycleViewAdaptor;

    SharedPreferences preferences;
    StoredData data;
    String savedLocation;

    //Job posts related field
    ArrayList<HashMap<String,String>> jobsList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> filteredJobsList = new ArrayList<HashMap<String, String>>();

    //Filter variables
    HashMap<String, Integer> tempFilterPreferences = new HashMap<String, Integer>();
    int pay = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs2);

        // Intialise the recycler view
        intializeRecylcerView();

        setClickListeners();
        initializeDatabase();

        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        data = new StoredData(preferences);
        savedLocation = data.getUserLocation();

        if(savedLocation.isEmpty()) {

        } else {
            String[] savedCoordinates = savedLocation.split(",");

            double lat = Double.parseDouble(savedCoordinates[0]);
            double longitude = Double.parseDouble(savedCoordinates[1]);

            currentLocationCoordinates = new LatLng(lat, longitude);

            if(!data.getUserLocationRange().isEmpty()) {
                radius = Integer.parseInt(data.getUserLocationRange());
            }

            if(!data.getStoredPayRate().isEmpty()) {
                pay = Integer.parseInt(data.getStoredPayRate());
            }

            defineRadius(currentLocationCoordinates);
            pullJobs();
        }

        }



    private void intializeRecylcerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleViewAdaptor = new RecycleViewAdaptor();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleViewAdaptor);
    }

    private void setClickListeners() {
        Button locateButton = (Button) findViewById(R.id.locateButton);
        locateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                locateUser();
            }});

        Button locationCancelButton = (Button) findViewById(R.id.locationCancelButton);
        locationCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showJobPosts();
            }});

        Button locationApplyButton = (Button) findViewById(R.id.locationApplyButton);
        locationApplyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                changeRadius();
                pullJobs();
                showJobPosts();
            }});

        Button homeButton = (Button) findViewById(R.id.viewJobsHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToHome();
            }});

        Button filterButton = (Button) findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switchToFilterOptions();

            }});


        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        search(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        return false;
                    }
                });

        searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                initializeJobPostings(jobsList);
                return false;
            }
        });

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
                if(mMap !=null) {
                    defineRadius(currentLocationCoordinates);
                    drawMarkerWithCircle();
                }

            }
        });
    }

    public void search(String query) {
        ArrayList<HashMap<String,String>> jobs = new ArrayList<HashMap<String, String>>();
        for(HashMap<String,String> job: jobsList) {
            if(job.get("jobTitle").toLowerCase().contains(query.toLowerCase())){
                jobs.add(job);
            }
        }
        initializeJobPostings(jobs);

    }

    public void switchToFilterOptions() {
        if(currentLocationCoordinates!= null) {
            View filterLayer = findViewById(R.id.filterLayer);
            ConstraintLayout showJobsLayer = findViewById(R.id.jobPostLayer);

            filterLayer.setVisibility(View.VISIBLE);
            showJobsLayer.setVisibility(View.INVISIBLE);


            Button filterCancelButton = (Button) findViewById(R.id.jobFilterCancelButton);
            filterCancelButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    View filterLayer = findViewById(R.id.filterLayer);
                    ConstraintLayout showJobsLayer = findViewById(R.id.jobPostLayer);

                    filterLayer.setVisibility(View.INVISIBLE);
                    showJobsLayer.setVisibility(View.VISIBLE);
                }
            });

            Button filterLocationButton = (Button) findViewById(R.id.filterLocationButton);
            filterLocationButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    View filterLayer = findViewById(R.id.filterLayer);
                    ConstraintLayout mapLayer = findViewById(R.id.mapLayer);

                    filterLayer.setVisibility(View.INVISIBLE);
                    mapLayer.setVisibility(View.VISIBLE);
                }
            });

            Button filterApplyButton = (Button) findViewById(R.id.jobFilterApplyButton);
            filterApplyButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(tempFilterPreferences.containsKey("pay")) {
                        pay = tempFilterPreferences.get("pay");
                    }

                    ArrayList<HashMap<String,String>> jobs = filter(pay, "pay", jobsList);
                    data.setStoredPayRate(pay);
                    initializeJobPostings(jobs);

                    View filterLayer = findViewById(R.id.filterLayer);
                    ConstraintLayout showJobsLayer = findViewById(R.id.jobPostLayer);

                    filterLayer.setVisibility(View.INVISIBLE);
                    showJobsLayer.setVisibility(View.VISIBLE);

                }
            });

            int[] filterButtons = new int[]{R.id.button7, R.id.button8, R.id.button9, R.id.button10};
            for(int button : filterButtons) {
                Button tempButton = (Button) findViewById(button);
                tempButton.setOnClickListener(this::onClick);
            }
        } else {
            showToast("Please locate yourself before you filter.");
        }
    }

    public ArrayList<HashMap<String, String>> filter(int query, String filterKey, ArrayList<HashMap<String, String>> arrayList) {
        ArrayList<HashMap<String,String>> jobs = new ArrayList<HashMap<String, String>>();
        if(filterKey.equalsIgnoreCase("pay")){
            for(HashMap<String,String> job: arrayList) {
                if(job.get("jobPayRate").matches("[0-9]+")) {
                    if(Double.parseDouble(job.get("jobPayRate"))>= query){
                        jobs.add(job);
                    }
                }
            }
        }
        return jobs;
    }

    public void showJobPosts() {
        View mapInfo =  findViewById(R.id.mapLayer);
        ConstraintLayout showJobsLayer = findViewById(R.id.jobPostLayer);

        mapInfo.setVisibility(View.INVISIBLE);
        showJobsLayer.setVisibility(View.VISIBLE);
    }


    private void switchToHome() {
        Intent dashboardEmployee = new Intent(this, DashboardEmployee.class);
        startActivity(dashboardEmployee);
    }


    public void initializeDatabase(){
        jobInformation = FirebaseDatabase.getInstance().getReference().child("JobInformation");
    }


    //Job related methods
    public void pullJobs() {
        jobInformation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jobsList.clear();
                // Store all the job posts in the jobslist arraylist as a hashmap
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Double lat = (Double) postSnapshot.child("jobLocationCoordinates").child("latitude").getValue();
                    Double longi = (Double) postSnapshot.child("jobLocationCoordinates").child("longitude").getValue();
                    String employeeID = postSnapshot.child("employeeID").getValue().toString();
                    if (lat != null && longi != null && employeeID.isEmpty()) {
                        if (isInRange(lat, longi)) {
                            HashMap<String, String> job = (HashMap<String, String>) postSnapshot.getValue();
                            job.put("jobPostId", postSnapshot.getKey());
                            job.put("jobEmployerID",postSnapshot.child("employerID").getValue().toString());
                            jobsList.add(job);
                        }
                    }
                }
                //all methods that require anything to do with the data retrieved will be called here
                //System.out.println(jobsList);


                if (!data.getStoredPayRate().isEmpty()) {
                    ArrayList<HashMap<String, String>> jobs = filter(pay, "pay", jobsList);
                    initializeJobPostings(jobs);
                } else {
                    initializeJobPostings(jobsList);
                }
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



    private void initializeJobPostings(ArrayList<HashMap<String,String>> jobs) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleViewAdaptor = new RecycleViewAdaptor(jobs);

        recyclerView.setAdapter(recycleViewAdaptor);
        recyclerView.setLayoutManager(layoutManager);

        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setVisibility(View.VISIBLE);
    }

    //Check if the given points are in the circle or not
    public boolean isInRange(double latitude, double longitude) {
        // For the next four lines, I have used the solution from https://stackoverflow.com/questions/22063842/check-if-a-latitude-and-longitude-is-within-a-circle (accessed on 13/02/2021) to get the distance between a point and radius.
        float[] results = new float[1];
        Location.distanceBetween(latitude, longitude, currentLocationCoordinates.latitude, currentLocationCoordinates.longitude, results);
        float distanceInMeters = results[0];
        boolean isWithinRange = distanceInMeters < circleOptions.getRadius();

        return isWithinRange;
    }

    //Location and map related methods
    protected void changeRadius() {
        radius = undecidedRadius;
        data.storeUserLocationRange(radius);
        defineRadius(currentLocationCoordinates);
    }

    public void defineRadius(LatLng position) {

        if(mMap!=null) {
            mMap.clear();
        }

        // Fix a way to also see radius when choosing range

        int rad = radius;

        double radiusInMeters = rad * 1000.0;  // increase decrease this distance as per your requirements
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        circleOptions = new CircleOptions()
                .center(position)
                .radius(radiusInMeters)
                .fillColor(shadeColor)
                .strokeColor(strokeColor)
                .strokeWidth(8);
    }

    private void drawMarkerWithCircle() {
        mCircle = mMap.addCircle(circleOptions);
    }

    public void locateUser() {
        View mapInfo =  findViewById(R.id.mapLayer);
        ConstraintLayout showJobsLayer = findViewById(R.id.jobPostLayer);

        showJobsLayer.setVisibility(View.INVISIBLE);
        mapInfo.setVisibility(View.VISIBLE);

        //contents from here onwards are taken from the tutorial on google maps api
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
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, listener);

        mapFragment.getMapAsync(this);
    }



    //This method is used from the tutorial on google maps api
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

        if (currentLocationCoordinates != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationCoordinates, 10));
            defineRadius(currentLocationCoordinates);
            drawMarkerWithCircle();
        }
    }

    //This listener is used from the tutorial on google maps api
    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            currentLocationCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
            String tempLocation = location.getLatitude() + "," + location.getLongitude();
            data.storeUserLocation(tempLocation);


            Log.d("Location", "" + location.getLatitude() + "," + location.getLongitude());

            if(mMap!=null) {
                mMap.clear();
                defineRadius(currentLocationCoordinates);
                drawMarkerWithCircle();
            }

            if (mMap != null) {
               /* if (isFirstLaunch) {
                    MarkerOptions mOptions = new MarkerOptions().position(currentLocationCoordinates);
                    myMarker = mMap.addMarker(mOptions);
                    isFirstLaunch = false;
                } else {
                    myMarker.setPosition(currentLocationCoordinates);
                }*/
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationCoordinates, 10));
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

    //This method is used from the tutorial on google maps api
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

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        int[] paysRates = new int[]{R.id.button7, R.id.button8, R.id.button9, R.id.button10};
        boolean flag = false;

        for(int id : paysRates) {
            if(button.getId() == id) {
                flag = true;
            }
        }

        if(flag) {
            if(button.getText().toString().equalsIgnoreCase("Any")) {
                tempFilterPreferences.put("pay", -1);

            } else {
                int numberOnly= Integer.parseInt(button.getText().toString().replaceAll("[^0-9]", ""));
                tempFilterPreferences.put("pay", numberOnly);

            }
        }

    }
}
