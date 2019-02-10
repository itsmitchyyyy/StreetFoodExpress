package com.example.administrator.streetfood.Order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.streetfood.Payment.PaymentActivity;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    ListView listView;
    OrderListAdapter orderListAdapter;
    ArrayList<Product> productList = new ArrayList<>();
    TextView totalAmount;
    Button payButton;

    private double totalOrderAmount;
    private ProductServer productServer;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View orderView = inflater.inflate(R.layout.activity_order, container, false);

        productServer = new ProductServer(getContext());

        listView = orderView.findViewById(R.id.listView1);
        totalAmount = orderView.findViewById(R.id.textView4);
        payButton = orderView.findViewById(R.id.button5);

        productServer.viewAll(new ProductServer.VolleyCallBack() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onProductQuery(List<Product> list) {
                productList.addAll(list);
                orderListAdapter = new OrderListAdapter(orderView.getContext(), productList);
                listView.setAdapter(orderListAdapter);

                orderListAdapter.setOnDataChangeListener(() -> {
                    totalOrderAmount = orderListAdapter.getTotalAmount(productList);
                    totalAmount.setText(String.format(Locale.getDefault(), "%.2f", totalOrderAmount));

                    if (totalOrderAmount == 0.00) {
                        payButton.setEnabled(false);
                    } else {
                        payButton.setEnabled(true);
                    }
                });
            }

            @Override
            public void onGetProduct(Product product) {

            }
        });

        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(orderView.getContext(), PaymentActivity.class);
            intent.putExtra("totalAmount", String.format(Locale.getDefault(), "%.2f",totalOrderAmount));
            startActivity(intent);
        });

        return orderView;
    }
}
