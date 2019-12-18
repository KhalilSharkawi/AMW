package com.MS.applications.UnlimitedServicesDriver.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.Customer;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;

public class CustomersListActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayAdapter aAdapter;
    Customer[] customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customers_list_layout);
        mListView = (ListView) findViewById(R.id.list_dynamic);
        retrieve();
        aAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                //String value= (String) aAdapter.getItem(position);
                //Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();

            }
        });

    }


    //RETRIEVE
    private void retrieve() {

        JSONObject jsonParams = new JSONObject();

        new SendGetJsonApi(this, "api/api/customers/all",
                jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson) {
                        try {
                            APIResponse apiResponse = getResponse(resultjson, Customer[].class);
                            if (apiResponse != null && apiResponse.getResult().equals("success")) {
                                customers = (Customer[]) apiResponse.getContent();
                                List<Customer> s = (List<Customer>)Arrays.asList(customers);
                                for (Customer a :s ) {
                                  aAdapter.add( a.getName() + "   "+a.getMobileNumber());
                                }
                                mListView.setAdapter(aAdapter);
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


//        DBAdapter db=new DBAdapter(this);
//
//        //OPEN
//        db.openDB();
//
//        players.clear();
//
//        //SELECT
//        Cursor c=db.getAllPlayers();
//
//        //LOOP THRU THE DATA ADDING TO ARRAYLIST
//        while (c.moveToNext())
//        {
//            int id=c.getInt(0);
//            String name=c.getString(1);
//            String pos=c.getString(2);
//
//            //CREATE PLAYER
//            Player p=new Player(name,pos,id);
//
//            //ADD TO PLAYERS
//            players.add(p);
//        }
//
//        //SET ADAPTER TO RV
//        if(!(players.size()<1))
//        {
//            rv.setAdapter(adapter);
//        }

    }


}
