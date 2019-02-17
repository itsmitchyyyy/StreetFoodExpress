package com.example.administrator.streetfood.Vendor;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.administrator.streetfood.Category.Category;
import com.example.administrator.streetfood.Category.CategoryAdapter;
import com.example.administrator.streetfood.Category.CategoryServer;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.Session;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private ImageView productImage;
    private EditText productName, quantityOnHand, productPrice, productDesc;
    private Spinner category;
    public  List<Category> catList = new ArrayList<>();
    private CategoryAdapter categoryArrayAdapter;
    private int catId;
    private Button btnAdd;
    private Uri imagePath;
    private Bitmap bitmap;
    private ProductServer productServer;
    private Product product;
    private View addProductView;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addProductView =  inflater.inflate(R.layout.fragment_add_product, container, false);

        productServer = new ProductServer(getContext());

        productImage = addProductView.findViewById(R.id.imageView6);
        productName = addProductView.findViewById(R.id.editText9);
        productDesc = addProductView.findViewById(R.id.editText15);
        quantityOnHand = addProductView.findViewById(R.id.editText16);
        productPrice = addProductView.findViewById(R.id.editText18);
        category = addProductView.findViewById(R.id.spinner2);
        btnAdd = addProductView.findViewById(R.id.button11);

        setDropDownCategories();

        category.setOnItemSelectedListener(this);

        productImage.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        return addProductView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView6:
                Intent photoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, 1);
                break;
            case R.id.button11:
                Session session = new Session(getContext(), "accountPref");
                int supId = session.getId();
                String prodName = productName.getText().toString();
                String prodPrice = productPrice.getText().toString();
                String qtyOnHand = quantityOnHand.getText().toString();
                String prodDesc = productDesc.getText().toString();
                if (bitmap != null) {
                     product = new Product(supId, catId, prodName, prodDesc,
                            Double.parseDouble(qtyOnHand), Double.parseDouble(prodPrice),
                            imageToString(bitmap));
                } else {
                    product = new Product(supId, catId, prodName, prodDesc,
                            Double.parseDouble(qtyOnHand), Double.parseDouble(prodPrice), null);
                }
                productServer.addProduct(product);
                productName.setText("");
                productPrice.setText("");
                productDesc.setText("");
                quantityOnHand.setText("");
                productImage.setImageResource(R.mipmap.ic_launcher_round);
                bitmap = null;
                break;
        }
    }

    public void setDropDownCategories() {
        catList.clear();
        CategoryServer categoryServer = new CategoryServer(getContext());
        categoryServer.getCategories(new CategoryServer.VolleyCallBack() {
            @Override
            public void onGetCategories(List<Category> categoryList) {
                catList.addAll(categoryList);
                categoryArrayAdapter = new CategoryAdapter(getContext(), android.R.layout.simple_spinner_item, catList);
                categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryArrayAdapter);
                category.setSelection(0);
            }

            @Override
            public void onGetCategory(Category category) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                imagePath = data.getData();
                Picasso.get()
                        .load(imagePath)
                        .fit().centerInside()
                        .rotate(90)
                        .into(productImage);
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NewApi")
    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Category category = categoryArrayAdapter.getItem(position);
        catId = category.getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
