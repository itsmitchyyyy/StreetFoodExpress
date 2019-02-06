package com.example.administrator.streetfood.Shared;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.administrator.streetfood.R;

public class CustomProgressHorizontalDialog {

    private  CustomProgressHorizontalDialog progressDialog = null;
    private Dialog pDialog;
    private ProgressBar progressBar;


    public CustomProgressHorizontalDialog getInstance(){
        if(progressDialog == null){
            progressDialog = new CustomProgressHorizontalDialog();
        }
        return progressDialog;
    }

    public void showProgress(Context context) {
        pDialog = new Dialog(context);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_dialog_horizontal);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        progressBar = pDialog.findViewById(R.id.progressBar2);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    public void setMax(int max) {
        progressBar.setMax(max);
    }

    public void setProgress(int progress){
        progressBar.incrementProgressBy(progress);
    }

    public Integer getProgress(){
        return progressBar.getProgress();
    }

    public void hideProgress() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
