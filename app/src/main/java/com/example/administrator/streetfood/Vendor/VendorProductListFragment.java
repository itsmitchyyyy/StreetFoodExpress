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

import java.util.ArrayList;
import java.util.List;

public class VendorProductListFragment extends Fragment {


    private ListView listView;
    private VendorServer vendorServer;
    List<Product> products = new ArrayList<>();
    private VendorProductListAdapter vendorProductListAdapter;

    public VendorProductListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vendor_product_list, container, false);
        listView = v.findViewById(R.id.productListView);

        vendorServer = new VendorServer(getContext());

        productList();

        return v;
    }

    public void productList() {
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
