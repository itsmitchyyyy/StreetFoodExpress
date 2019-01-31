package com.example.administrator.streetfood.Customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.ActivityDrawer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerServer {

    public static final String accountPreferences = "accountPref";
    private Context mContext;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();
    SharedPreferences sharedPreferences;

    public CustomerServer(){}

    public CustomerServer(Context context) {
        this.mContext = context;
    }

    public void loginCustomer(String mUrl, Customer customer) {
        customProgressDialog.showProgress(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.POST, mUrl, response -> {
            Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status") != null) {
                    Toast.makeText(mContext, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                } else {
//                        sharedPreferences = mContext.getSharedPreferences(accountPreferences, Context.MODE_PRIVATE);
                        new Customer(jsonObject.getString("email"), jsonObject.getString("password"),
                                jsonObject.getString("gender"), jsonObject.getString("birthdate"),
                                jsonObject.getString("firstName"), jsonObject.getString("lastName"));

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("id", jsonObject.getString("id"));
//                        editor.putString("name", jsonObject.getString("firstName").concat(jsonObject.getString("lastName")));
//                        editor.apply();

                        mContext.startActivity(new Intent(mContext, ActivityDrawer.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            customProgressDialog.hideProgress();
        }, error -> {
            Toast.makeText(mContext, "An error occured", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", customer.getEmail());
                map.put("password", customer.getPassword());
                return map;
            }
        };
        queue.add(request);
    }

    public void sendRequest(String mUrl, Customer customer) {
        customProgressDialog.showProgress(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.POST, mUrl, response -> {
            Log.d("customer", response);
            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        }, error -> {
            Toast.makeText(mContext, "An error occured", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("firstName", customer.getFirstname());
                map.put("lastName", customer.getLastname());
                map.put("gender", customer.getGender());
                map.put("birthdate", customer.getBirthdate());
                map.put("email", customer.getEmail());
                map.put("password", customer.getPassword());
                return map;
            }
        };
        queue.add(request);
    }
}
