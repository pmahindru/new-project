package com.example.csci3130_project_group17;

import com.google.android.gms.maps.model.LatLng;

public class Job {
    private String id;
    private String jobTitle;
    private String jobType;
    private String jobDescription;
    private String jobLocation;
    private String jobPayRate;
    private String state;
    private String employeeID;
    private String employerID;
    private LatLng locationCoordinates;

    //constructors
    public Job() {
    }

    public Job(String jobTitle, String jobType, String jobDescription, String jobLocation, String jobPayRate, String state, String employeeID, String employerID, LatLng location) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.jobPayRate = jobPayRate;
        this.state = state;
        this.employeeID = employeeID;
        this.employerID = employerID;
        this.locationCoordinates = location;
    }

    //getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobPayRate() {
        return jobPayRate;
    }

    public void setJobPayRate(String jobPayRate) {
        this.jobPayRate = jobPayRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public LatLng getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(LatLng locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

}
