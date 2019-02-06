package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.CustomProgressHorizontalDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorOrderListFragment extends Fragment {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Order> orderList = new ArrayList<>();
    private VendorListOrderAdapter orderListAdapter;
    private VendorServer vendorServer;

    public VendorOrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vendorOrderView =  inflater.inflate(R.layout.fragment_vendor_order_list, container, false);

        swipeRefreshLayout = vendorOrderView.findViewById(R.id.vendorListViewRefresh);
        listView = vendorOrderView.findViewById(R.id.vendorListView);

        vendorServer = new VendorServer(getContext());

        viewOrders();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewOrders();
            swipeRefreshLayout.setRefreshing(false);
        });

        return vendorOrderView;
    }

    public void viewOrders() {
        vendorServer.viewOrders(list -> {
            orderList.addAll(list);
            orderListAdapter = new VendorListOrderAdapter(getContext(), orderList);
            listView.setAdapter(orderListAdapter);
        });
    }

}
