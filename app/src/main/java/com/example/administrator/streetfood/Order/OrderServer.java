package com.example.administrator.streetfood.Order;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;

import java.util.HashMap;
import java.util.Map;

public class OrderServer {

    private Context context;
    private CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public OrderServer() {
    }

    public OrderServer(Context context) {
        this.context = context;
    }

    public void addOrder(Order order) {
        String url = DBConfig.OrderURL + "insert.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
        }, error -> {
            Toast.makeText(context, "An error occured while processing your request", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("prodId", Integer.toString(order.getProdId()));
                map.put("customerId", Integer.toString(order.getCustomerId()));
                map.put("orderQty", Double.toString(order.getOrderQty()));
                map.put("totalAmount", Double.toString(order.getTotalAmount()));
                return map;
            }
        };
        queue.add(request);
    }
}
