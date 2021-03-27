package com.example.csci3130_project_group17;

public class Applicant {

    private String firstName;

    private String lastName;

    private String currentUserID;

    public  Applicant() {};

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setCurrentUserID(String id){this.currentUserID = id;}

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getCurrentUserID() {return this.currentUserID;}


}
