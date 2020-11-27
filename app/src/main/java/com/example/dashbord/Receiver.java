 package com.example.dashbord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

 public class Receiver extends BroadcastReceiver {
     @Override
     public void onReceive(Context context, Intent intent) {
         String Title=intent.getStringExtra("title");
         String Det=intent.getStringExtra("Dets");
         NotificationH notificationHelper = new NotificationH(context);
         NotificationCompat.Builder nb = notificationHelper.nbuilder(Title,Det);
         notificationHelper.getM().notify(1, nb.build());
         //Toast.makeText(context,"Reminder Set!",Toast.LENGTH_LONG).show();
     }
 }
