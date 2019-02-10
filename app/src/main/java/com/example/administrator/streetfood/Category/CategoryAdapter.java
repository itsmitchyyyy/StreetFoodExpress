package com.example.administrator.streetfood.Category;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context context, int resourceId, List<Category> catList) {
        super(context, resourceId, catList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);

        TextView label = (TextView) super.getView(position, convertView, parent);

        label.setTextColor(Color.BLACK);

        assert category != null;
        label.setText(category.getCatName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);

        TextView label = (TextView) super.getDropDownView(position, convertView, parent);

        label.setTextColor(Color.BLACK);

        assert category != null;
        label.setText(category.getCatName());

        return label;
    }
}
