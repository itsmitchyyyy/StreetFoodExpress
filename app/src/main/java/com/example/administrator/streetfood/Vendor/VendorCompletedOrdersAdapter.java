package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;

import java.util.List;

public class VendorCompletedOrdersAdapter extends ArrayAdapter<Order> {

    public VendorCompletedOrdersAdapter(Context context, List<Order> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);

        Log.d("uuid", order.getOrderUuid());

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.completed_order_list, null);
            viewHolder.orderId = convertView.findViewById(R.id.completedTextView38);
            viewHolder.orderDate = convertView.findViewById(R.id.completedTextView40);
            viewHolder.orderTotalAmount = convertView.findViewById(R.id.completedTextView39);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        assert order != null;
//        String orderUUID = "Order ID: " + order.getOrderUuid();
        String totalAmount = "Total: " + order.getTotalAmount();
        viewHolder.orderId.setText(order.getOrderUuid());
        viewHolder.orderTotalAmount.setText(totalAmount);
        viewHolder.orderDate.setText(order.getOrderDate());

        return  convertView;
    }

    static class ViewHolder {
        TextView orderId, orderDate, orderTotalAmount;
    }
}
