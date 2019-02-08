package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.R;

import java.util.List;

public class VendorTakeOrderAdapter extends ArrayAdapter<Order> {

    public VendorTakeOrderAdapter(Context context,  List<Order> orderList) {
        super(context, 0, orderList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);

        ProductServer productServer = new ProductServer(getContext());

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.take_order, null);
            viewHolder.productImage = convertView.findViewById(R.id.imageView5);
            viewHolder.productName = convertView.findViewById(R.id.textView20);
            viewHolder.orderQuantity = convertView.findViewById(R.id.textView21);
            viewHolder.totalAmount = convertView.findViewById(R.id.textView22);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert order != null;
        viewHolder.orderQuantity.setText(Double.toString(order.getOrderQty()));
        viewHolder.totalAmount.setText(Double.toString(order.getTotalAmount()));

        return convertView;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productName, orderQuantity, totalAmount;
    }
}
