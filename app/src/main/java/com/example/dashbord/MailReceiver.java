package com.example.dashbord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MailReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String email = bundle.getString("Usermail");
        String subject =bundle.getString("Tiv");
        String message = "Starts in an hour\n"+ "Details of the event :"+bundle.getString("Cmntv")+"\n"+"Date: "+bundle.getString("datev") +"\nTime: "+bundle.getString("timev");
        com.example.dashbord.SendMailTask sm = new com.example.dashbord.SendMailTask(context, email, subject, message);
        sm.execute();
    }
}
