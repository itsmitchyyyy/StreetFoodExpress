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

public class VendorCompletedOrdersListAdapter extends ArrayAdapter<Order> {

    private ProductServer productServer;

    public VendorCompletedOrdersListAdapter(Context context, List<Order> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        Order order = getItem(position);

        productServer = new ProductServer(getContext());

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vendor_completed_order_list_item, null);
            viewHolder.productImage = convertView.findViewById(R.id.completedOrderListItemImageView8);
            viewHolder.productName = convertView.findViewById(R.id.completedOrderListItemTextView36);
            viewHolder.productTotal = convertView.findViewById(R.id.completedOrderListItemTextView37);
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
                Picasso.get().load(DBConfig.ServerURL + product.getProdImage())
                        .fit()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(viewHolder.productImage);
                viewHolder.productName.setText(product.getProdName());
                viewHolder.productTotal.setText(Double.toString(order.getTotalAmount()));
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productName, productTotal;
    }
}
