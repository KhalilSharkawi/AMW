package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.MS.applications.UnlimitedServicesDriver.MyActivities.CustomerListActivity;
import com.MS.applications.UnlimitedServicesDriver.MyActivities.ReportListActivity;
import com.MS.applications.UnlimitedServicesDriver.R;
import com.MS.applications.UnlimitedServicesDriver.Transactions.TransactionListActivity;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        SharedPreferences settings = getSharedPreferences("user", 0);
        String silent = settings.getString("username", "");
        Button customers = findViewById(R.id.btnCustomers);
        Button transaction = findViewById(R.id.add_customerbtn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        if (silent.equals("admin")) {
            customers.setVisibility(View.VISIBLE);
            transaction.setVisibility(View.VISIBLE);

        }
        if (silent.equals("emp")) {
            transaction.setVisibility(View.VISIBLE);
            customers.setVisibility(View.INVISIBLE);

        }
    }

    public void customers(View v) {
        try {
            startActivity(new Intent(getBaseContext(),
                    CustomerListActivity.class));
            finish();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void on_click_createCustomer(View v) {
        try {
            startActivity(new Intent(getBaseContext(),
                    TransactionListActivity.class));
            finish();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void report(View v) {
        try {
            startActivity(new Intent(getBaseContext(),
                    ReportListActivity.class));
            finish();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
