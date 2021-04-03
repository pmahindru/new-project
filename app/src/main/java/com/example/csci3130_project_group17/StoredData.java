package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

public class StoredData {
    private SharedPreferences storedAppData;
    private String storedDataName = "userPrefs";
    private String storedUserIdKey = "uID";
    private String storedUserTypeKey = "employer";
    private String storedUserLocation = "userLocation";
    private String storedLocationRange = "userLocationRange";
    private String storedPayRate = "userPayRate";

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

    public boolean getUserType() {
        return storedAppData.getBoolean(storedUserTypeKey, false);
    }

    public void storeUserID(String uID) {
        storeByKeyValue(storedUserIdKey, uID);
    }

    public void storeUserLocationRange(int radius) {
        storeByKeyValue(storedLocationRange, String.valueOf(radius));
    }

    public String getUserLocationRange() {
        return getStoredDataByKey(storedLocationRange);
    }

    public void setStoredPayRate(int pay) {
        storeByKeyValue(storedPayRate, String.valueOf(pay));
    }

    public String getStoredPayRate() {
        return getStoredDataByKey(storedPayRate);
    }

    public void storeUserLocation(String location) {
        storeByKeyValue(storedUserLocation, location);
    }

    public String getUserLocation() {
        return getStoredDataByKey(storedUserLocation);
    }

    public void storeByKeyValue(String key, String value) {
        SharedPreferences.Editor editor = storedAppData.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void storeUserType( Boolean userType) {
        SharedPreferences.Editor editor = storedAppData.edit();
        editor.putBoolean(storedUserTypeKey, userType) ;
        editor.commit();
    }

    public void clearStoredData(){
        storedAppData.edit().clear().commit();
    }

}
