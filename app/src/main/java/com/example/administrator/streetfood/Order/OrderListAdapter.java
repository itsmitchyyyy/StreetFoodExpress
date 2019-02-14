package com.example.administrator.streetfood.Order;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class OrderListAdapter extends ArrayAdapter<Product> {

    private int editingPosition;

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    public ArrayList<Order> list;
    OnDataChangeListener mOnDataChangeListener;
    int[] currentQuantity;

    static class ViewHolder {
        ImageView orderImage;
        TextView orderName, orderPrice;
        Button btnDecrement, btnIncrement;
        EditText stepCounter;
    }


    public OrderListAdapter(Context context, ArrayList<Product> resource) {
        super(context, 0, resource);
        currentQuantity = new int[resource.size()];
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.order_layout, null);
            viewHolder.orderImage = convertView.findViewById(R.id.imageView2);
            viewHolder.orderName = convertView.findViewById(R.id.textView7);
            viewHolder.orderPrice = convertView.findViewById(R.id.textView11);
            viewHolder.btnDecrement = convertView.findViewById(R.id.button4);
            viewHolder.btnIncrement = convertView.findViewById(R.id.button6);
            viewHolder.stepCounter = convertView.findViewById(R.id.editText5);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.get()
                .load(DBConfig.ServerURL + product.getProdImage())
                .resize(100, 100)
                .centerCrop()
                .rotate(90)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(viewHolder.orderImage);
        viewHolder.orderName.setText(Objects.requireNonNull(product).getProdName());
        viewHolder.orderPrice.setText(String.format(Locale.getDefault(),"%.2f", product.getProdPrice()));

        viewHolder.btnIncrement.setOnClickListener(v -> {
            currentQuantity[position] = Integer.parseInt(viewHolder.stepCounter.getText().toString());

            if(currentQuantity[position] <  100) {
                currentQuantity[position] += 1;

                viewHolder.stepCounter.removeTextChangedListener(watcher);
                viewHolder.stepCounter.setText(String.format(Locale.getDefault(), "%d", currentQuantity[position]));
                viewHolder.stepCounter.setOnFocusChangeListener((v1, hasFocus) -> {
                    if (hasFocus) editingPosition = position;
                });
                viewHolder.stepCounter.addTextChangedListener(watcher);

                product.setTotalAmount(product.getProdPrice() * currentQuantity[position]);

            }

            mOnDataChangeListener.onDataChanged();
        });

        viewHolder.btnDecrement.setOnClickListener(v -> {
            currentQuantity[position] = Integer.parseInt(viewHolder.stepCounter.getText().toString());

            if(currentQuantity[position] > 0) {
                currentQuantity[position] -= 1;

                viewHolder.stepCounter.removeTextChangedListener(watcher);
                viewHolder.stepCounter.setText(String.format(Locale.getDefault(), "%d", currentQuantity[position]));
                viewHolder.stepCounter.setOnFocusChangeListener((v1, hasFocus) -> {
                    if (hasFocus) editingPosition = position;
                });
                viewHolder.stepCounter.addTextChangedListener(watcher);

                product.setTotalAmount(product.getProdPrice() * currentQuantity[position]);

            }

            mOnDataChangeListener.onDataChanged();
        });

        return convertView;
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            currentQuantity[editingPosition] = Integer.parseInt(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public double getTotalAmount(ArrayList<Product> products) {
        double totalAmount = 0;
        for (Product product: products){
            totalAmount+= product.getTotalAmount();
        }
        return totalAmount;
    }
}
