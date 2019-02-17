package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.PusherConfig;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VendorProductListFragment extends Fragment {


    private ListView listView;
    private VendorServer vendorServer;
    List<Product> products = new ArrayList<>();
    private VendorProductListAdapter vendorProductListAdapter;
    private PusherConfig pusherConfig;

    public VendorProductListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vendor_product_list, container, false);
        listView = v.findViewById(R.id.productListView);

        vendorServer = new VendorServer(getContext());

        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher("eb6ffe9cf06d0c047e3a", options);

        Channel channel = pusher.subscribe("streetfood");

        channel.bind("product", (channelName, eventName, data) -> {
          handler.post(this::productList);
        });

        pusher.connect();

        productList();

        return v;
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public void productList() {
        products.clear();
        vendorServer.viewVendorProduct(new VendorServer.VolleyCallBack() {
            @Override
            public void onOrdersQuery(List<Order> list) {

            }

            @Override
            public void onVendorProductQuery(List<Product> list) {
                products.addAll(list);
                vendorProductListAdapter = new VendorProductListAdapter(getContext(), products);
                listView.setAdapter(vendorProductListAdapter);
            }
        });
    }

}
