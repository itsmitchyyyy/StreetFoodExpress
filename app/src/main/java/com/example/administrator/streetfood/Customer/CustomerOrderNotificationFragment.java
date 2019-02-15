package com.example.administrator.streetfood.Customer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.streetfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerOrderNotificationFragment extends Fragment {


    private TextView totalValue;
    private Button completed, processing, shipping;
    private ListView orderListView;

    public CustomerOrderNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customer_notification_order, container, false);
        processing = v.findViewById(R.id.button14);
        shipping = v.findViewById(R.id.button15);
        completed = v.findViewById(R.id.button16);
        orderListView = v.findViewById(R.id.orderListView);
        totalValue = v.findViewById(R.id.totalValue);

        return v;
    }

}
