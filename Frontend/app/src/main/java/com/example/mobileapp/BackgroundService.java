package com.example.mobileapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mobileapp.networking.NotificationService;
import com.example.mobileapp.networking.RetrofitClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mobileapp.Notification_Popup.CHANNEL_1_ID;


public class BackgroundService extends Service {

    private NotificationManagerCompat notificationManager;
    private NotificationService notificationService;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Started Service Background", Toast.LENGTH_SHORT).show();
        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                while (true) {
                    startService();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        try {
            asyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    public void startService () {
        notificationService = RetrofitClient.getRetrofitInstance().create(NotificationService.class);
        Call<List<com.example.mobileapp.model.Notification>> call = notificationService.getNotificationFromUser("bruno@mail.com");
        call.enqueue(new Callback<List<com.example.mobileapp.model.Notification>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<com.example.mobileapp.model.Notification>> call, Response<List<com.example.mobileapp.model.Notification>> response) {
                response.body().forEach(notification -> {
                    createNotification(notification.getNotificationType(), notification.getNotificationMessage(), notification.getTripName());
                });

            }

            @Override
            public void onFailure(Call<List<com.example.mobileapp.model.Notification>> call, Throwable t) {

            }
        });
    }
    public void createNotification (int type, String message, String groupName) {
        notificationManager = NotificationManagerCompat.from(this);

        Intent activityIntent = new Intent(this, Notifications_Screen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        String notificationType;
        String tripOrTransaction;

        switch (type) {
            case 0:
                notificationType = "New Transaction";
                tripOrTransaction = "Transaction";
                break;
            case 1:
                notificationType = "Transaction deletion";
                tripOrTransaction = "Transaction";
                break;
            case 2:
                notificationType = "Trip invite";
                tripOrTransaction = "Trip";
                break;
            case 3:
                notificationType = "Removed from Trip";
                tripOrTransaction = "Trip";
                break;
            case 4:
                notificationType = "Admin promotion";
                tripOrTransaction = "Trip";
                break;
            default:
                notificationType = "Nothing happend";
                tripOrTransaction = "None";
                break;
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationType) //TODO Wird durch IF Abfrage nach Typ geändert
                .setContentText(groupName)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message) //TODO
                        .setBigContentTitle(groupName) //TODO
                        .setSummaryText(tripOrTransaction)) //TODO
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setColor(Color.parseColor("#C5B358"))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
