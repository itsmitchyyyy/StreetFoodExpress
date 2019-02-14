package com.example.administrator.streetfood.Shared;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.streetfood.Vendor.AddProductFragment;
import com.example.administrator.streetfood.Vendor.VendorProductListFragment;

public class myViewPager extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Add Product", "Product List"};
    private Context context;

    public myViewPager(FragmentManager fm, Context ctx) {
        super(fm);
        context = ctx;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AddProductFragment();
            case 1:
                return new VendorProductListFragment();
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
