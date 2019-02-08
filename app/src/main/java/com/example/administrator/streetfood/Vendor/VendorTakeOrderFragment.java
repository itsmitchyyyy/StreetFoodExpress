package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorTakeOrderFragment extends Fragment {


    private ListView listView;
    private SwipeRefreshLayout swipeToRefresh;
    private VendorTakeOrderAdapter takeOrderAdapter;
    ArrayList<Order> ordersList = new ArrayList<>();
    private VendorServer vendorServer;
    private int customerId;
    private TextView customerName, orderDate;
    private String cName, oDate;

    public VendorTakeOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor_take_order, container, false);

        listView = view.findViewById(R.id.listView);
        swipeToRefresh = view.findViewById(R.id.swipeToRefresh);
        customerName = view.findViewById(R.id.textView17);
        orderDate = view.findViewById(R.id.textView18);

        vendorServer = new VendorServer(getContext());

        getFragmentArguments();
        customerOrders();

        swipeToRefresh.setOnRefreshListener(() -> {
            customerOrders();
            swipeToRefresh.setRefreshing(false);
        });

        return view;
    }

    public void customerOrders() {

        vendorServer.viewCustomerOrder(customerId, list -> {
            ordersList.addAll(list);
            takeOrderAdapter = new VendorTakeOrderAdapter(getContext(), ordersList);
            listView.setAdapter(takeOrderAdapter);
        });
    }

    public void getFragmentArguments() {
        customerId = getArguments() != null ? getArguments().getInt("id") : 0;
        cName = getArguments().getString("name");
        oDate = getArguments().getString("orderDate");

        customerName.setText(cName);
        orderDate.setText(oDate);
    }
}
