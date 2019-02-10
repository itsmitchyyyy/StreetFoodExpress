package com.example.administrator.streetfood.Product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductServer {

    Context context;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public interface VolleyCallBack {
        void onSuccess(String result);
        void onProductQuery(List<Product> list);
        void onGetProduct(Product product);
    }

    public ProductServer() {
    }

    public ProductServer(Context context) {
        this.context = context;
    }

    public void addProduct(Product product){
        customProgressDialog.showProgress(context);
        String url = DBConfig.ServerURL + "product/insert.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                Toast.makeText(context, "Product added", Toast.LENGTH_SHORT).show();
                customProgressDialog.hideProgress();
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to server", Toast.LENGTH_SHORT).show();;
            customProgressDialog.hideProgress();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("supId", Integer.toString(product.getSupId()));
                map.put("catId", Integer.toString(product.getCategoryId()));
                map.put("prodName", product.getProdName());
                map.put("prodDesc", product.getProdDesc());
                map.put("prodQty", Double.toString(product.getProdQty()));
                map.put("prodPrice", Double.toString(product.getProdPrice()));
                map.put("prodImage", product.getProdImage());
                return map;
            }
        };
        queue.add(request);
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

    public void getProduct(int id, final VolleyCallBack volleyCallBack){
        String url = DBConfig.ServerURL + "product/get.php?id=" + id;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                    JSONObject jsonObject = new JSONObject(response);
                    Product product = new Product();
                    product.setId(Integer.parseInt(jsonObject.getString("id")));
                    product.setSupId(Integer.parseInt(jsonObject.getString("supId")));
                    product.setProdName(jsonObject.getString("prodName"));
                    product.setProdImage(jsonObject.getString("prodImage"));
                    product.setProdDesc(jsonObject.getString("prodDesc"));
                    product.setProdQty(Double.parseDouble(jsonObject.getString("prodQty")));
                    product.setProdPrice(Double.parseDouble(jsonObject.getString("prodPrice")));
                    volleyCallBack.onGetProduct(product);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to the server", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }

    public Bitmap stringToBitmap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
