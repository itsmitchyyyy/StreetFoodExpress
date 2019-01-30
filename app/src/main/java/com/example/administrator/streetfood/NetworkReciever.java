package com.example.administrator.streetfood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReciever extends BroadcastReceiver {

    private NetworkRecieverLister networkRecieverLister;

    NetworkReciever(NetworkRecieverLister listener) {
        networkRecieverLister = listener;
    }

    public interface NetworkRecieverLister {
        void onNetworkChanged(boolean isConnected);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        networkRecieverLister.onNetworkChanged(isConnected(context));
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

}
