package com.example.administrator.streetfood.Shared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.ActivityDrawer;
import com.example.administrator.streetfood.Customer.Customer;
import com.example.administrator.streetfood.Vendor.Vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Server {

    public static final String accountPreferences = "accountPref";
    private Context mContext;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public Server(){}

    public Server(Context context) {
        this.mContext = context;
    }

    public void setSession(Users users, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(accountPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", Integer.toString(users.getId()));
        editor.putString("name", users.getFirstname().concat(users.getLastname()));
        editor.apply();
    }

    public void login(String mUrl, Users users) {
        customProgressDialog.showProgress(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.POST, mUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status") != null) {
                    Toast.makeText(mContext, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                } else {

                    Users user = new Users(jsonObject.getString("email"), jsonObject.getString("password"),
                            jsonObject.getString("gender"), jsonObject.getString("birthdate"),
                            jsonObject.getString("firstName"), jsonObject.getString("lastName"),
                            jsonObject.getString("userType"));
                    user.setId(Integer.parseInt(jsonObject.getString("id")));

                    setSession(user, mContext);
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
                map.put("email", users.getEmail());
                map.put("password", users.getPassword());
                return map;
            }
        };
        queue.add(request);
    }

    public void sendRequest(String mUrl, Users users) {
        customProgressDialog.showProgress(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.POST, mUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status") != null) {
                    Toast.makeText(mContext, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                } else {
                    Users user = new Users(jsonObject.getString("email"), jsonObject.getString("password"),
                            jsonObject.getString("gender"), jsonObject.getString("birthdate"),
                            jsonObject.getString("firstName"), jsonObject.getString("lastName"),
                            jsonObject.getString("userType"));
                    user.setId(Integer.parseInt(jsonObject.getString("id")));

//                    setSession(user, mContext);

                    Toast.makeText(mContext, user.getType(), Toast.LENGTH_SHORT).show();

                    mContext.startActivity(new Intent(mContext, ActivityDrawer.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            customProgressDialog.hideProgress();
        }, error -> {
            Toast.makeText(mContext, "An error ofccured", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("firstName", users.getFirstname());
                map.put("lastName", users.getLastname());
                map.put("gender", users.getGender());
                map.put("birthdate", users.getBirthdate());
                map.put("email", users.getEmail());
                map.put("password", users.getPassword());
                map.put("userType", users.getType());
                return map;
            }
        };
        queue.add(request);
    }

    private void setCustomer(Users users){
        Customer newCustomer = new Customer(users.getEmail(), users.getPassword(),
                users.getGender(), users.getBirthdate(),
                users.getFirstname(), users.getLastname());
        newCustomer.setId(users.getId());

    }

    private void setVendor(Users users){
        Vendor newVendor = new Vendor(users.getEmail(), users.getPassword(),
                users.getGender(), users.getBirthdate(),
                users.getFirstname(), users.getLastname());
        newVendor.setId(users.getId());
    }
}
