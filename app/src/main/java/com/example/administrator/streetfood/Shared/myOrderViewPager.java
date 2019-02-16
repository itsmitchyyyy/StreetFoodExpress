package com.example.administrator.streetfood.Shared;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.streetfood.Vendor.VendorCompletedOrdersFragment;
import com.example.administrator.streetfood.Vendor.VendorShippingOrderFragment;

public class myOrderViewPager extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Shipping Orders", "Completed Orders"};
    private Context context;

    public myOrderViewPager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new VendorShippingOrderFragment();
            case 1:
                return new VendorCompletedOrdersFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
