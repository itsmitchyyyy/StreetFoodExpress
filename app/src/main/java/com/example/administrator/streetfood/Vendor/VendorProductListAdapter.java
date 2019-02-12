package com.example.administrator.streetfood.Vendor;

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

import java.util.List;

public class VendorProductListAdapter extends ArrayAdapter<Product> {

    public VendorProductListAdapter(Context context, List<Product> product) {
        super(context, 0, product);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.take_order, null);
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

        return super.getView(position, convertView, parent);
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        Button btnUpdate, btnDelete;
    }
}
