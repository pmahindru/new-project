package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

//just to notify that they are hired and position closed then it employer will pay them
public class Notification_ReviewApplicant_To_Employee {
    private SharedPreferences data_notification2;
    private String storedDataName2 = "jobsPrefs_fromreviewapplicants";
    private String storedUserIdKey3 = "jobID";

    public Notification_ReviewApplicant_To_Employee(SharedPreferences data_notification) {
        this.data_notification2 = data_notification;
    }

    public Notification_ReviewApplicant_To_Employee(Context appContext) {
        this.data_notification2 = appContext.getSharedPreferences(storedDataName2,Context.MODE_PRIVATE);
    }

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

    public void clearStoredData3(){
        data_notification2.edit().clear().commit();
    }
}
