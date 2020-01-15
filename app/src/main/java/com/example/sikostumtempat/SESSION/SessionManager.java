package com.example.sikostumtempat.SESSION;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.sikostumtempat.BerandaActivity;
import com.example.sikostumtempat.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static String TAG = SessionManager.class.getSimpleName();

    int PRIVATE_MODE= 0;
    private static final String PREF_NAME = "session";
    private static final String PREF_USERNAME = "USERNAME";

    private static final String IS_LOGIN = "IsLoggedIn";
    public final String KEY_ID = "id_user";
    public final String KEY_NAMA = "nama";
    public final String KEY_NOHP ="no_hp";
    public final String KEY_PASSWORD = "password";
    public final String KEY_LEVEL = "level";
    public final String KEY_USERNAME = "username";
    public final String KEY_IMAGE = "foto_identitas";
    public final String KEY_ALAMAT = "alamat";
    public final String KEY_EMAIL = "email";
    public final String KEY_IDN = "id_identitas";


    //constructor
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(IS_LOGIN, isLoggedIn);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    //CREATE LOGIN SESSION
    public void createLoginSession(String id_user, String nama,String no_hp, String password, String level, String username,
    String foto_identitas, String alamat,String email, String id_identitas){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id_user);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_NOHP, no_hp);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_LEVEL, level);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_IMAGE,foto_identitas);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_IDN, id_identitas);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }
    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, BerandaActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }
    public HashMap<String, String> getUserDetail(){
        HashMap<String,String> user= new HashMap<String, String>();
        user.put(KEY_ID, sharedPreferences.getString(KEY_ID,null));
        user.put(KEY_NAMA, sharedPreferences.getString(KEY_NAMA,null));
        user.put(KEY_NOHP,sharedPreferences.getString(KEY_NOHP, null));
        user.put(KEY_ALAMAT, sharedPreferences.getString(KEY_ALAMAT, null));
        user.put(KEY_EMAIL,sharedPreferences.getString(KEY_EMAIL, null));
        user.put(KEY_IDN, sharedPreferences.getString(KEY_IDN, null));

        return user;
    }
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public static SharedPreferences getSharedPreference(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public static void setUsername(Context ctx, String username)
    {
        SharedPreferences.Editor editor = getSharedPreference(ctx).edit();
        editor.putString(PREF_USERNAME, username);
        editor.commit();

    }
    public static String getUsername(Context ctx)
    {
        return getSharedPreference(ctx).getString(PREF_USERNAME,"");
    }
    public void logutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
