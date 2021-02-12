package com.example.csci3130_project_group17;

public class User {
    String Email, firstName, lastName, orgName, password;
    boolean employee, employer;

    public User(){};
    public User (String Email, String firstName, String lastName, String orgName, String password){
        this.Email = Email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orgName = orgName;
        this.password = password;
        this.employee = false;
        this.employer = false;
    }

    public void setEmployee(boolean b){
        employee = b;
    }

    public void setEmployer(boolean b){
        employer = b;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public boolean logInCheck(String password){
        return password.equals(this.password);
    }
}
