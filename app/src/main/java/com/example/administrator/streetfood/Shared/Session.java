package com.example.administrator.streetfood.Shared;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class Session {

    private Context mContext;
    private SharedPreferences sharedPreferences;

    public Session() {}

    public Session(Context context, String sessioName) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(sessioName, Context.MODE_PRIVATE);
    }

    public int getId() {
       return Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString("id", null)));
    }

    public String getName() {
        return sharedPreferences.getString("name", null);
    }

    public String getPhone() { return sharedPreferences.getString("phone", null); }

}
