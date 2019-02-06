package com.example.administrator.streetfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

public class AppStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);

        SharedPreferences sharedPreferences = getSharedPreferences("accountPref", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("id", null) != null)
            if (Objects.equals(sharedPreferences.getString("type", null), "Customer"))
                startActivity(new Intent(this, ActivityDrawer.class));
            else
                startActivity(new Intent(this, VendorActivityDrawer.class));
        else
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 3000);

    }
}
