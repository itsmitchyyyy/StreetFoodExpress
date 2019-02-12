package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.streetfood.R;

public class VendorProductListFragment extends Fragment {


    private ListView listView;

    public VendorProductListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vendor_product_list, container, false);
        listView = v.findViewById(R.id.productListView);

        return v;
    }

}
