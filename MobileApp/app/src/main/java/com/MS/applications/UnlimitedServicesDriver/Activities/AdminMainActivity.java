package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.MS.applications.UnlimitedServicesDriver.MyActivities.CustomerListActivity;
import com.MS.applications.UnlimitedServicesDriver.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
    }

    public void customers(View v){
        try {
            startActivity(new Intent(getBaseContext(),
                    CustomerListActivity.class));
            finish();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void on_click_createCustomer(View v){

    }
}
