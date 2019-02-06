package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.R;

import java.util.List;

public class VendorListOrderAdapter extends ArrayAdapter<Order> {

    public VendorListOrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vendor_order, null);
            viewHolder.orderImage = convertView.findViewById(R.id.imageView3);
            viewHolder.orderName = convertView.findViewById(R.id.textView15);
            viewHolder.orderDate = convertView.findViewById(R.id.textView16);
            viewHolder.orderTime = convertView.findViewById(R.id.textView17);
            viewHolder.btnTakeOrder = convertView.findViewById(R.id.button9);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.orderImage.setImageResource();
        viewHolder.orderName.setText("asd");
        viewHolder.orderDate.setText(order.getOrderDate());
        viewHolder.orderTime.setText("asdasd");

        return convertView;
    }

    static class ViewHolder {
        ImageView orderImage;
        TextView orderDate, orderTime, orderName;
        Button btnTakeOrder;
    }
}
