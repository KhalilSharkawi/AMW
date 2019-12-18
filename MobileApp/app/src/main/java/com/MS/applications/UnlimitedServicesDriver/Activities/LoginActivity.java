package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.ResetPass;
import com.MS.applications.UnlimitedServicesDriver.Models.UserInfo;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONObject;

import static com.MS.applications.UnlimitedServicesDriver.Api.APIConstants.RESET_PASS;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getLoginParams;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResetPassParams;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.EMAIL;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.FCM_TOKEN;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.FIRST_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.IS_VERIFIED;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.LAST_NAME;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.TOKEN;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.USER_ID;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readSharedPreferenceFCMString;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readSharedPreferenceInt;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.readSharedPreferenceString;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.writeSharedPreferenceInt;
import static com.MS.applications.UnlimitedServicesDriver.Utils.SharedPreferencesHelper.writeSharedPreferenceString;

public class LoginActivity extends GodFatherActivity {

    private EditText EmailTxt, PasswordTxt;
    private TextView Reslog;
    private boolean isFromRegister = false;
    private String email = "";
    private String password = "";
    public int ActiveStatus = 0, UserId = 0;
    public String Token = "", FCM_Token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserId = readSharedPreferenceInt(this, USER_ID);
        Token = readSharedPreferenceString(this, TOKEN);
        String isAdmin = readSharedPreferenceString(this, LAST_NAME);
        if(!Token.equals("") && isAdmin.equals("True"))
        {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
        }
        else if(!Token.equals("") && !isAdmin.equals("True"))
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else
        {
            setContentView(R.layout.activity_login);
            EmailTxt = findViewById(R.id.username);
            PasswordTxt = findViewById(R.id.password);
            Reslog = findViewById(R.id.Reslogin);
        }
        try{
            email = getIntent().getStringExtra("email");
            password = getIntent().getStringExtra("password");
            if(!email.equals("") && !password.equals("")){
                isFromRegister = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(isFromRegister){
            EmailTxt.setText(email);
            PasswordTxt.setText(password);
        }
    }
    public void Guest(View v){
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
    public void LoginBtnAction(View v)
    {
        hideKeyboard(this);
        Reslog.setText("");
        String email = EmailTxt.getText().toString().trim();
        String password = PasswordTxt.getText().toString().trim();
        if (email.equals("")) {
            EmailTxt.setError(getString(R.string.username_required));EmailTxt.requestFocus();  return;
        }
        if (password.equals("")) {
            PasswordTxt.setError(getString(R.string.password_required));PasswordTxt.requestFocus();  return;
        }
        FCM_Token = readSharedPreferenceFCMString(this, FCM_TOKEN);
        JSONObject jsonParams = getLoginParams(email, password, FCM_Token);
        new SendGetJsonApi(this,"/mws/api/account/login?username="+email+"&password="+password,jsonParams,
            new CallBackListener() {
                @Override
                public void onFinish(String resultjson)
                {
                    try { APIResponse<Boolean> userInfoAPIResponse = getResponse(resultjson, Boolean.class);
                        if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success"))
                        {
                            //boolean userInfo = userInfoAPIResponse.getContent();
                            //SaveToSharedPreferences(userInfo);
                            if(email.equals("admin")) {
                                getIntent().putExtra("user",email);
                                startActivity(new Intent(getBaseContext(), AdminMainActivity.class));

                                finish();
                            }
                            else if(email.equals("emp")){
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            }else {
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            }
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

    private void SaveToSharedPreferences(UserInfo userInfo) {
        writeSharedPreferenceInt(this, USER_ID, userInfo.getId());
        writeSharedPreferenceString(this, FIRST_NAME, userInfo.getName());
        writeSharedPreferenceString(this, LAST_NAME, String.valueOf(userInfo.isAdmin()));
        writeSharedPreferenceString(this, EMAIL, userInfo.getEmail());
        writeSharedPreferenceString(this, TOKEN, userInfo.getEmail());
        writeSharedPreferenceString(this, "ImageUrl", userInfo.getImageUrl());
        writeSharedPreferenceInt(this, IS_VERIFIED, userInfo.getMainRegionId());
    }

    public void RegisterBtnAction(View v)
    {
        startActivity(new Intent(getBaseContext() , RegisterActivity.class));
    }

    public void ResetPasswordBtnAction(View v)
    {
        hideKeyboard(this);
        Reslog.setText("");
        final String email = EmailTxt.getText().toString().trim();
        if (email.equals("")) {
            EmailTxt.setError(getString(R.string.username_required));
            EmailTxt.requestFocus();
            return;
        }
        JSONObject jsonParams = getResetPassParams(email);
        new SendGetJsonApi(this, RESET_PASS, jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson)
                    {
                        try {
                            APIResponse<ResetPass> userInfoAPIResponse = getResponse(resultjson, ResetPass.class);
                            if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success"))
                            {
//                                Intent resetIntent = new Intent(getBaseContext(), ResetPasswordActivity.class);
//                                resetIntent.putExtra("email", email);
//                                startActivity(resetIntent);
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
}
