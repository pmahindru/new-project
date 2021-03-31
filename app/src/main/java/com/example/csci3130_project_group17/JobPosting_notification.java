package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

//storing the current jobs and send all the users via notification
//taking reference from the StoreData that was made by sabrina for the page
public class JobPosting_notification {
    private SharedPreferences data_notification;
    private String storedDataName2 = "jobsPrefs";
    private String storedUserIdKey2 = "jobID";

    public JobPosting_notification(SharedPreferences data_notification) {
        this.data_notification = data_notification;
    }

    public JobPosting_notification(Context appContext) {
        this.data_notification = appContext.getSharedPreferences(storedDataName2,Context.MODE_PRIVATE);
    }

    public String getStoredUserID2() {
        return getStoredDataByKey(storedUserIdKey2);
    }

    public String getStoredDataByKey(String key) {
        return data_notification.getString(key,"");
    }

    public void storedjobID(String jobID_notification) {
        storeByKeyValue(storedUserIdKey2, jobID_notification);
    }

    public void storeByKeyValue(String key, String value) {
        SharedPreferences.Editor editor = data_notification.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearStoredData2(){
        data_notification.edit().clear().commit();
    }
}