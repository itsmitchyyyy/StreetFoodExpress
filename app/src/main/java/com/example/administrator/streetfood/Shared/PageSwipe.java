package com.example.administrator.streetfood.Shared;

import android.support.v4.view.ViewPager;
import android.widget.TabHost;

public class PageSwipe implements ViewPager.OnPageChangeListener {

    TabHost tabHost;

    public PageSwipe() {}

    public PageSwipe(TabHost host) {
        tabHost = host;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
