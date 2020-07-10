package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.mobileapp.Notification_Popup.CHANNEL_1_ID;
import static com.example.mobileapp.Notification_Popup.CHANNEL_2_ID;

public class Notifications_Screen extends AppCompatActivity {

    private ArrayList<Integer> notificationType = new ArrayList<>();
    private ArrayList<String> notificationTitle = new ArrayList<>();
    private ArrayList<String> notificationGroup = new ArrayList<>();
    private ArrayList<String> notificationMessage = new ArrayList<>();
    private TextView testButtonOne;
    private TextView testButtonTwo;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_screen);

        initNotifications();

        notificationManager = NotificationManagerCompat.from(this);

        testButtonOne = findViewById(R.id.notifications_test_one);
        testButtonTwo = findViewById(R.id.notifications_test_two);

        testButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1();
            }
        });

        testButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel2();
            }
        });

    }

    public void sendOnChannel1() {

        Intent activityIntent = new Intent(this, Notifications_Screen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Transaction oder Delete Transaction") //TODO Wird durch IF Abfrage nach Typ geändert
                .setContentText("Gruppenname")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hier steht die gesamte Transaktion als Notification. Hier steht so viel scheiße drinnen, da es nicht komplett angezeigt werden kann.") //TODO
                        .setBigContentTitle("Hier kommt der Gruppenname hin") //TODO
                        .setSummaryText("Transaction"))
                .setColor(Color.parseColor("#C5B358"))
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2() {
        Intent activityIntent = new Intent(this, Notifications_Screen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_2_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Admin, Removed or Added to Trip") //TODO Wird durch IF Abfrage nach Typ geändert
                .setContentText("Gruppenname")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hier steht ob Gruppe hinzugekommen, Admin geworden oder von einer Grupp entfernt") //TODO
                        .setBigContentTitle("Hier kommt der Gruppenname hin") //TODO
                        .setSummaryText("Trip"))
                .setColor(Color.parseColor("#C5B358"))
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(2, notification);
    }

    private void initNotifications() {

        notificationType.add(0);
        notificationTitle.add("Test 0");
        notificationGroup.add("Group 0");
        notificationMessage.add("This is 0");

        notificationType.add(1);
        notificationTitle.add("Test 1");
        notificationGroup.add("Group 1");
        notificationMessage.add("This is 1");

        notificationType.add(2);
        notificationTitle.add("Test 2");
        notificationGroup.add("Group 2");
        notificationMessage.add("This is 2");

        notificationType.add(3);
        notificationTitle.add("Test 3");
        notificationGroup.add("Group 3");
        notificationMessage.add("This is 3");

        notificationType.add(4);
        notificationTitle.add("Test 4");
        notificationGroup.add("Group 4");
        notificationMessage.add("This is 4");

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_notification);
        Recycler_View_Adapter_Notification adapter_transaction = new Recycler_View_Adapter_Notification(this, notificationType, notificationTitle, notificationGroup, notificationMessage);
        recyclerView.setAdapter(adapter_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void backButton(View v) {
        finish();
    }
}
