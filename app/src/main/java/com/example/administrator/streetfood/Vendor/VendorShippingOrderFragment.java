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
public class VendorShippingOrderFragment extends Fragment {


    private ListView shippingListView;
    private VendorServer vendorServer;
    List<Order> orderList = new ArrayList<>();
    private VendorShippingOrderAdapter adapter;

    public VendorShippingOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vendor_shipping_order, container, false);

        shippingListView = v.findViewById(R.id.shippingListView);

        vendorServer = new VendorServer(getContext());

        vendorServer.viewShippingOrdersList("1", new VendorServer.VolleyCallBack() {
            @Override
            public void onOrdersQuery(List<Order> list) {
                orderList.addAll(list);
                adapter = new VendorShippingOrderAdapter(getContext(), orderList);
                shippingListView.setAdapter(adapter);
            }

            @Override
            public void onVendorProductQuery(List<Product> list) {

            }
        });

        shippingListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle b = new Bundle();
            b.putString("customerId", String.valueOf(orderList.get(position).getCustomerId()));
            b.putString("uuid", orderList.get(position).getOrderUuid());
            VendorShippingOrderListFragment vendorShippingOrderListFragment = new VendorShippingOrderListFragment();
            vendorShippingOrderListFragment.setArguments(b);
            ((VendorActivityDrawer) Objects.requireNonNull(getContext())).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.vendorContentFrame, vendorShippingOrderListFragment)
                    .commit();
        });

        return v;
    }

}
