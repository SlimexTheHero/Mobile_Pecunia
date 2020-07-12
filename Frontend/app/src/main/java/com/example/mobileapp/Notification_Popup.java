package com.example.mobileapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class Notification_Popup extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    private boolean permissionGranted;

    @Override
    public void onCreate() {
        super.onCreate();
        askForNotification();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Notifications for Transactions",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setLightColor(Color.parseColor("#C5B358"));
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Notifications for Trips",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setLightColor(Color.parseColor("#C5B358"));
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

    private void askForNotification () {
        MaterialAlertDialogBuilder permission = new MaterialAlertDialogBuilder(this);
        permission.setTitle("Receive Notifications");
        permission.setMessage("Do you want to receive Notifications about Trips and Transactions? \n");
        permission.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionGranted = true;
                dialog.dismiss();
            }
        });
        permission.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionGranted = false;
                dialog.dismiss();
            }
        });
        permission.setCancelable(false);
        permission.show();
    }

}
