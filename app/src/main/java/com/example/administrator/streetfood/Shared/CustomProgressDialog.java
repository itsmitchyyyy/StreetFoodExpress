package com.example.administrator.streetfood.Shared;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.administrator.streetfood.R;

public class CustomProgressDialog {

    private  CustomProgressDialog progressDialog = null;
    private Dialog pDialog;


    public CustomProgressDialog getInstance(){
        if(progressDialog == null){
            progressDialog = new CustomProgressDialog();
        }
        return progressDialog;
    }

    public void showProgress(Context context) {
        ProgressBar progressBar;
        pDialog = new Dialog(context);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_dialog);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar = pDialog.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    public void hideProgress() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
