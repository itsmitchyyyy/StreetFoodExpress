package com.example.administrator.streetfood.Customer;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.PusherConfig;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private PusherConfig pusherConfig;

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

        pusherConfig = new PusherConfig(getContext());

        Bundle b = getArguments();
        assert b != null;
        uuid = b.getString("uuid");
        orderStatus = b.getString("orderStatus");
        orderDate = b.getString("orderDate");

        checkStatus(orderStatus);

        orderServer = new OrderServer(getContext());

        orderServer.getSelectedCustomerOrders(uuid, new OrderServer.VolleyCallback() {
            @Override
            public void onCustomerOrderListQuery(List<Order> list) {
                orderList.addAll(list);
                adapter = new CustomerOrderNotificationAdapter(getContext(), orderList);
                orderListView.setAdapter(adapter);
            }

            @Override
            public void onUpdateStatus(boolean status) {

            }
        });

        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher("eb6ffe9cf06d0c047e3a", options);

        Channel channel = pusher.subscribe("streetfood");

        channel.bind("order-status", (channelName, eventName, data) -> {
            handler.post(() -> {
                String[] status = data.replace("[", "")
                        .replace("]", "")
                        .replace("\"", "")
                        .split(",");
                orderStatus = status[0];
                checkStatus(orderStatus);
            });
        });

        pusher.connect();

        return v;
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public void checkStatus(String status) {
        switch (status) {
            case "0":
                processing.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));
                shipping.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                completed.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                break;
            case "1":
                shipping.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));
                processing.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                completed.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                break;
            case "2":
                completed.setBackground(getResources().getDrawable(R.drawable.rounded_button_active));
                shipping.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                processing.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                break;
        }
    }

}
