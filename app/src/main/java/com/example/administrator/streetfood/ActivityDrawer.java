package com.example.administrator.streetfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Customer.Customer;
import com.example.administrator.streetfood.Customer.CustomerOrderList;
import com.example.administrator.streetfood.Order.OrderFragment;
import com.example.administrator.streetfood.Shared.Session;

public class ActivityDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawLayout;
    private NavigationView navigationView;
    private int backButton = 0;
    private TextView navHeaderText;
    private Session session;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mDrawLayout = findViewById(R.id.nav_drawer);

        session = new Session(getApplicationContext(), "accountPref");

        navigationView = findViewById(R.id.navView);
        View navHeader = navigationView.inflateHeaderView(R.layout.nav_header_layout);
        navHeaderText = navHeader.findViewById(R.id.navHeaderText);

        navHeaderText.setText(session.getName());

        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        toggle = new ActionBarDrawerToggle(this, mDrawLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawLayout.setDrawerListener(toggle);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentFrame, new OrderFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_order);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

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
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentFrame, new OrderFragment())
                        .commit();
                break;
            case R.id.nav_order_list:
                Bundle b = new Bundle();
                b.putString("id", String.valueOf(session.getId()));
                CustomerOrderList customerOrderList = new CustomerOrderList();
                customerOrderList.setArguments(b);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contentFrame, customerOrderList)
                        .commit();
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
        if (mDrawLayout.isDrawerOpen(GravityCompat.START)){
            mDrawLayout.closeDrawer(GravityCompat.START);
        }
//        else if(backButton > 1) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Press the back button once again", Toast.LENGTH_SHORT).show();
//            backButton++;
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
}
