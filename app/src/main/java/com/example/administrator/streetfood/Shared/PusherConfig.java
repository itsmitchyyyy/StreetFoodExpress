package com.example.administrator.streetfood.Shared;

import android.content.Context;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;


public class PusherConfig {

    Context context;

    public PusherConfig() {
    }

    public PusherConfig(Context context) {
        this.context = context;
    }

    private Pusher setupPusher(){
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        return new Pusher("eb6ffe9cf06d0c047e3a", options);
    }

    public Channel subscribeChannel(String channelName) {
        return setupPusher().subscribe(channelName);
    }

    public void connectPusher() {
        setupPusher().connect();
    }
}
