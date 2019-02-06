package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.CustomProgressHorizontalDialog;
import com.example.administrator.streetfood.Shared.DBConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VendorServer {

    private Context context;
    private CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public interface VolleyCallBack {
        void onOrdersQuery(List<Order> list);
    }

    public VendorServer() {
    }

    public VendorServer(Context context) {
        this.context = context;
    }

    public void viewOrders(final VolleyCallBack volleyCallBack){
        List<Order> list = new ArrayList<>();
        customProgressDialog.showProgress(context);
        String url = DBConfig.ServerURL + "order/view.php";
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

}
