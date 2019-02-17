package com.example.administrator.streetfood.Vendor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.example.administrator.streetfood.VendorActivityDrawer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VendorProductListAdapter extends ArrayAdapter<Product> {

    private ProductServer productServer;

    public VendorProductListAdapter(Context context, List<Product> product) {
        super(context, 0, product);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        productServer = new ProductServer(getContext());

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

        viewHolder.btnUpdate.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("prodId", Integer.toString(product.getId()));
            b.putString("prodImage", product.getProdImage());
            b.putString("prodName", product.getProdName());
            b.putString("prodDesc", product.getProdDesc());
            b.putString("prodPrice", Double.toString(product.getProdPrice()));
            b.putString("prodQuantity", Double.toString(product.getProdQty()));
            b.putString("catId", Integer.toString(product.getCategoryId()));
            UpdateProductFragment updateProductFragment = new UpdateProductFragment();
            updateProductFragment.setArguments(b);
            ((VendorActivityDrawer)getContext()).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.vendorContentFrame, updateProductFragment)
                    .commit();
        });

        viewHolder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Product Deletion")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes", (dialog, which) -> {
                productServer.deleteProduct(product.getId());
            })
            .setNegativeButton("No", null)
                    .show();
        });

        return convertView;
    }



    static class ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        Button btnUpdate, btnDelete;
    }
}
