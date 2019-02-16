package com.example.administrator.streetfood.Vendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorTakeOrderAdapter extends ArrayAdapter<Order> {

    private double totalAmount;

    public VendorTakeOrderAdapter(Context context, List<Order> orderList) {
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
        productServer.getProduct(order.getProdId(), new ProductServer.VolleyCallBack() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onProductQuery(List<Product> list) {

            }

            @Override
            public void onGetProduct(Product product) {
                Picasso.get()
                        .load(DBConfig.ServerURL + product.getProdImage())
                        .centerCrop()
                        .fit()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(viewHolder.productImage);
                viewHolder.productName.setText(product.getProdName());
                viewHolder.orderQuantity.setText(Double.toString(order.getOrderQty()));
                viewHolder.totalAmount.setText(Double.toString(order.getTotalAmount()));
            }
        });


        return convertView;
    }

    public double getTotalAmount(List<Order> orders) {
        for (Order order: orders) {
           totalAmount += order.getTotalAmount();
        }
        return totalAmount;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productName, orderQuantity, totalAmount;
    }
}
