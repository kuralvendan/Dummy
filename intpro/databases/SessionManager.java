package com.binary2quantumtechbase.andapp.intpro.databases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.binary2quantumtechbase.andapp.intpro.First_activity;
import com.binary2quantumtechbase.andapp.intpro.Login;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "NTC";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String IS_FILLDATA = "isfilldata";

    public static final String KEY_ID = "id";

    public static final String KEY_NAME = "name";

    public static final String KEY_MOBILE = "mobile";

    public static final String KEY_EMAIL = "email";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String id, String name, String mobile, String email) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_EMAIL, email);


        editor.commit();
    }



      /**
       * Get stored session data
       */
    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, null));

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));

        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        return user;
    }


    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        } else {
            // user is logged in redirect him to Login Activity
            Intent i = new Intent(_context, First_activity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    /**
     * Clear session details
     */
/*    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, signin.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);


    }

    public void logoutUser2(String id) {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
if(id == "1")
{

    Intent i = new Intent(_context, AdminMain.class);
    // Closing all the Activities
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    // Add new Flag to start new Activity
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    // Staring Login Activity
    _context.startActivity(i);


}else
{

    Intent i = new Intent(_context, MainActivity.class);
    // Closing all the Activities
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    // Add new Flag to start new Activity
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    // Staring Login Activity
    _context.startActivity(i);

}


    } */

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isFillData() {
        return pref.getBoolean(IS_FILLDATA, false);
    }


    public void createdate(String startdate, String enddate) {

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void createpurpose(String purpose, String custname) {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

}
