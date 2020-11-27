package com.example.dashbord;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationH extends ContextWrapper {
    public static final String ReminderID="ReminderID";
    public static final String channelName="Reminder";
    public static final String ReID="ReID";

    private NotificationManager manager;
    public NotificationH(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel c= new NotificationChannel(ReminderID,channelName, NotificationManager.IMPORTANCE_DEFAULT);
        getM().createNotificationChannel(c);
    }

    protected NotificationManager getM() {
        if(manager==null){
            manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder nbuilder(String title, String Description){
        return  new NotificationCompat.Builder(getApplicationContext(),ReminderID)
                .setContentTitle(title)
                .setContentText(Description)
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setAutoCancel(true);
    }

}
