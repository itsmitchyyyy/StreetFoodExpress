package com.example.administrator.streetfood.Payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.administrator.streetfood.R;

public class PaymentFragment extends Fragment {

    ImageButton cashOnDelivery, onlinePayment;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View paymentView = inflater.inflate(R.layout.activity_payment, container, false);

        cashOnDelivery = paymentView.findViewById(R.id.codButton);
        onlinePayment = paymentView.findViewById(R.id.paypalButton);

        return paymentView;
    }
}
