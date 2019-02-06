package com.example.administrator.streetfood.Payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class PaymentFragment extends Fragment {

    private CustomProgressDialog customProgressDialog;
    ImageButton cashOnDelivery, onlinePayment;
    View paymentView;
    BraintreeFragment braintreeFragment;
    private String clientToken;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paymentView = inflater.inflate(R.layout.activity_payment, container, false);

        customProgressDialog = new CustomProgressDialog().getInstance();

        cashOnDelivery = paymentView.findViewById(R.id.codButton);
        onlinePayment = paymentView.findViewById(R.id.paypalButton);

        getClientToken();

        onlinePayment.setOnClickListener(v -> {
            submitCheckout(getContext());
        });
        return paymentView;
    }

    public void getClientToken() {
        customProgressDialog.showProgress(getContext());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(DBConfig.ServerURL + "payment/client-token.php", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                customProgressDialog.hideProgress();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                clientToken = responseString;
                customProgressDialog.hideProgress();
            }
        });
    }

    public void submitCheckout(Context context){
        DropInRequest request = new DropInRequest().clientToken(clientToken);
        startActivityForResult(request.getIntent(context), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                Toast.makeText(getContext(), result.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "cancelled", Toast.LENGTH_SHORT).show();
            }
        } else {
            Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}