package com.example.administrator.streetfood;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class NetworkSchedulerService extends JobService implements NetworkReciever.NetworkRecieverLister {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private NetworkReciever networkReciever;

    @Override
    public void onCreate() {
        super.onCreate();
        networkReciever = new NetworkReciever(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        registerReceiver(networkReciever, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        unregisterReceiver(networkReciever);
        return false;
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        String msg = isConnected ? "Connection Established" : "No internet connection";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
