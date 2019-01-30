package com.example.administrator.streetfood;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NetworkJob {

    private NetworkJob networkJob = null;

    public NetworkJob getInstance() {
        if (networkJob == null) {
            networkJob = new NetworkJob();
        }
        return networkJob;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(Context context) {
        JobInfo jobInfo = new JobInfo.Builder(0 , new ComponentName(context, NetworkSchedulerService.class))
                .setRequiresCharging(false)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(false)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }

}
