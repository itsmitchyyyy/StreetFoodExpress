package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.VendorActivityDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorCompletedOrdersFragment extends Fragment {


    private ListView completedListView;
    private VendorServer vendorServer;
    List<Order> orderList = new ArrayList<>();
    private VendorCompletedOrdersAdapter adapter;

    public VendorCompletedOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_vendor_completed_orders, container, false);

        completedListView = v.findViewById(R.id.completedListView);

        vendorServer = new VendorServer(getContext());

        vendorServer.viewShippingOrdersList("2", new VendorServer.VolleyCallBack() {
            @Override
            public void onOrdersQuery(List<Order> list) {
                orderList.addAll(list);
                adapter = new VendorCompletedOrdersAdapter(getContext(), orderList);
                completedListView.setAdapter(adapter);
            }

            @Override
            public void onVendorProductQuery(List<Product> list) {

            }
        });

        completedListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle b = new Bundle();
            b.putString("customerId", String.valueOf(orderList.get(position).getCustomerId()));
            b.putString("uuid", orderList.get(position).getOrderUuid());
            VendorCompletedOrdersListFragment vendorCompletedOrdersListFragment = new VendorCompletedOrdersListFragment();
            vendorCompletedOrdersListFragment.setArguments(b);
            ((VendorActivityDrawer) Objects.requireNonNull(getContext())).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.vendorContentFrame, vendorCompletedOrdersListFragment)
                    .commit();
        });

        return v;
    }

}
