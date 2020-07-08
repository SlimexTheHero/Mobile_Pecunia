package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Notifications_Screen extends AppCompatActivity {

    private ArrayList<Integer> notificationType = new ArrayList<>();
    private ArrayList<String> notificationTitle = new ArrayList<>();
    private ArrayList<String> notificationGroup = new ArrayList<>();
    private ArrayList<String> notificationMessage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_screen);

        initNotifications();

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


    public void backButton (View v) {
        finish();
    }
}
