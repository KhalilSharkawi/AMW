package com.MS.applications.UnlimitedServicesDriver.Transactions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Api.CallBackListener;
import com.MS.applications.UnlimitedServicesDriver.Api.SendGetJsonApi;
import com.MS.applications.UnlimitedServicesDriver.Models.APIResponse;
import com.MS.applications.UnlimitedServicesDriver.Models.Transaction;
import com.MS.applications.UnlimitedServicesDriver.MyActivities.AddCustomerActivity;
import com.MS.applications.UnlimitedServicesDriver.R;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.MS.applications.UnlimitedServicesDriver.Utils.JsonParser.getResponse;

/**
 * An activity representing a list of Transactions. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TransactionDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TransactionListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static Map<String, Transaction> transactions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddTransactionActivity.class));

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.transaction_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.transaction_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        retrieve(recyclerView);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final TransactionListActivity mParentActivity;
        private final List<Transaction> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction item = (Transaction) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(TransactionDetailFragment.ARG_ITEM_ID, Integer.toString(item.getId()));
                    TransactionDetailFragment fragment = new TransactionDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.transaction_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TransactionDetailActivity.class);
                    intent.putExtra(TransactionDetailFragment.ARG_ITEM_ID, Integer.toString(item.getId()));

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(TransactionListActivity parent,
                                      List<Transaction> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(Integer.toString(mValues.get(position).getId()));
            holder.mContentView.setText((mValues.get(position).getDate().toString()) + mValues.get(position).getCustomerId());


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

        new SendGetJsonApi(this, "api/api/transactions/all",
                jsonParams,
                new CallBackListener() {
                    @Override
                    public void onFinish(String resultjson) {
                        try {
                            APIResponse apiResponse = getResponse(resultjson, Transaction[].class);
                            if (apiResponse != null && apiResponse.getResult().equals("success")) {
                                List<Transaction> s = (List<Transaction>) Arrays.asList((Transaction[]) apiResponse.getContent());
                                for (Transaction c : s ) {
                                    transactions.put(Integer.toString(c.getId()), c);
                                }
                                recyclerView.setAdapter(
                                        new TransactionListActivity.SimpleItemRecyclerViewAdapter(TransactionListActivity.this,
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
