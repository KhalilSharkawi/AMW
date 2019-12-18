package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.Order;
import com.MS.applications.UnlimitedServicesDriver.R;
import com.MS.applications.UnlimitedServicesDriver.Services.LocationMonitoringService;
import com.MS.applications.UnlimitedServicesDriver.Utils.CustomDialogClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import static com.MS.applications.UnlimitedServicesDriver.Api.APIConstants.LOGOUT;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.ARABIC_LANG;
import static com.MS.applications.UnlimitedServicesDriver.Utils.AppConstants.ENGLISH_LANG;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getTokenParam;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.FIRST_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.IS_ALREADY_STARTED;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.IS_DRIVER_ONLINE;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.LAST_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.TOKEN;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.clearUserSharedPreferences;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readLanguage;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readSharedPreferenceString;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.saveLanguage;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.writeSharedPreferenceInt;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.writeSharedPreferenceString;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    public static String curr_lang = ENGLISH_LANG;
    public static android.support.v7.widget.SwitchCompat onlineSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        }
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView customerNameTxt = headerLayout.findViewById(R.id.driver_name);

        String token = readSharedPreferenceString(this, TOKEN);
        if(token != null && !token.equals("")) {
            String customer;
            customer = String.format("%s %s", readSharedPreferenceString(this, FIRST_NAME), readSharedPreferenceString(this, LAST_NAME));
            if (!customer.equals("")) {
                customerNameTxt.setText(customer);
            }
        }
        onlineSwitch = findViewById(R.id.online_switch);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
//            case R.id.orders:
//                Intent OrdersIntent = new Intent(this, OrdersActivity.class);
//                OrdersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(OrdersIntent);
//                return true;
//            case R.id.lang:
//                ChangeLanguage();
//                return true;
            case R.id.logout:
                final CustomDialogClass cdd = new CustomDialogClass(this, "log_out");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(cdd.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                cdd.show();
                cdd.yes.setOnClickListener(v -> {
                    logOut();
                    cdd.dismiss();
                });
                cdd.no.setOnClickListener(v -> cdd.dismiss());
                return true;
            default:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }

    public void ChangeLanguage()
    {
        if (curr_lang.equals(ARABIC_LANG))
        {
            curr_lang = ENGLISH_LANG;
        }
        else
        {
            curr_lang = ARABIC_LANG;
        }
        saveLanguage(this, curr_lang);
        finish();
        startActivity(getIntent());
    }

    private void logOut() {
        String token = readSharedPreferenceString(this, TOKEN);
        JSONObject jsonParams = getTokenParam(token);
        new SendGetJsonApi(this, LOGOUT, jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson)
                    {
                        try {
                            CheckOffline();
                        }
                        catch (Exception ignored){}
                    }
                    @Override
                    public void onProgress(int process) {
                    }
                }
                , false).Execute();
        writeSharedPreferenceString(this, IS_ALREADY_STARTED, String.valueOf(false));
        stopService(new Intent(BaseActivity.this, LocationMonitoringService.class));
        writeSharedPreferenceInt(this, IS_DRIVER_ONLINE, 0);
        clearUserSharedPreferences(this);
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void CheckOffline()
    {
        String token = readSharedPreferenceString(this, TOKEN);
        JSONObject jsonParam = new JSONObject();
        try {
//            jsonParam.put("lat", driverLocation.latitude);
//            jsonParam.put("lng", driverLocation.longitude);
//            jsonParam.put("location", driverLocationName);
            jsonParam.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new SendGetJsonApi(this, "checkOut", jsonParam,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson)
                    {
                        try {
                            APIResponse<Order[]> userInfoAPIResponse = getResponse(resultjson, Order[].class);
                            if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success"))
                            {
                                Toast.makeText(BaseActivity.this, getString(R.string.offline_mode), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String error_des = userInfoAPIResponse != null ? userInfoAPIResponse.getErrorDes() : null;
                                if(!(error_des != null && error_des.equals(""))){
                                    Toast.makeText(BaseActivity.this, error_des, Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    Toast.makeText(BaseActivity.this, getString(R.string.fail_try_agian), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch ( Exception e)
                        {
                            Toast.makeText(BaseActivity.this, getString(R.string.fail_try_agian), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onProgress(int process) {
                    }
                }
        , false).Execute();
    }
}
