package com.example.administrator.streetfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Order.OrderFragment;
import com.example.administrator.streetfood.Shared.Session;
import com.example.administrator.streetfood.Vendor.AddProductFragment;
import com.example.administrator.streetfood.Vendor.VendorOrderListFragment;

public class VendorActivityDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawLayout;
    private NavigationView navigationView;
    private int backButton = 0;
    private TextView navHeaderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_drawer);

        Session session = new Session(getApplicationContext(), "accountPref");

        mDrawLayout = findViewById(R.id.vendor_nav_drawer);

        navigationView = findViewById(R.id.vendorNavView);
        View navHeader = navigationView.inflateHeaderView(R.layout.nav_header_layout);
        navHeaderText = navHeader.findViewById(R.id.navHeaderText);

        navHeaderText.setText(session.getName());

        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.vendorContentFrame, new VendorOrderListFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_order);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            open the drawer when the user taps on the nav drawer button
            case android.R.id.home:
                mDrawLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        menuItem.setChecked(true);
        mDrawLayout.closeDrawers();
        switch (id) {
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.vendorContentFrame, new VendorOrderListFragment()).commit();
                break;
            case R.id.nav_product:
//                getSupportFragmentManager().beginTransaction().replace(R.id.vendorContentFrame, new AddProductFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.vendorContentFrame, new ProductTabFragment()).commit();
                break;
            case R.id.nav_logout:
                SharedPreferences sharedPreferences = getSharedPreferences("accountPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawLayout.closeDrawer(GravityCompat.START);
        }
//        else if (backButton > 1) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Press the back button once again", Toast.LENGTH_SHORT).show();
//            backButton++;
//            return;
//        }
//        super.onBackPressed();
    }
}