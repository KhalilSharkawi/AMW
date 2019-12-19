package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.CustomerInfo;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONObject;

import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getLoginParams;
import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;

public class MainActivity extends GodFatherActivity {

//    private int UserId;
//    private TextView name, number, Month, Year, date, south, north, Coastal_region, Eastern_Region, Northern_Region, Lebanon, comm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String email = getIntent().getStringExtra("email");
//        UserId = readSharedPreferenceInt(this, USER_ID);
//        String Name = readSharedPreferenceString(this, FIRST_NAME);
//        number = findViewById(R.id._id);
//        name = findViewById(R.id.name);
//        Month = findViewById(R.id.Month);
//        Year = findViewById(R.id.Year);
//        comm = findViewById(R.id.Monthly_commission);
//        getCommition();
//        number.setText(UserId + "");
//        name.setText(Name + "");
//        Calendar cal = Calendar.getInstance();
//        String x = new SimpleDateFormat("mm").format(cal.getTime());
//        String y = new SimpleDateFormat("yy").format(cal.getTime());
//        int month = Integer.parseInt(x);
//        int year = Integer.parseInt(y);
//        Month.setText(month + "");
//        Year.setText(year + "");
    }

    public void viewInfo(View v) {
        hideKeyboard(this);
        String cardNumber = ((EditText) findViewById(R.id.editText_cardNumber)).getText().toString().trim();
        TextView _5days = findViewById(R.id.textView_5DaysAllownce);
        TextView _monthly = findViewById(R.id.textView_30DaysAllownce);
        TextView free = findViewById(R.id.textView_free);
        JSONObject jsonParams = getLoginParams("", "", null);

        new SendGetJsonApi(this, "api/api/customers/info?cardnumber=" + cardNumber,
                jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson) {
                        try {
                            APIResponse<CustomerInfo> userInfoAPIResponse = getResponse(resultjson, CustomerInfo.class);
                            if (userInfoAPIResponse != null && userInfoAPIResponse.getResult().equals("success")) {
                                CustomerInfo ci = userInfoAPIResponse.getContent();
                                _5days.setText(ci.get_5daysAllownce() + " L");
                                _monthly.setText(ci.get_monthlyAllownce() + " L");
                                free.setText(ci.get_free() + " L");
                            } else {
                                TextView error = findViewById(R.id.textView_errorMsg);
                                error.setText(userInfoAPIResponse.getErrorDes());
                            }
                        } catch (Exception e) {
                            TextView error = findViewById(R.id.textView_errorMsg);
                            error.setText(e.getMessage());
                        }
                    }

                    @Override
                    public void onProgress(int process) {
                    }
                }

        ).Execute();

    }
//    private void getCommition() {
//        Calendar cal = Calendar.getInstance();
//        String x = new SimpleDateFormat("mm").format(cal.getTime());
//        String y = new SimpleDateFormat("yy").format(cal.getTime());
//        int month = Integer.parseInt(x);
//        int year = Integer.parseInt(y);
//        JSONObject jsonParams = new JSONObject();
//        try {
//            jsonParams.put("id", UserId);
//            jsonParams.put("month", month);
//            jsonParams.put("year", year);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        new SendGetJsonApi(this, "sales/one", jsonParams,
//                new CallBackListener() {
//                    @Override
//                    public void onFinish(String resultjson) {
//                        APIResponse apiResponse = JsonParser.getResponse(resultjson, double.class);
//                        try {
//                            if (apiResponse != null && apiResponse.getResult().equals("success")) {
//                                double commition = (double) apiResponse.getContent();
//                                comm.setText(commition + "");
//                            } else {
//                                String error_des = apiResponse != null ? apiResponse.getErrorDes() : null;
//                            }
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onProgress(int process) {
//                    }
//                }
//                , false).Execute();
//    }
}
