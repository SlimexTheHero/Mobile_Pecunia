package com.example.mobileapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobileapp.networking.NotificationService;
import com.example.mobileapp.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mobileapp.Notification_Popup.CHANNEL_1_ID;

public class Notifications_Screen extends AppCompatActivity {

    private ArrayList<String> notificationId= new ArrayList<>();
    private ArrayList<Integer> notificationType = new ArrayList<>();
    private ArrayList<String> notificationTitle = new ArrayList<>();
    private ArrayList<String> notificationTripName = new ArrayList<>();
    private ArrayList<String> notificationTripId = new ArrayList<>();
    private ArrayList<String> notificationTransactionId = new ArrayList<>();
    private ArrayList<String> notificationMessage = new ArrayList<>();
    private ArrayList<String> notificationUserId = new ArrayList<>();

    private TextView testButtonOne;
    private NotificationManagerCompat notificationManager;
    private NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_screen);
        notificationService = RetrofitClient.getRetrofitInstance().create(NotificationService.class);
        initNotifications();

        notificationManager = NotificationManagerCompat.from(this);

        testButtonOne = findViewById(R.id.notifications_test_one);

        testButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1();
            }
        });


    }

    public void sendOnChannel1() {

        Intent activityIntent = new Intent(this, Notifications_Screen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Type der Notification, siehe 0-4") //TODO Wird durch IF Abfrage nach Typ geändert
                .setContentText("Gruppenname")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hier steht die gesamte Transaktion als Notification. Hier steht so viel scheiße drinnen, da es nicht komplett angezeigt werden kann.") //TODO
                        .setBigContentTitle("Hier kommt der Gruppenname hin") //TODO
                        .setSummaryText("Transaction oder Trip")) //TODO
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setColor(Color.parseColor("#C5B358"))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();


        notificationManager.notify(1, notification);
    }

    private void requestNotifications(String userId){
        Call<List<com.example.mobileapp.model.Notification>> call = notificationService.getNotificationFromUser(userId);
        call.enqueue(new Callback<List<com.example.mobileapp.model.Notification>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<com.example.mobileapp.model.Notification>> call,
                                   Response<List<com.example.mobileapp.model.Notification>> response) {
                response.body().forEach(notification -> {
                    notificationId.add(notification.getNotificationId());
                    notificationType.add(notification.getNotificationType());
                    notificationMessage.add(notification.getNotificationMessage());
                    notificationTripName.add(notification.getTripName());
                    notificationTripId.add(notification.getTripId());
                    notificationTransactionId.add(notification.getTransactionId());
                    notificationUserId.add(notification.getUserId());
                });
            }

            @Override
            public void onFailure(Call<List<com.example.mobileapp.model.Notification>> call, Throwable t) {

            }
        });
    }


    private void initNotifications() {

        notificationType.add(0);
        notificationTitle.add("Test 0");
        notificationTripName.add("Group 0");
        notificationMessage.add("This is 0");

        notificationType.add(1);
        notificationTitle.add("Test 1");
        notificationTripName.add("Group 1");
        notificationMessage.add("This is 1");

        notificationType.add(2);
        notificationTitle.add("Test 2");
        notificationTripName.add("Group 2");
        notificationMessage.add("This is 2");

        notificationType.add(3);
        notificationTitle.add("Test 3");
        notificationTripName.add("Group 3");
        notificationMessage.add("This is 3");

        notificationType.add(4);
        notificationTitle.add("Test 4");
        notificationTripName.add("Group 4");
        notificationMessage.add("This is 4");

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_notification);
        Recycler_View_Adapter_Notification adapter_transaction = new Recycler_View_Adapter_Notification(this, notificationType, notificationTitle, notificationTripName, notificationMessage);
        recyclerView.setAdapter(adapter_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void backButton(View v) {
        finish();
        startActivity(new Intent(this,Trip_Overview_Screen.class));
    }
}
