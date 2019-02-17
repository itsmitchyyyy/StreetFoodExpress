package com.example.administrator.streetfood.Customer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.streetfood.ActivityDrawer;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.Server;
import com.example.administrator.streetfood.Shared.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerOrderList extends Fragment {


    private ListView listView;
    private OrderServer orderServer;
    private Session session;
    List<Order> orders = new ArrayList<>();
    private CustomerOrderListAdapter adapter;


    public CustomerOrderList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_customer_order_list, container, false);

        listView = v.findViewById(R.id._orderList);

        orderServer = new OrderServer(getContext());
        session = new Session(getContext(), Server.accountPreferences);

        orderServer.customerOrderList(session.getId(), new OrderServer.VolleyCallback() {
            @Override
            public void onCustomerOrderListQuery(List<Order> list) {
                orders.addAll(list);
                adapter = new CustomerOrderListAdapter(getContext(), orders);
                listView.setAdapter(adapter);
            }

            @Override
            public void onUpdateStatus(boolean status) {

            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle b = new Bundle();
            b.putString("uuid", orders.get(position).getOrderUuid());
            b.putString("orderDate", orders.get(position).getOrderDate());
            b.putString("orderStatus", orders.get(position).getOrderStatus());
            CustomerOrderNotificationFragment customerOrderNotificationFragment = new CustomerOrderNotificationFragment();
            customerOrderNotificationFragment.setArguments(b);
            ((ActivityDrawer)getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentFrame, customerOrderNotificationFragment)
                    .commit();
        });

        return v;
    }
}
