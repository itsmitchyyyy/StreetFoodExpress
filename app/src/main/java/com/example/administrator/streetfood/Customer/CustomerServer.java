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
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.example.administrator.streetfood.Shared.Server;
import com.example.administrator.streetfood.Shared.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServer {

    public static final String accountPreferences = "accountPref";
    private Context mContext;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public interface VolleyCallBack {
        void onGetCustomer(Customer customer);
    }

    public CustomerServer(){}

    public CustomerServer(Context context) {
        this.mContext = context;
    }

    public void setSession(Customer customer, Context context) {
       SharedPreferences sharedPreferences = context.getSharedPreferences(accountPreferences, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString("id", Integer.toString(customer.getId()));
       editor.putString("name", customer.getLastname().concat(customer.getLastname()));
       editor.apply();
    }

    public void loginCustomer(String mUrl, Customer customer) {
        customProgressDialog.showProgress(mContext);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.POST, mUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status") != null) {
                    Toast.makeText(mContext, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                } else {
                        Customer newCustomer = new Customer(jsonObject.getString("email"), jsonObject.getString("password"),
                                jsonObject.getString("gender"), jsonObject.getString("birthdate"),
                                jsonObject.getString("firstName"), jsonObject.getString("lastName"));
                        newCustomer.setId(Integer.parseInt(jsonObject.getString("id")));

                        setSession(newCustomer, mContext);

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
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status") != null) {
                    Toast.makeText(mContext, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                } else {
                    Customer newCustomer = new Customer(jsonObject.getString("email"), jsonObject.getString("password"),
                            jsonObject.getString("gender"), jsonObject.getString("birthdate"),
                            jsonObject.getString("firstName"), jsonObject.getString("lastName"));
                    newCustomer.setId(Integer.parseInt(jsonObject.getString("id")));

                    setSession(newCustomer, mContext);

                    mContext.startActivity(new Intent(mContext, ActivityDrawer.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                map.put("userType", customer.getTag());
                return map;
            }
        };
        queue.add(request);
    }

    public void getCustomerOrder(int id, VolleyCallBack volleyCallBack) {
        String url = DBConfig.ServerURL + "user/get.php?id=" + id;
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                    JSONObject jsonObject = new JSONObject(response);
                    Customer customer = new Customer(jsonObject.getString("email"), jsonObject.getString("password"),
                            jsonObject.getString("gender"), jsonObject.getString("birthdate"),
                            jsonObject.getString("firstName"), jsonObject.getString("lastName"));
                    customer.setId(Integer.parseInt(jsonObject.getString("id")));
                volleyCallBack.onGetCustomer(customer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(mContext, "An error occured while connecting to server", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }
}
