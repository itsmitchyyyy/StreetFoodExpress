package com.example.administrator.streetfood.Vendor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorProductListAdapter extends ArrayAdapter<Product> {

    public VendorProductListAdapter(Context context, List<Product> product) {
        super(context, 0, product);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

       ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vendor_product_list, null);
            viewHolder.productImage = convertView.findViewById(R.id.imageView7);
            viewHolder.productName = convertView.findViewById(R.id.textView24);
            viewHolder.productPrice = convertView.findViewById(R.id.textView28);
            viewHolder.productQuantity = convertView.findViewById(R.id.textView31);
            viewHolder.btnUpdate = convertView.findViewById(R.id.button12);
            viewHolder.btnDelete = convertView.findViewById(R.id.button13);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.get()
                .load(DBConfig.ServerURL + product.getProdImage())
                .centerCrop()
                .fit()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(viewHolder.productImage);

        viewHolder.productName.setText(product.getProdName());
        viewHolder.productPrice.setText(Double.toString(product.getProdPrice()));
        viewHolder.productQuantity.setText(Double.toString(product.getProdQty()));

        return convertView;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        Button btnUpdate, btnDelete;
    }
}
