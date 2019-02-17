package com.example.administrator.streetfood.Vendor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.administrator.streetfood.Category.Category;
import com.example.administrator.streetfood.Category.CategoryAdapter;
import com.example.administrator.streetfood.Category.CategoryServer;
import com.example.administrator.streetfood.Product.Product;
import com.example.administrator.streetfood.Product.ProductServer;
import com.example.administrator.streetfood.ProductTabFragment;
import com.example.administrator.streetfood.R;
import com.example.administrator.streetfood.Shared.DBConfig;
import com.example.administrator.streetfood.Shared.Session;
import com.example.administrator.streetfood.VendorActivityDrawer;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UpdateProductFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private ImageView productImage;
    private EditText productName, quantityOnHand, productPrice, productDesc;
    private Spinner category;
    public List<Category> catList = new ArrayList<>();
    private CategoryAdapter categoryArrayAdapter;
    private int catId;
    private Button btnUpdate;
    private Uri imagePath;
    private Bitmap bitmap;
    private ProductServer productServer;
    private Product product;

    public UpdateProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View updateProductView =  inflater.inflate(R.layout.fragment_update_product, container, false);

        productServer = new ProductServer(getContext());

        productImage = updateProductView.findViewById(R.id.updateImageView6);
        productName = updateProductView.findViewById(R.id.updateEditText9);
        productDesc = updateProductView.findViewById(R.id.updateEditText15);
        quantityOnHand = updateProductView.findViewById(R.id.updateEditText16);
        productPrice = updateProductView.findViewById(R.id.updateEditText18);
        category = updateProductView.findViewById(R.id.updateSpinner2);
        btnUpdate = updateProductView.findViewById(R.id.updateButton11);

        setDropDownCategories();

        Picasso.get().load(DBConfig.ServerURL + getArguments().getString("prodImage"))
                .fit()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(productImage);
        productName.setText(getArguments().getString("prodName"));
        productDesc.setText(getArguments().getString("prodDesc"));
        quantityOnHand.setText(getArguments().getString("prodQuantity"));
        productPrice.setText(getArguments().getString("prodPrice"));

        category.setOnItemSelectedListener(this);

        productImage.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        return updateProductView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Category category = categoryArrayAdapter.getItem(position);
        catId = category.getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                for (int i = 0; i < categoryArrayAdapter.getCount(); i++) {
                    Category categorys = categoryArrayAdapter.getItem(i);
                    if (getArguments().getString("catId").equals(categorys.getId())) {
                        catId = categorys.getId();
                        category.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onGetCategory(Category category) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateImageView6:
                Intent photoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent, 1);
                break;
            case R.id.updateButton11:
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
                    product.setId(Integer.parseInt(getArguments().getString("prodId")));
                } else {
                    product = new Product(supId, catId, prodName, prodDesc,
                            Double.parseDouble(qtyOnHand), Double.parseDouble(prodPrice), null);
                    product.setId(Integer.parseInt(getArguments().getString("prodId")));
                }
                productServer.updateProduct(product);
                productName.setText("");
                productPrice.setText("");
                productDesc.setText("");
                quantityOnHand.setText("");
                productImage.setImageResource(R.mipmap.ic_launcher_round);
                bitmap = null;
                ((VendorActivityDrawer)getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.vendorContentFrame, new ProductTabFragment())
                        .commit();
                break;
        }
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

}
