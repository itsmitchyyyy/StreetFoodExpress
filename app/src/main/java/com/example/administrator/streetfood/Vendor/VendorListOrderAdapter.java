package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Customer.Customer;
import com.example.administrator.streetfood.Customer.CustomerServer;
import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.VendorActivityDrawer;

import java.util.List;

public class VendorListOrderAdapter extends ArrayAdapter<Order> {

    public VendorListOrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Order order = getItem(position);

        CustomerServer customerServer = new CustomerServer(getContext());

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vendor_order, null);
            viewHolder.orderImage = convertView.findViewById(R.id.imageView3);
            viewHolder.orderName = convertView.findViewById(R.id.textView15);
            viewHolder.orderDate = convertView.findViewById(R.id.textView16);
            viewHolder.btnTakeOrder = convertView.findViewById(R.id.button9);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert order != null;
        customerServer.getCustomerOrder(order.getCustomerId(), customer -> {
//          viewHolder.orderImage.setImageResource();
            viewHolder.orderName.setText(customer.getFirstname().concat(customer.getLastname()));
            viewHolder.orderDate.setText(order.getOrderDate());
            viewHolder.btnTakeOrder.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putInt("id", order.getCustomerId());
                b.putString("orderDate", order.getOrderDate());
                b.putString("name", customer.getFirstname().concat(" ").concat(customer.getLastname()));
//                b.putString("image", customerGetImage());
                VendorTakeOrderFragment vendorTakeOrderFragment = new VendorTakeOrderFragment();
                vendorTakeOrderFragment.setArguments(b);
                ((VendorActivityDrawer) getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.vendorContentFrame, vendorTakeOrderFragment)
                        .commit();
            });
        });



        return convertView;
    }

    static class ViewHolder {
        ImageView orderImage;
        TextView orderDate, orderName;
        Button btnTakeOrder;
    }
}
