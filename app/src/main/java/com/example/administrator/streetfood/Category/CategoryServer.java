package com.example.administrator.streetfood.Category;

import android.content.Context;
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

public class CategoryServer {

    Context context;
    CustomProgressDialog customProgressDialog = new CustomProgressDialog().getInstance();

    public CategoryServer() {
    }

    public CategoryServer(Context context) {
        this.context = context;
    }

    public interface VolleyCallBack {
        void onGetCategories(List<Category> categoryList);
        void onGetCategory(Category category);
    }

    public void getCategories(VolleyCallBack volleyCallBack) {
        String url = DBConfig.ServerURL + "category/view.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        List<Category> list = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Category category = new Category();
                    category.setId(Integer.parseInt(jsonObject.getString("id")));
                    category.setCatName(jsonObject.getString("catName"));
                    category.setCatDesc(jsonObject.getString("catDesc"));
                    list.add(category);
                }
                volleyCallBack.onGetCategories(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, "An error occured while connecting to server", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }

}
