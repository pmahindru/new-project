package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

//just to notify that they are hired and position closed then it employer will pay them
public class Notification_ReviewApplicant_To_Employee {
    private SharedPreferences data_notification2;
    private String storedDataName2 = "jobsPrefs_fromreviewapplicants";
    private String storedUserIdKey3 = "jobID";
    private String storedUserIdKey = "employeeuID";

    private String storedUserIdKey4 = "Key_jobapplication";

    public Notification_ReviewApplicant_To_Employee(SharedPreferences data_notification) {
        this.data_notification2 = data_notification;
    }

    public Notification_ReviewApplicant_To_Employee(Context appContext) {
        this.data_notification2 = appContext.getSharedPreferences(storedDataName2,Context.MODE_PRIVATE);
    }

    //this is for the current job id from the review applicants when they hired to the notification page
    public String getStoredUserID3() {
        return getStoredDataByKey3(storedUserIdKey3);
    }

    public String getStoredDataByKey3(String key) {
        return data_notification2.getString(key,"");
    }

    public void storedjobID3(String jobID_notification) {
        storeByKeyValue3(storedUserIdKey3, jobID_notification);
    }

    public void storeByKeyValue3(String key, String value) {
        SharedPreferences.Editor editor = data_notification2.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //this is for the current user id from the review applicants to the notification page
    public String getStoredUserID2() {
        return getStoredDataByKey2(storedUserIdKey);
    }

    public String getStoredDataByKey2(String key) {
        return data_notification2.getString(key,"");
    }

    public void storedjobID2(String jobID_notification) {
        storeByKeyValue2(storedUserIdKey, jobID_notification);
    }

    public void storeByKeyValue2(String key, String value) {
        SharedPreferences.Editor editor = data_notification2.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //this is for the current key when the preson apply to it.
    public String getStoredUserID4() {
        return getStoredDataByKey4(storedUserIdKey4);
    }

    public String getStoredDataByKey4(String key) {
        return data_notification2.getString(key,"");
    }

    public void storedjobID4(String jobID_notification) {
        storeByKeyValue4(storedUserIdKey4, jobID_notification);
    }

    public void storeByKeyValue4(String key, String value) {
        SharedPreferences.Editor editor = data_notification2.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearStoredData3(){
        data_notification2.edit().clear().commit();
    }

}
