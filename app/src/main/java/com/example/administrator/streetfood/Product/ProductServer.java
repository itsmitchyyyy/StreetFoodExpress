package com.example.administrator.streetfood.Product;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductServer {

    Context context;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public interface VolleyCallBack {
        void onSuccess(String result);
        void onProductQuery(List<Product> list);
    }

    public ProductServer() {
    }

    public ProductServer(Context context) {
        this.context = context;
    }

    public void viewAll(final VolleyCallBack volleyCallBack){
        List<Product> list = new ArrayList<>();
        customProgressDialog.showProgress(context);
        String url = DBConfig.ServerURL + "product/view.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Product product = new Product();
                    product.setId(Integer.parseInt(jsonObject.getString("id")));
                    product.setSupId(Integer.parseInt(jsonObject.getString("supId")));
                    product.setProdName(jsonObject.getString("prodName"));
                    product.setProdImage(jsonObject.getString("prodImage"));
                    product.setProdDesc(jsonObject.getString("prodDesc"));
                    product.setProdQty(Double.parseDouble(jsonObject.getString("prodQty")));
                    product.setProdPrice(Double.parseDouble(jsonObject.getString("prodPrice")));
                    list.add(product);
                }
                Objects.requireNonNull(volleyCallBack).onProductQuery(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            customProgressDialog.hideProgress();
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to the server", Toast.LENGTH_SHORT).show();
            customProgressDialog.hideProgress();
        });
        queue.add(request);
    }
}
