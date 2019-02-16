package com.example.administrator.streetfood.Customer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerOrderNotificationFragment extends Fragment {


    private TextView totalValue;
    private Button completed, processing, shipping;
    private ListView orderListView;
    private CustomerOrderNotificationAdapter adapter;
    List<Order> orderList = new ArrayList<>();
    private String uuid, orderStatus, orderDate;
    private OrderServer orderServer;

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

        Bundle b = getArguments();
        uuid = b.getString("uuid");
        orderStatus = b.getString("orderStatus");
        orderDate = b.getString("orderDate");

        checkStatus();

        orderServer = new OrderServer(getContext());

        orderServer.getSelectedCustomerOrders(uuid, list -> {
            orderList.addAll(list);
            adapter = new CustomerOrderNotificationAdapter(getContext(), orderList);
            orderListView.setAdapter(adapter);
        });
        return v;
    }

    public void checkStatus() {
        switch (orderStatus) {
            case "1":
                shipping.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));
                break;
            case "2":
                completed.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));
                break;
            default:
                processing.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));
                break;
        }
    }

}
