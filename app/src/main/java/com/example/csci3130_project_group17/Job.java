package com.example.csci3130_project_group17;

public class Job {
    private String jobTitle;
    private String jobType;
    private String jobDescription;
    private String jobLocation;
    private String jobPayRate;
    private String jobState;

    //constructors
    public Job() {
    }

    public Job(String jobTitle, String jobType, String jobDescription, String jobLocation, String jobPayRate, String jobState) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.jobPayRate = jobPayRate;
        this.jobState = jobState;
    }

    //getters and setters
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

    public String getJobState() {
        return jobState;
    }

    public void setJobState(String jobState) {
        this.jobState = jobState;
    }
}
