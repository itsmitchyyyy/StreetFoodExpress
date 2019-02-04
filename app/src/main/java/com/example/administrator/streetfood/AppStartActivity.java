package com.example.administrator.streetfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);

        SharedPreferences sharedPreferences = getSharedPreferences("accountPref", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("id", null) != null)
            startActivity(new Intent(this, ActivityDrawer.class));
        else
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);

    }
}
