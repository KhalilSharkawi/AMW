package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.Region;
import com.MS.applications.UnlimitedServicesDriver.Models.UserInfo;
import com.MS.applications.UnlimitedServicesDriver.R;
import com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.MS.applications.UnlimitedServicesDriver.Api.APIConstants.REGISTER;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getRegisterParams;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.EMAIL;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.FCM_TOKEN;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.FIRST_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.IS_VERIFIED;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.LAST_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.TOKEN;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.USER_ID;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readSharedPreferenceFCMString;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.writeSharedPreferenceInt;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.writeSharedPreferenceString;


public class RegisterActivity extends GodFatherActivity implements AdapterView.OnItemSelectedListener {

    private EditText LastNameTxt, EmailTxt, PasswordTxt,PasswordConfirmTxt,MobNumberTxt;
    private TextView Reslog;
    private Spinner citiesSpinner;
    private static ArrayAdapter aa;
    private Region[] citiesArray;
    private int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(getString(R.string.register));
        getCities();
        LastNameTxt =  findViewById(R.id.last_name);
        EmailTxt =  findViewById(R.id.email);
        PasswordTxt =  findViewById(R.id.password);
        PasswordConfirmTxt =  findViewById(R.id.password_confirm);
        MobNumberTxt =  findViewById(R.id.mob_number);
        Reslog= findViewById(R.id.Reslogin);

        citiesSpinner =  findViewById(R.id.cities);
        List<String> names = new ArrayList<>();
        names.add("Loading");
        aa = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, names);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(aa);

    }

    private void getCities(){
        JSONObject jsonParams = new JSONObject();
        new SendGetJsonApi(this, "regions/all", jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson)
                    {
                        APIResponse apiResponse = JsonParser.getResponse(resultjson, Region[].class);
                        try {
                            if (apiResponse != null && apiResponse.getResult().equals("success"))
                            {
                                citiesArray = (Region[]) apiResponse.getContent();
                                List<String> names = new ArrayList<>();
                                names.add("Select Region");
                                for (Region aCitiesArray : citiesArray) {
                                    names.add(aCitiesArray.getName());
                                }
                                citiesSpinner.setOnItemSelectedListener(RegisterActivity.this);
                                aa = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,names);
                                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                citiesSpinner.setAdapter(aa);
                                cityId = -10;
                            }
                            else {
                                String error_des = apiResponse != null ? apiResponse.getErrorDes() : null;
                            }
                        }
                        catch ( Exception e)
                        {

                        }
                    }
                    @Override
                    public void onProgress(int process) {
                    }
                }
                , false).Execute();
    }

    public void RegisterBtnAction(View v)
    {
        hideKeyboard(this);
        Reslog.setText("");
        final String last_name = LastNameTxt.getText().toString().trim();
        final String email = EmailTxt.getText().toString().trim();
        final String password = PasswordTxt.getText().toString().trim();
        String password_confirm = PasswordConfirmTxt.getText().toString().trim();
        String mobile = MobNumberTxt.getText().toString().trim();
        if (last_name.equals("")) {
            LastNameTxt.setError(getString(R.string.last_name_required));LastNameTxt.requestFocus();  return;
        }
        if (email.equals("")) {
            EmailTxt.setError(getString(R.string.username_required));EmailTxt.requestFocus();  return;
        }
        if (password.equals("")) {
            PasswordTxt.setError(getString(R.string.password_required));PasswordTxt.requestFocus();  return;
        }
        if (password.length() < 6) {
            PasswordTxt.setError(getString(R.string.password_length));PasswordTxt.requestFocus();  return;
        }
        if (!password.equals(password_confirm)) {
            PasswordConfirmTxt.setError(getString(R.string.password_confirm_not_equal_password));PasswordConfirmTxt.requestFocus();  return;
        }
        if(cityId == -10){
            Reslog.setText("Region Required"); return;
        }
        int MainRegionId = cityId;
        JSONObject jsonParam = getRegisterParams(email, password, last_name, mobile, MainRegionId);
        new SendGetJsonApi(this, "people/register", jsonParam,
            new CallBackListener() {
                @Override
                public void onFinish(String resultjson)
                {
                    try {
                        APIResponse<UserInfo> userInfoAPIResponse = getResponse(resultjson, UserInfo.class);
                        if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success"))
                        {
                            UserInfo userInfo = userInfoAPIResponse.getContent();
                            SaveToSharedPreferences(userInfo);
                            startActivity(new Intent(getBaseContext() , MainActivity.class));
                            finish();
                        }
                        else
                        {
                            String error_des = userInfoAPIResponse != null ? userInfoAPIResponse.getErrorDes() : null;
                            if(!(error_des != null && error_des.equals(""))){
                                Reslog.setText(error_des);
                            }else
                            {
                                Reslog.setText(getString(R.string.fail_try_agian));
                            }
                        }
                    }
                    catch ( Exception e)
                    {
                        Reslog.setText(getString(R.string.fail_try_agian));
                    }
                }
                @Override
                public void onProgress(int process) {
                }
            }
        ).Execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spin = (Spinner) adapterView;
        if(i != 0) {
            if (spin.getId() == R.id.cities) {
                cityId = citiesArray[i - 1].getId();
            }

            Reslog.setText("");
        }
        else {
            Reslog.setText("All Fields are required");
        }
    }
    private void SaveToSharedPreferences(UserInfo userInfo) {
        writeSharedPreferenceInt(this, USER_ID, userInfo.getId());
        writeSharedPreferenceString(this, FIRST_NAME, userInfo.getName());
        writeSharedPreferenceString(this, LAST_NAME, String.valueOf(userInfo.isAdmin()));
        writeSharedPreferenceString(this, EMAIL, userInfo.getEmail());
        writeSharedPreferenceString(this, TOKEN, userInfo.getEmail());
        writeSharedPreferenceString(this, "ImageUrl", userInfo.getImageUrl());
        writeSharedPreferenceInt(this, IS_VERIFIED, userInfo.getMainRegionId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
