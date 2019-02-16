package com.example.administrator.streetfood.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;

import java.util.List;

public class CustomerOrderListAdapter extends ArrayAdapter<Order> {

    public CustomerOrderListAdapter(Context context, List<Order> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Order order = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout._customer_order_list_layout, null);
            viewHolder.orderUUID = convertView.findViewById(R.id.textView32);
            viewHolder.totalAmount = convertView.findViewById(R.id.textView33);
            viewHolder.orderDate = convertView.findViewById(R.id.textView34);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert order != null;
        String orderUUID = "Order ID: " + order.getOrderUuid();
        String totalAmount = "Total: " + order.getTotalAmount();
        viewHolder.orderUUID.setText(orderUUID);
        viewHolder.totalAmount.setText(totalAmount);
        viewHolder.orderDate.setText(order.getOrderDate());

        return convertView;
    }

    static class ViewHolder {
        TextView orderUUID, totalAmount, orderDate;
    }
}
