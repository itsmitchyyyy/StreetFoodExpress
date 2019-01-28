package com.example.administrator.streetfood;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderListAdapter extends ArrayAdapter<Order> {

    public static ArrayList<Order> list;

    static class ViewHolder {
        ImageView orderImage;
        TextView orderName, orderPrice;
        Button btnDecrement, btnIncrement;
        EditText stepCounter;
    }

    public OrderListAdapter(Context context, ArrayList<Order> resource) {
        super(context, 0, resource);
    }

//    public getOrderAmount(){
//
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Order order = getItem(position);

        final ViewHolder viewHolder;
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

        viewHolder.orderImage.setImageResource(order.getProductImage());
        viewHolder.orderName.setText(order.getOrderName());
        viewHolder.orderPrice.setText(String.format("%.2f", order.getProductPrice()));
        viewHolder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(viewHolder.stepCounter.getText().toString()) + 1;;
                viewHolder.stepCounter.setText(Integer.toString(currentQuantity));

                double amountOrder = order.getProductPrice() * currentQuantity;
                order.setOrderAmount(amountOrder);

                notifyDataSetChanged();

            }
        });
        viewHolder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(viewHolder.stepCounter.getText().toString()) - 1;
                viewHolder.stepCounter.setText(Integer.toString(currentQuantity));

                double amountOrder = order.getProductPrice() * currentQuantity;
                order.setOrderAmount(amountOrder);

                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
