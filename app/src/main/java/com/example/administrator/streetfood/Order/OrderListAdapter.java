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
import com.example.administrator.streetfood.Shared.Server;
import com.example.administrator.streetfood.Shared.Session;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    List<HashMap<String, Integer>> selectedProduct = new ArrayList<>();
    Session session;

    static class ViewHolder {
        ImageView orderImage;
        TextView orderName, orderPrice;
        Button btnDecrement, btnIncrement;
        EditText stepCounter;
    }


    public OrderListAdapter(Context context, ArrayList<Product> resource) {
        super(context, 0, resource);
        currentQuantity = new int[resource.size()];
        session = new Session(context, Server.accountPreferences);
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
                selectedProducts(product, position, currentQuantity[position]);

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
                unselectedProduct(position);

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

    double getTotalAmount(ArrayList<Product> products) {
        double totalAmount = 0;
        for (Product product: products){
            totalAmount+= product.getTotalAmount();
        }
        return totalAmount;
    }

    private void selectedProducts(Product product, int position, int quantity) {
        HashMap<String, Integer> map = new HashMap<>();
        int id = session.getId();
        map.put("prodId", product.getId());
        map.put("customerId", id);
        map.put("position", position);
        map.put("orderQty", quantity);
        map.put("totalAmount", (int) product.getTotalAmount());
        for (HashMap<String, Integer> s: selectedProduct) {
            if (s.get("position").equals(position)) {
                s.put("customerId", id);
                s.put("orderQty", quantity);
                s.put("totalAmount", (int) product.getTotalAmount());
                return;
            }
        }
        selectedProduct.add(map);
    }

    private void unselectedProduct(int position) {
        if (selectedProduct.size() > 0) {
            for (int i = 0; i < selectedProduct.size(); i++) {
               if ((Objects.requireNonNull(selectedProduct.get(i).get("orderQty")).equals(1))
               && Objects.requireNonNull(selectedProduct.get(i).get("position")).equals(position)) {
                   selectedProduct.remove(i);
                   return;
               }
            }
        }
    }

     List<HashMap<String, Integer>> getSelectedProducts() {
        return selectedProduct;
    }
}
