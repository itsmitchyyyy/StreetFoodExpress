package com.example.administrator.streetfood.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.streetfood.Order.Order;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class CustomerOrderNotificationAdapter extends ArrayAdapter<Order> {

    private ProductServer productServer;

    public CustomerOrderNotificationAdapter(Context context, int resource, List<Order> list) {
        super(context, 0, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = getItem(position);

        productServer = new ProductServer();

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.customer_order_listview_item, null);
            viewHolder.orderListViewImage = convertView.findViewById(R.id.orderListViewImage);
            viewHolder.productName = convertView.findViewById(R.id.orderProductName);
            viewHolder.productQty = convertView.findViewById(R.id.orderProductQty);
            viewHolder.productAmount = convertView.findViewById(R.id.orderProductAmount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

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
                        .load(product.getProdImage())
                        .centerCrop()
                        .resize(50, 50)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(viewHolder.orderListViewImage);
                viewHolder.productName.setText(product.getProdName());
                viewHolder.productQty.setText(String.format(Locale.getDefault(), ".%2f", order.getOrderQty()));
                viewHolder.productAmount.setText(String.format(Locale.getDefault(), ".%2f", order.getTotalAmount()));
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView orderListViewImage;
        TextView productName, productQty, productAmount;
    }
}
