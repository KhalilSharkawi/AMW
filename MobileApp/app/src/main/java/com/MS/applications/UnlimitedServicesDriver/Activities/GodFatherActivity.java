package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.ARABIC_LANG;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.ENGLISH_LANG;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readLanguage;

public class GodFatherActivity extends AppCompatActivity {

    public static String curr_lang = ENGLISH_LANG;

    @Override
    protected void attachBaseContext(Context context) {
        String lang =  readLanguage(context);
        String sys_lang = Locale.getDefault().getLanguage();
        if ((lang != null && lang.equals(ARABIC_LANG)) || (lang != null && lang.equals(ENGLISH_LANG))) curr_lang = lang;
        else  curr_lang = sys_lang;
        Configuration config = context.getResources().getConfiguration();
        Locale locale = new Locale(curr_lang);
        Locale.setDefault(locale);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
