package com.MS.applications.UnlimitedServicesDriver.Transactions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.Customer;
import com.MS.applications.UnlimitedServicesDriver.Models.Transaction;
import com.MS.applications.UnlimitedServicesDriver.MyActivities.CustomerListActivity;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;

public class AddTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Transaction c = new Transaction();
                    c.setCustomerId((((EditText) findViewById(R.id.Customer)).getText().toString()));
                    c.setConvertExceedingAmountToFree(((Switch) findViewById(R.id.ConvertExceedingAmountToFree)).isChecked());
                    String date =String.format ("%s/%s/%s",
                            ((DatePicker) findViewById(R.id.Date)).getYear(),
                            ((DatePicker) findViewById(R.id.Date)).getMonth(),
                            ((DatePicker) findViewById(R.id.Date)).getDayOfMonth()
                            );

                    c.setDate(date);
                    c.setPetrolAmount(Integer.parseInt(((EditText) findViewById(R.id.PetrolAmount)).getText().toString()));


                    JSONObject jsonParam = new JSONObject();

                    jsonParam.put("CustomerId", c.getCustomerId());
                    jsonParam.put("ConvertExceedingAmountToFree", c.isConvertExceedingAmountToFree());
                    jsonParam.put("Date", c.getDate());
                    jsonParam.put("PetrolAmount", c.getPetrolAmount());
                    jsonParam.put("FreeAmount", 0);


                    new SendGetJsonApi(AddTransactionActivity.this, "/api/api/transactions/create", jsonParam,
                            new CallBackListener() {
                                @Override
                                public void onFinish(String resultjson) {
                                    try {
                                        APIResponse<Boolean> userInfoAPIResponse = getResponse(resultjson, Boolean.class);
                                        if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success")) {
                                            startActivity(new Intent(getBaseContext(), TransactionListActivity.class));

                                        } else {
                                            String error_des = userInfoAPIResponse != null ? userInfoAPIResponse.getErrorDes() : null;
                                            if (!(error_des != null && error_des.equals(""))) {
                                                Snackbar.make(view, error_des, Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            } else {
                                                Snackbar.make(view, "Error", Snackbar.LENGTH_LONG)
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

                } catch (Exception e) {
                    Snackbar.make(view, "Invalid Input", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

}
