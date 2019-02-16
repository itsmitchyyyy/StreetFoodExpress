package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;

import java.util.List;

public class VendorShippingOrderAdapter extends ArrayAdapter<Order> {

    public VendorShippingOrderAdapter(Context context, List<Order> orderList) {
        super(context, 0, orderList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.shipping_order_list, null);
            viewHolder.orderId = convertView.findViewById(R.id.textView38);
            viewHolder.orderDate = convertView.findViewById(R.id.textView40);
            viewHolder.orderTotalAmount = convertView.findViewById(R.id.textView39);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert order != null;
        String orderUUID = "Order ID: " + order.getOrderUuid();
        String totalAmount = "Total: " + order.getTotalAmount();
        viewHolder.orderId.setText(orderUUID);
        viewHolder.orderTotalAmount.setText(totalAmount);
        viewHolder.orderDate.setText(order.getOrderDate());

        return convertView;
    }

    static class ViewHolder {
        TextView orderId, orderDate, orderTotalAmount;
    }
}
