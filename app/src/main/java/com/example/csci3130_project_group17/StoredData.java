package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

public class StoredData {
    private SharedPreferences storedAppData;
    private String storedDataName = "userPrefs";
    private String storedUserIdKey = "uID";

    public StoredData(SharedPreferences storedAppData) {
        this.storedAppData = storedAppData;

    }

    public StoredData(Context appContext) {
        this.storedAppData = appContext.getSharedPreferences(storedDataName, Context.MODE_PRIVATE);


    }

    public String getStoredUserID() {
        return getStoredDataByKey(storedUserIdKey);
    }

    public String getStoredDataByKey(String key) {
        return storedAppData.getString(key, "");
    }

    public void storeUserID(String uID) {
        storeByKeyValue(storedUserIdKey, uID);
    }

    public void storeByKeyValue(String key, String value) {
        SharedPreferences.Editor editor = storedAppData.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void clearStoredData(){
        storedAppData.edit().clear().commit();
    }

}
