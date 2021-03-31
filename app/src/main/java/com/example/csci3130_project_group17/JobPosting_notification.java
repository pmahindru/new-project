
package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

public class JobPosting_notification{
    private SharedPreferences storedAppData2;
    private String storedDataName2 = "current_jobs";
    private String storedUserIdKey2 = "uID_notification";

    public JobPosting_notification(SharedPreferences storedAppData) {
        this.storedAppData2 = storedAppData;

    }

    public JobPosting_notification(Context appContext) {
        this.storedAppData2 = appContext.getSharedPreferences(storedDataName2, Context.MODE_PRIVATE);
    }

    public String getStoredUserID2() {
        return getStoredDataByKey2(storedUserIdKey2);
    }

    public String getStoredDataByKey2(String key) {
        return storedAppData2.getString(key, "");
    }


    public void storeUserID2(String uID) {
        storeByKeyValue2(storedUserIdKey2, uID);
    }

    public void storeByKeyValue2(String key, String value) {
        SharedPreferences.Editor editor = storedAppData2.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearStoredData(){
        storedAppData2.edit().clear().commit();
    }

}
