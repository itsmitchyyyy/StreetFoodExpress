package com.example.administrator.streetfood.Vendor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.streetfood.Customer.CustomerServer;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VendorCompletedOrdersListFragment extends Fragment {

    private ImageView customerImage;
    private TextView customerName, customerEmail;
    private ListView completedOrderListView;
    private CustomerServer customerServer;
    private OrderServer orderServer;
    private String uuid;
    List<Order> orderList = new ArrayList<>();
    private VendorCompletedOrdersListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vendor_completed_orders_list, container, false);

        customerImage = v.findViewById(R.id.completedOrderCustomerImage);
        customerName = v.findViewById(R.id.completedOrderCustomerName);
        customerEmail = v.findViewById(R.id.completedOrderCustomerEmail);

        Bundle b = getArguments();
        customerServer = new CustomerServer(getContext());
        orderServer = new OrderServer(getContext());

        completedOrderListView = v.findViewById(R.id.completedOrderListView);

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
                adapter = new VendorCompletedOrdersListAdapter(getContext(), orderList);
                completedOrderListView.setAdapter(adapter);
            }

            @Override
            public void onUpdateStatus(boolean status) {

            }
        });

        return v;
    }

}
