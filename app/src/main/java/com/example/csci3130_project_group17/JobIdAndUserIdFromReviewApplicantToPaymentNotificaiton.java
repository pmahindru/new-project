package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

public class JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton {

    private SharedPreferences data_pays;
    private String storedDataName = "bothidsPrefs";

    private String storedUserIdKey = "jobID";
    private String storedUserIdKey2 = "employeeuID";
    private String storedUserIdKey3 = "Status";
    private String storedUserIdKey4 = "Date";

    public JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton(SharedPreferences data__forpaymentpage) {
        this.data_pays = data__forpaymentpage;
    }

    public JobIdAndUserIdFromReviewApplicantToPaymentNotificaiton(Context appContext) {
        this.data_pays = appContext.getSharedPreferences(storedDataName,Context.MODE_PRIVATE);
    }


    //jobids
    public void storedjobID(String jobid) {
        storeByKeyValue(storedUserIdKey, jobid);
    }

    private void storeByKeyValue(String storedUserIdKey, String jobid) {
        SharedPreferences.Editor editor = data_pays.edit();
        editor.putString(storedUserIdKey, jobid);
        editor.commit();
    }

    public String getStoredUserId(){
        return getStoredDataByKey(storedUserIdKey);
    }

    private String getStoredDataByKey(String storedUserIdKey) {
        return data_pays.getString(storedUserIdKey,"");
    }


    //current user ids
    public void storedjobID2(String userid) {
        storeByKeyValue2(storedUserIdKey2, userid);
    }

    private void storeByKeyValue2(String storedUserIdKey2, String userid) {
        SharedPreferences.Editor editor = data_pays.edit();
        editor.putString(storedUserIdKey2, userid);
        editor.commit();
    }

    public String getStoredUserId2(){
        return getStoredDataByKey2(storedUserIdKey2);
    }

    private String getStoredDataByKey2(String storedUserIdKey2) {
        return data_pays.getString(storedUserIdKey2,"");
    }

    //status of the payment

    public void storedjobID3(String status) {
        storeByKeyValue3(storedUserIdKey3, status);
    }

    private void storeByKeyValue3(String storedUserIdKey3, String status) {
        SharedPreferences.Editor editor = data_pays.edit();
        editor.putString(storedUserIdKey3, status);
        editor.commit();
    }

    public String getStoredUserId3(){
        return getStoredDataByKey3(storedUserIdKey3);
    }

    private String getStoredDataByKey3(String storedUserIdKey2) {
        return data_pays.getString(storedUserIdKey3,"");
    }


    //date of the payment
    public void storedjobID4(String date) {
        storeByKeyValue4(storedUserIdKey4, date);
    }

    private void storeByKeyValue4(String storedUserIdKey4, String date) {
        SharedPreferences.Editor editor = data_pays.edit();
        editor.putString(storedUserIdKey4, date);
        editor.commit();
    }

    public String getStoredUserId4(){
        return getStoredDataByKey4(storedUserIdKey4);
    }

    private String getStoredDataByKey4(String storedUserIdKey4) {
        return data_pays.getString(storedUserIdKey4,"");
    }

    public void clearStoredData(){
        data_pays.edit().clear().commit();
    }

}
