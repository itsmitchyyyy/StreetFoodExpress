package com.example.administrator.streetfood.Payment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PostalAddress;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.CustomProgressDialog;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class PaymentFragment extends Fragment {

    private CustomProgressDialog customProgressDialog;
    ImageButton cashOnDelivery, onlinePayment;
    View paymentView;
    private String clientToken;
    private BraintreeFragment braintreeFragment;
    List<HashMap<String, Integer>> list;
    private OrderServer orderServer;
    Order order;
    private String orderTotalAmount;

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paymentView = inflater.inflate(R.layout.activity_payment, container, false);

        customProgressDialog = new CustomProgressDialog().getInstance();

        Bundle b = getArguments();
        if (b.size() > 0) {
            list = (ArrayList<HashMap<String, Integer>>)b.getSerializable("orderDetails");
            orderTotalAmount = b.getString("orderTotalAmount");
        }

        orderServer = new OrderServer(getContext());

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
               Log.d("asd", responseString);
                customProgressDialog.hideProgress();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Log.d("asd", responseString);
                    clientToken = responseString;
                    braintreeFragment = BraintreeFragment.newInstance(getActivity(), clientToken);
                    braintreeFragment.addListener(braintreeCancelListener);
                    braintreeFragment.addListener(braintreeErrorListener);
                    braintreeFragment.addListener(paymentMethodNonceCreatedListener);
                    customProgressDialog.hideProgress();
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void submitCheckout(Context context){
        customProgressDialog.showProgress(context);
        PayPalRequest request =  new PayPalRequest("1")
                .currencyCode("USD")
                .intent(PayPalRequest.INTENT_AUTHORIZE);
        PayPal.requestOneTimePayment(braintreeFragment, request);
    }

    private BraintreeErrorListener braintreeErrorListener  = error -> {
        if (error instanceof ErrorWithResponse) {
            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
            BraintreeError braintreeError = errorWithResponse.errorFor("creditCard");
            if (braintreeError != null) {
                BraintreeError monthError = braintreeError.errorFor("expirationMonth");
                if (monthError != null) {
                    Toast.makeText(getContext(), monthError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        customProgressDialog.hideProgress();
    };

    private BraintreeCancelListener braintreeCancelListener = requestCode -> {
            Toast.makeText(getContext(), "Cancelled Payment", Toast.LENGTH_SHORT).show();
        customProgressDialog.hideProgress();
    };

    private PaymentMethodNonceCreatedListener paymentMethodNonceCreatedListener = paymentMethodNonce -> {
        String nonce = paymentMethodNonce.getNonce();
        if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;

            String email = payPalAccountNonce.getEmail();
            String firstName = payPalAccountNonce.getFirstName();
            String lastName = payPalAccountNonce.getLastName();
            String phone = payPalAccountNonce.getPhone();

            PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
            PostalAddress shippingAddress = payPalAccountNonce.getShippingAddress();
            postNonceToServer(nonce);
        }

    };

    void postNonceToServer(String nonce) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", nonce);
        params.put("amount", orderTotalAmount);
        client.post(DBConfig.ServerURL + "payment/payment.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("zxc", responseString);
                Toast.makeText(getContext(), "Payment Error", Toast.LENGTH_SHORT).show();
                customProgressDialog.hideProgress();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                String uuid = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
                for (HashMap<String, Integer> map: list) {
                    order = new Order();
                    order.setProdId(map.get("prodId"));
                    order.setCustomerId(map.get("customerId"));
                    order.setOrderQty(map.get("orderQty"));
                    order.setTotalAmount(map.get("totalAmount"));
                    order.setOrderUuid(uuid);
                    orderServer.addOrder(order);
                }
                customProgressDialog.hideProgress();
                Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}