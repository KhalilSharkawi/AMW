package com.MS.applications.UnlimitedServicesDriver.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.MS.applications.UnlimitedServicesDriver.R;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesHelper {

    public static final String APP_PREFS = "USDriverApp";
    public static final String APP_PREFS_FCM = "USDriverFCM";

    private static final String _LANG = "com.MS.applications.UnlimitedServicesDriver._lang";
    private static final String LANG = "com.MS.applications.UnlimitedServicesDriver.lang";

    public static final String TOKEN = "com.MS.applications.UnlimitedServicesDriver.User.token";
    public static final String FIRST_NAME  ="com.MS.applications.UnlimitedServicesDriver.User.first_name";
    public static final String LAST_NAME  ="com.MS.applications.UnlimitedServicesDriver.User.last_name";
    public static final String USER_ID  ="com.MS.applications.UnlimitedServicesDriver.User.user_id";
    public static final String EMAIL  ="com.MS.applications.UnlimitedServicesDriver.User.email";
    public static final String IS_VERIFIED  ="com.MS.applications.UnlimitedServicesDriver.User.is_verified";

    public static final String FCM_TOKEN  ="com.MS.applications.UnlimitedServicesDriver.User.FCM_token";
    public static final String IS_FCM_TOKEN_SENT = "com.MS.applications.UnlimitedServicesDriver.User.FCM_token.sent";

    public static final String IS_DRIVER_ONLINE  ="com.MS.applications.UnlimitedServicesDriver.Service.is_online";
    public static final String IS_ALREADY_STARTED  ="com.MS.applications.UnlimitedServicesDriver.Service.already_started";


    public static String readLanguage(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(APP_PREFS + _LANG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANG, activity.getString(R.string.ENGLISH_LANG));
    }

    public static String readLanguage(Context activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(APP_PREFS + _LANG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LANG, activity.getString(R.string.ENGLISH_LANG));
    }

    public static void saveLanguage(Activity activity, String curr_lang){
        SharedPreferences sharedPrefereSt = activity.getSharedPreferences(APP_PREFS + _LANG, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefereSt.edit();
        editor.putString(LANG, curr_lang);
        editor.apply();
    }

    //Read from Shared Preferences (INTEGER)
    public static int readSharedPreferenceInt(Activity activity, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static int readSharedPreferenceInt(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    //Read from Shared Preferences (String)
    public static String readSharedPreferenceString(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    //Read from Shared Preferences (String)
    public static String readSharedPreferenceFCMString(Activity activity, String key){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(APP_PREFS_FCM, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void writeSharedPreferenceFCMString(Context context, String key , String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS_FCM, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeSharedPreferenceFCMInt(Context context, String key , int value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFS_FCM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //write shared preferences in integer
    public static void writeSharedPreferenceInt(Activity activity, String key , int value) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //write shared preferences in String
    public static void writeSharedPreferenceString(Activity activity, String key , String value ){
        SharedPreferences sharedPrefereSt = activity.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefereSt.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clearUserSharedPreferences(Activity activity){
        activity.getSharedPreferences(APP_PREFS, MODE_PRIVATE).edit().clear().apply();
    }
}
