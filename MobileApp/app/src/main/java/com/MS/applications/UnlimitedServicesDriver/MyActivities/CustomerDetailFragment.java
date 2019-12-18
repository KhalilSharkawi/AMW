package com.MS.applications.UnlimitedServicesDriver.MyActivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MS.applications.UnlimitedServicesDriver.Models.Customer;
import com.MS.applications.UnlimitedServicesDriver.R;

/**
 * A fragment representing a single Customer detail screen.
 * This fragment is either contained in a {@link CustomerListActivity}
 * in two-pane mode (on tablets) or a {@link CustomerDetailActivity}
 * on handsets.
 */
public class CustomerDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Customer mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = CustomerListActivity.customers.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customer_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            String detail = String.format("Address: %s \nMobile: %s \nResidence: %s \nNationalNumber: %s" +
                            "\n CardNumber: %s \nVehicleNumber: %s", mItem.getAddress(),
                    mItem.getMobileNumber(), mItem.getResidence(), mItem.getNationalNumber(), mItem.getCardNumber()
                    , mItem.getVehicleNumber());
            ((TextView) rootView.findViewById(R.id.customer_detail)).setText(detail);
        }

        return rootView;
    }
}
