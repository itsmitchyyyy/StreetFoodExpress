package com.example.administrator.streetfood;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CustomerServer {

    private Context mContext;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public CustomerServer(Context context) {
        this.mContext = context;
    }

    public void loginCustomer(String mUrl, Customer customer) {
        customProgressDialog.showProgress(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.POST, mUrl, response -> {
            Log.d("customer", response);
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
