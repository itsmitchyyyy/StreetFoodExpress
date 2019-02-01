package com.example.administrator.streetfood.Order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.streetfood.Payment.PaymentActivity;
import com.example.administrator.streetfood.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    ListView listView;
    OrderListAdapter orderListAdapter;
    ArrayList<Order> orderList = new ArrayList<Order>();
    TextView totalAmount;
    Button payButton;

    private double totalOrderAmount;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View orderView = inflater.inflate(R.layout.activity_order, container, false);

        orderList.add(new Order(1, 1, 20, R.drawable.streetfoodlogo, 20.00, "2019-01-01", "Taho"));
        orderList.add(new Order(2, 2, 30, R.drawable.streetfoodlogo, 30.00, "2019-02-02", "Chicharon"));

        orderListAdapter = new OrderListAdapter(orderView.getContext(), orderList);
        listView = orderView.findViewById(R.id.listView1);
        totalAmount = orderView.findViewById(R.id.textView4);
        payButton = orderView.findViewById(R.id.button5);

        listView.setAdapter(orderListAdapter);

        orderListAdapter.setOnDataChangeListener(() -> {
            totalOrderAmount = orderListAdapter.getTotalAmount(orderList);
            totalAmount.setText(String.format(Locale.getDefault(), "%.2f", totalOrderAmount));

            if (totalOrderAmount == 0.00) {
                payButton.setEnabled(false);
            } else {
                payButton.setEnabled(true);
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
