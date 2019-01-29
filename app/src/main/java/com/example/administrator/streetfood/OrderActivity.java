package com.example.administrator.streetfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    ListView listView;
    OrderListAdapter orderListAdapter;
    ArrayList<Order> orderList = new ArrayList<Order>();
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderList.add(new Order(1, 1, 20, R.drawable.streetfoodlogo, 20.00, "2019-01-01", "Taho"));
        orderList.add(new Order(2, 2, 30, R.drawable.streetfoodlogo, 30.00, "2019-02-02", "Chicharon"));

        orderListAdapter = new OrderListAdapter(this, orderList);
        listView = findViewById(R.id.listView1);
        totalAmount = findViewById(R.id.textView4);

        listView.setAdapter(orderListAdapter);
        orderListAdapter.setOnDataChangeListener(new OrderListAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged() {
                double totalOrderAmount = orderListAdapter.getTotalAmount(orderList);
                totalAmount.setText(String.format(Locale.getDefault(), "%.2f", totalOrderAmount));
            }
        });
    }

}
