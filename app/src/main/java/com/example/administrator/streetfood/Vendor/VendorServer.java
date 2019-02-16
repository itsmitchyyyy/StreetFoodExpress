package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Product.Product;
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

public class VendorServer {

    private Context context;
    private CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public interface VolleyCallBack {
        void onOrdersQuery(List<Order> list);
        void onVendorProductQuery(List<Product> list);
    }

    public VendorServer() {
    }

    VendorServer(Context context) {
        this.context = context;
    }

    void viewShippingOrdersList(String status, VolleyCallBack volleyCallBack) {
        List<Order> list = new ArrayList<>();
        int id = new Session(context, Server.accountPreferences).getId();
        customProgressDialog.showProgress(context);
        String url = DBConfig.OrderVendorURL + "list.php";
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
                volleyCallBack.onOrdersQuery(list);
                customProgressDialog.hideProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to server", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderStatus", status);
                map.put("supId", String.valueOf(id));
                return map;
            }
        };
        queue.add(request);
    }

    void viewOrders(String status, final VolleyCallBack volleyCallBack){
        int id = new Session(context, Server.accountPreferences).getId();
        List<Order> list = new ArrayList<>();
        customProgressDialog.showProgress(context);
        String url = DBConfig.OrderVendorURL + "view.php?id=" + id + "&orderStatus=" + status;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(Integer.parseInt(jsonObject.getString("id")));
                    order.setProdId(Integer.parseInt(jsonObject.getString("prodId")));
                    order.setCustomerId(Integer.parseInt(jsonObject.getString("customerId")));
                    order.setOrderQty(Double.parseDouble(jsonObject.getString("orderQty")));
                    order.setTotalAmount(Double.parseDouble(jsonObject.getString("totalAmount")));
                    order.setOrderDate(jsonObject.getString("orderDate"));
                    order.setOrderUuid(jsonObject.getString("uuid"));
                    order.setOrderStatus(jsonObject.getString("orderStatus"));
                    list.add(order);
                }
                volleyCallBack.onOrdersQuery(list);
                customProgressDialog.hideProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to server", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        });
        queue.add(request);
    }


    void viewCustomerOrder(String queryString, final VolleyCallBack volleyCallBack){
        List<Order> list = new ArrayList<>();
        customProgressDialog.showProgress(context);
        String url = DBConfig.ServerURL + "order/customer/get.php?" + queryString;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Order order = new Order();
                    order.setId(Integer.parseInt(jsonObject.getString("id")));
                    order.setProdId(Integer.parseInt(jsonObject.getString("prodId")));
                    order.setCustomerId(Integer.parseInt(jsonObject.getString("customerId")));
                    order.setOrderQty(Double.parseDouble(jsonObject.getString("orderQty")));
                    order.setTotalAmount(Double.parseDouble(jsonObject.getString("totalAmount")));
                    order.setOrderDate(jsonObject.getString("orderDate"));
                    order.setOrderStatus(jsonObject.getString("orderStatus"));
                    list.add(order);
                }
                volleyCallBack.onOrdersQuery(list);
                customProgressDialog.hideProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to server", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        });
        queue.add(request);
    }

    void viewVendorProduct(VolleyCallBack volleyCallBack) {
        int id = new Session(context, Server.accountPreferences).getId();
        List<Product> list= new ArrayList<>();
        customProgressDialog.showProgress(context);
        String url = DBConfig.VendorProductURL + "view.php?id=" + id;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Product product = new Product();
                    product.setId(Integer.parseInt(jsonObject.getString("id")));
                    product.setSupId(Integer.parseInt(jsonObject.getString("supId")));
                    product.setCategoryId(Integer.parseInt(jsonObject.getString("catId")));
                    product.setProdName(jsonObject.getString("prodName"));
                    product.setProdDesc(jsonObject.getString("prodDesc"));
                    product.setProdQty(Double.parseDouble(jsonObject.getString("prodQty")));
                    product.setProdPrice(Double.parseDouble(jsonObject.getString("prodPrice")));
                    product.setProdImage(jsonObject.getString("prodImage"));
                    list.add(product);
                }
                volleyCallBack.onVendorProductQuery(list);
                customProgressDialog.hideProgress();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to the server", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();}
        );
        queue.add(request);
    }

}
