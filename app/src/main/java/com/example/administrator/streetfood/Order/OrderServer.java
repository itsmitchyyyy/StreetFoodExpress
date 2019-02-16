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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServer {

    private Context context;
    private CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public interface VolleyCallback {
        void onCustomerOrderListQuery(List<Order> list);
    }

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
            Toast.makeText(context, "An error occurred while processing your request", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("prodId", Integer.toString(order.getProdId()));
                map.put("customerId", Integer.toString(order.getCustomerId()));
                map.put("orderQty", Double.toString(order.getOrderQty()));
                map.put("totalAmount", Double.toString(order.getTotalAmount()));
                map.put("uuid", order.getOrderUuid());
                return map;
            }
        };
        queue.add(request);
    }

    public void customerOrderList(int id , VolleyCallback volleyCallback) {
        customProgressDialog.showProgress(context);
        List<Order> list = new ArrayList<>();
        String url = DBConfig.OrderCustomerURL + "list.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(Integer.parseInt(jsonObject.getString("id")));
                    order.setOrderUuid(jsonObject.getString("uuid"));
                    order.setProdId(Integer.parseInt(jsonObject.getString("prodId")));
                    order.setCustomerId(Integer.parseInt(jsonObject.getString("customerId")));
                    order.setTotalAmount(Double.parseDouble(jsonObject.getString("orderTotalAmount")));
                    order.setOrderDate(jsonObject.getString("orderDate"));
                    order.setOrderStatus(jsonObject.getString("orderStatus"));
                    list.add(order);
                }
                volleyCallback.onCustomerOrderListQuery(list);
                customProgressDialog.hideProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occurred while connecting to the server", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("customerId", String.valueOf(id));
                return map;
            }
        };
        queue.add(request);
    }

    public void getSelectedCustomerOrders(String id, VolleyCallback volleyCallback){
        List<Order> list = new ArrayList<>();
        String url  = DBConfig.OrderCustomerURL + "view.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(Integer.parseInt(jsonObject.getString("id")));
                    order.setProdId(Integer.parseInt(jsonObject.getString("prodId")));
                    order.setCustomerId(Integer.parseInt(jsonObject.getString("customerId")));
                    order.setTotalAmount(Double.parseDouble(jsonObject.getString("totalAmount")));
                    order.setOrderUuid(jsonObject.getString("uuid"));
                    order.setOrderDate(jsonObject.getString("orderDate"));
                    order.setOrderQty(Double.parseDouble(jsonObject.getString("orderQty")));
                    list.add(order);
                }
                volleyCallback.onCustomerOrderListQuery(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occurred while connecting to the server", Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("uuid", String.valueOf(id));
                return map;
            }
        };
        queue.add(request);
    }

    public void updateOrder(String uuid, String status){
        customProgressDialog.showProgress(context);
        String url = DBConfig.OrderVendorURL + "accept.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                Toast.makeText(context, "Order Updated", Toast.LENGTH_SHORT).show();
                customProgressDialog.hideProgress();
            }, error -> {
            Toast.makeText(context, "An error occurred while connecting to the server", Toast.LENGTH_SHORT).show();
                customProgressDialog.hideProgress();
            }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("uuid", uuid);
                map.put("orderStatus", status);
                return map;
            }
        };
        queue.add(request);
    }
}
