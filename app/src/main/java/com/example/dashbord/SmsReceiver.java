package com.example.dashbord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            sendSMS(intent);

        }catch (Exception e) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void sendSMS(Intent intent){
        Bundle bundle = intent.getExtras();
        SmsManager smsManager = SmsManager.getDefault();

        String smsText = bundle.getString("Title")+"\n" +bundle.getString("Message");
        String smsNumber = bundle.getString("Phone");

        //smsManager.sendTextMessage(smsNumber, null, smsText, null, null);

    }
}

