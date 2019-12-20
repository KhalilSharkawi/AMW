package com.MS.applications.UnlimitedServicesDriver.MyActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.MS.applications.UnlimitedServicesDriver.Activities.AdminMainActivity;
import com.MS.applications.UnlimitedServicesDriver.Activities.CustomersListActivity;
import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.Customer;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;

public class AddCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Add Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CustomersListActivity.class));
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer c = new Customer();
                c.setName(((EditText) findViewById(R.id.nameTxt)).getText().toString());
                c.setMobileNumber(((EditText) findViewById(R.id.MobileNumber)).getText().toString());
                c.setVehicleNumber(((EditText) findViewById(R.id.vehicleNumber)).getText().toString());
                c.setNationalNumber(((EditText) findViewById(R.id.nationalNumber)).getText().toString());
                c.setAddress(((EditText) findViewById(R.id.address)).getText().toString());
                c.setResidence(((EditText) findViewById(R.id.residence)).getText().toString());
                c.setCardNumber(((EditText) findViewById(R.id.cardNumber)).getText().toString());

                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("Name", c.getName());
                    jsonParam.put("MobileNumber", c.getMobileNumber());
                    jsonParam.put("VehicleNumber", c.getVehicleNumber());
                    jsonParam.put("NationalNumber", c.getNationalNumber());
                    jsonParam.put("Address", c.getAddress());
                    jsonParam.put("Residence", c.getResidence());
                    jsonParam.put("CardNumber", c.getCardNumber());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new SendGetJsonApi(AddCustomerActivity.this, "/api/api/customers/create", jsonParam,
                        new CallBackListener() {
                            @Override
                            public void onFinish(String resultjson) {
                                try {
                                    APIResponse<Boolean> userInfoAPIResponse = getResponse(resultjson, Boolean.class);
                                    if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success")) {
                                        startActivity(new Intent(getBaseContext(), CustomerListActivity.class));

                                    } else {
                                        String error_des = userInfoAPIResponse != null ? userInfoAPIResponse.getErrorDes() : null;
                                        if (!(error_des != null && error_des.equals(""))) {
                                            Snackbar.make(view, "Error", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        } else {
                                            Snackbar.make(view, error_des, Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }

                            @Override
                            public void onProgress(int process) {
                            }
                        }

                ).Execute();


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

}
