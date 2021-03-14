package com.example.csci3130_project_group17;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;



public class PrefrenceUtil {

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime,String prefName){
        SharedPreferences sharedPreference = context.getSharedPreferences(prefName, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }
    public static boolean isFirstTimeAskingPermission(Context context, String permission,String prefName){
        return context.getSharedPreferences(prefName, MODE_PRIVATE).getBoolean(permission, true);
    }
}

