package com.example.administrator.streetfood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Handler;

public class NetworkChange extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        String action = intent.getAction();
        Log.d("action", action);

//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//       final android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        final android.net.NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//
//        if (wifi.isAvailable() || data.isAvailable()) {
//            Log.d("asd", "asd");
//        }

//        InternetCheckerDialog internetCheckerDialog = new InternetCheckerDialog().getInstance();
//        if (isInternetAvailable()){
//            internetCheckerDialog.showDialog(context, "Connection Established", isInternetAvailable());
//        } else {
//            internetCheckerDialog.showDialog(context, "Could not connect to internet", isInternetAvailable());
//            Thread thread = new Thread(){
//                @Override
//                public void run() {
//                    try {
//                        while (true) {
//                            sleep(1000);
//                            internetCheckerDialog.hideDialog();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            thread.start();
//        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress inetAddress = InetAddress.getByName("google.com");
            if (inetAddress.equals("")) {
                return false;
            } else {
                return true;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }
}
