package com.example.administrator.streetfood.Vendor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Order.OrderServer;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorTakeOrderFragment extends Fragment implements View.OnClickListener {


    private ListView listView;
    private SwipeRefreshLayout swipeToRefresh;
    private VendorTakeOrderAdapter takeOrderAdapter;
    ArrayList<Order> ordersList = new ArrayList<>();
    private VendorServer vendorServer;
    private int customerId;
    private TextView customerName, orderDate, totalAmount;
    private String cName, oDate;
    private Button btnConfirm;
    private double orderAmount;
    private Session session;
    private double getTotalAmount;
    private String orderUUID;
    private OrderServer orderServer;

    public VendorTakeOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor_take_order, container, false);
        session = new Session(getContext(), "accountPref");
        listView = view.findViewById(R.id.listView);
        swipeToRefresh = view.findViewById(R.id.swipeToRefresh);
        customerName = view.findViewById(R.id.textView17);
        orderDate = view.findViewById(R.id.textView18);
        totalAmount = view.findViewById(R.id.textView19);
        btnConfirm = view.findViewById(R.id.button10);

        vendorServer = new VendorServer(getContext());
        orderServer = new OrderServer(getContext());

        getFragmentArguments();
        customerOrders();

        swipeToRefresh.setOnRefreshListener(() -> {
            customerOrders();
            swipeToRefresh.setRefreshing(false);
        });

        btnConfirm.setOnClickListener(this);

        return view;
    }

    public void customerOrders() {
        ordersList.clear();
        String queryString = "customerId="+customerId+"&supId="+session.getId();
        vendorServer.viewCustomerOrder(queryString, new VendorServer.VolleyCallBack() {
            @Override
            public void onOrdersQuery(List<Order> list) {
                ordersList.addAll(list);
                takeOrderAdapter = new VendorTakeOrderAdapter(getContext(), ordersList);
                getTotalAmount = takeOrderAdapter.getTotalAmount(ordersList);
                totalAmount.setText(Double.toString(getTotalAmount));
                listView.setAdapter(takeOrderAdapter);
            }

            @Override
            public void onVendorProductQuery(List<Product> list) {

            }
        });
    }

    public void getFragmentArguments() {
        customerId = getArguments() != null ? getArguments().getInt("id") : 0;
        cName = getArguments().getString("name");
        oDate = getArguments().getString("orderDate");
        orderUUID = getArguments().getString("uuid");

        customerName.setText(cName);
        orderDate.setText(oDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button10:
                orderServer.updateOrder(orderUUID, "1");
                break;
        }
    }
}
