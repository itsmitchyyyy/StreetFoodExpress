package com.example.administrator.streetfood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Shared.PageSwipe;
import com.example.administrator.streetfood.Shared.myPagerAdapter;
import com.example.administrator.streetfood.Vendor.AddProductFragment;

import java.util.List;
import java.util.Vector;

public class ProductTabFragment extends Fragment {

    public ProductTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product_tab, container, false);
        FragmentTabHost tabHost = v.findViewById(android.R.id.tabhost);
        ViewPager viewPager = v.findViewById(R.id.viewPager);

        List<Fragment> fragmentList = new Vector<Fragment>();
        fragmentList.add(Fragment.instantiate(getActivity(), AddProductFragment.class.getName()));
        fragmentList.add(Fragment.instantiate(getActivity(), AddProductFragment.class.getName()));
        PagerAdapter adapter = new myPagerAdapter(getChildFragmentManager(), fragmentList);

        viewPager.setAdapter(adapter);

        tabHost.setup(getContext(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("addProduct").setIndicator("Add Product", null),
                AddProductFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("productList").setIndicator("Product List", null),
                AddProductFragment.class, null);

        viewPager.setOnPageChangeListener(new PageSwipe(tabHost));

        return v;
    }
}
