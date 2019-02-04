package com.example.administrator.streetfood.Payment;

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
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.example.administrator.streetfood.MainActivity;
import com.example.administrator.streetfood.Order.OrderFragment;
import com.example.administrator.streetfood.R;

public class PaymentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Bundle b = getIntent().getExtras();
        assert b != null;
        if(b.isEmpty()) {
            Toast.makeText(this, b.getString("totalAmount"), Toast.LENGTH_SHORT).show();
        }

        drawerLayout = findViewById(R.id.nav_drawer);
        navigationView = findViewById(R.id.navView);

        toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, new PaymentFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_order);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            open the drawer when the user taps on the nav drawer button
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        switch (id) {
            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, new OrderFragment()).commit();
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
}