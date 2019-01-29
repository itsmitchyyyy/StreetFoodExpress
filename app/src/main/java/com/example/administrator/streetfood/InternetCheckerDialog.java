package com.example.administrator.streetfood;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class InternetCheckerDialog {

    private  InternetCheckerDialog checkerDialog = null;
    private Dialog cDialog;


    public InternetCheckerDialog getInstance(){
        if(checkerDialog == null){
            checkerDialog = new InternetCheckerDialog();
        }
        return checkerDialog;
    }

    public void showDialog(Context context, String message, Boolean status) {
        TextView connectionText;
        cDialog = new Dialog(context);
        cDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cDialog.setContentView(R.layout.progress_dialog);
        cDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        connectionText = cDialog.findViewById(R.id.textView12);
        connectionText.setText(message);
        if(!status){
            connectionText.setTextColor(Color.RED);
        } else {
            connectionText.setTextColor(Color.GREEN);
        }
        connectionText.setVisibility(View.VISIBLE);
        cDialog.setCancelable(false);
        cDialog.setCanceledOnTouchOutside(false);
        cDialog.show();
    }

    public void hideDialog() {
        if (cDialog != null) {
            cDialog.dismiss();
            cDialog = null;
        }
    }
}
