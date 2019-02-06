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
import android.widget.Toast;

import com.example.administrator.streetfood.Order.OrderFragment;

public class ActivityDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawLayout;
    private NavigationView navigationView;
    private int backButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mDrawLayout = findViewById(R.id.nav_drawer);

        navigationView = findViewById(R.id.navView);

        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, new OrderFragment()).commit();
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

    @Override
    public void onBackPressed() {
        if (mDrawLayout.isDrawerOpen(GravityCompat.START)){
            mDrawLayout.closeDrawer(GravityCompat.START);
        }
        else if(backButton > 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press the back button once again", Toast.LENGTH_SHORT).show();
            backButton++;
            return;
        }
        super.onBackPressed();
    }
}
