package com.example.administrator.streetfood.Shared;

import android.content.Context;
import android.telephony.SmsManager;

public class SendMessage {

    Context context;

    public SendMessage() {
    }

    public SendMessage(Context ctx) {
        context = ctx;
    }

    public void sendMessage(String text, String phone) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, text, null, null);
    }
}
