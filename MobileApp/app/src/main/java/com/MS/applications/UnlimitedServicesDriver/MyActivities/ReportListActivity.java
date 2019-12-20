package com.MS.applications.UnlimitedServicesDriver.MyActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Activities.AdminMainActivity;
import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.CustomerReport;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;

/**
 * An activity representing a list of Reports. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReportDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ReportListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static Map<String, CustomerReport> customers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        toolbar.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (findViewById(R.id.report_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.report_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        retrieve(recyclerView);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ReportListActivity mParentActivity;
        private final List<CustomerReport> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerReport item = (CustomerReport) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ReportDetailFragment.ARG_ITEM_ID, item.getName());
                    ReportDetailFragment fragment = new ReportDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.report_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ReportDetailActivity.class);
                    intent.putExtra(ReportDetailFragment.ARG_ITEM_ID, item.getName());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ReportListActivity parent,
                                      List<CustomerReport> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getName());
            String result = String.format("VehicleNumber: %s\n TotalAmount: %s", mValues.get(position).getVehicleNumber()
                    , mValues.get(position).getTotalAmount());
            holder.mContentView.setText(result);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    private void retrieve(RecyclerView recyclerView) {

        JSONObject jsonParams = new JSONObject();

        new SendGetJsonApi(this, "api/api/customers/report",
                jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson) {
                        try {
                            APIResponse apiResponse = getResponse(resultjson, CustomerReport[].class);
                            if (apiResponse != null && apiResponse.getResult().equals("success")) {
                                List<CustomerReport> s = (List<CustomerReport>) Arrays.asList((CustomerReport[]) apiResponse.getContent());
                                for (CustomerReport c : s) {
                                    customers.put(c.getName(), c);
                                }
                                recyclerView.setAdapter(
                                        new ReportListActivity.SimpleItemRecyclerViewAdapter(ReportListActivity.this,
                                                s,
                                                mTwoPane));
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
