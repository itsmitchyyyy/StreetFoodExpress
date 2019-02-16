package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.myOrderViewPager;
import com.example.administrator.streetfood.Shared.myViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorOrderListTabFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public VendorOrderListTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_vendor_shipping_order_list, container, false);

        tabLayout = v.findViewById(R.id.shipTabLayout);
        viewPager = v.findViewById(R.id.shipViewPager);

        myOrderViewPager adapter = new myOrderViewPager(getChildFragmentManager(), getActivity());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
    }

}
