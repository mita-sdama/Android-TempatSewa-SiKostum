package com.example.sikostumtempat.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.sikostumtempat.Utils.PreferencesUtility.LOGGED_IN_TS;
import static com.example.sikostumtempat.Utils.PreferencesUtility.id;


public class SaveSharedPreferences {
    static String nama;
    static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void setLoggedInTS(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_TS, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatusTS(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_TS, false);
    }


    public static void setId(Context context, String name) {
        nama = name;
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(id, name);
        editor.apply();
    }
    public static String getId(Context context) {
        return getPreferences(context).getString(id, nama);
    }
}
