package com.example.aigentestapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.aigentestapp.activity.LoginActivity;

public class SessionManagerPref {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name

    public static final String PREF_NAME = "AigenTech";
    private static final String IS_LOGIN = "islogin";
    public static final String KEY_PWD = "password";
    public static final String KEY_EMAIL = "email";

    public SessionManagerPref(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }


    public boolean IsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getemail() {
        return pref.getString(KEY_EMAIL, "");
    }


    public String getPwd() {
        return pref.getString(KEY_PWD, "");
    }

    public void Logout() {
        editor.clear();
        editor.commit();
        System.out.println("session call user logout");
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }


    public void createLoginSession(String email, String pass) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_PWD, pass);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }
}