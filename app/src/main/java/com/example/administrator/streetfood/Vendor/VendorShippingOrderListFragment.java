package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.streetfood.Customer.CustomerServer;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.SendMessage;
import com.example.administrator.streetfood.Shared.Server;
import com.example.administrator.streetfood.Shared.Session;
import com.example.administrator.streetfood.VendorActivityDrawer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorShippingOrderListFragment extends Fragment {


    private ListView shippingOrderListView;
    private ImageView customerImage;
    private TextView customerName, customerEmail;
    private CustomerServer customerServer;
    List<Order> orderList = new ArrayList<>();
    private OrderServer orderServer;
    private VendorShippingOrderListAdapter adapter;
    private Button buttonComplete;
    private String uuid;
    private SendMessage sendMessage;
    private Session session;

    public VendorShippingOrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.vendor_shipping_order_list, container, false);

        customerImage = v.findViewById(R.id.customerImage);
        customerName = v.findViewById(R.id.customerName);
        customerEmail = v.findViewById(R.id.customerEmail);
        buttonComplete = v.findViewById(R.id.button17);

        sendMessage = new SendMessage(getContext());
        session = new Session(getContext(), Server.accountPreferences);

        Bundle b = getArguments();
        customerServer = new CustomerServer(getContext());
        orderServer = new OrderServer(getContext());

        shippingOrderListView = v.findViewById(R.id.shippingOrderListView);

        assert b != null;
        uuid = b.getString("uuid");
        customerServer.getCustomerOrder(Integer.parseInt(Objects.requireNonNull(b.getString("customerId"))), customer -> {
//            Picasso.get().load(customer.get)
            customerName.setText(customer.getFirstname().concat(" ").concat(customer.getLastname()));
            customerEmail.setText(customer.getEmail());
        });

        orderServer.getSelectedCustomerOrders(uuid, new OrderServer.VolleyCallback() {
            @Override
            public void onCustomerOrderListQuery(List<Order> list) {
                orderList.addAll(list);
                adapter = new VendorShippingOrderListAdapter(getContext(), orderList);
                shippingOrderListView.setAdapter(adapter);
            }

            @Override
            public void onUpdateStatus(boolean status) {

            }
        });

        buttonComplete.setOnClickListener(v1 -> {
            orderServer.updateOrder(uuid, "2", new OrderServer.VolleyCallback() {
                @Override
                public void onCustomerOrderListQuery(List<Order> list) {

                }

                @Override
                public void onUpdateStatus(boolean status) {
                    if (status) {
                        sendMessage.sendMessage("Your order has been delivered", session.getPhone());
                        ((VendorActivityDrawer)getContext())
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.vendorContentFrame, new VendorCompletedOrdersFragment())
                                .commit();
                    }
                }
            });
        });

        return v;
    }

}
