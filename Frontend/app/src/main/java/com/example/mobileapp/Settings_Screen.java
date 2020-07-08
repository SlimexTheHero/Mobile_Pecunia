package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Settings_Screen extends AppCompatActivity {

    private TextView counter;
    private Button profileButton;
    private Button logOutButton;
    private Button extractsButton;
    private TextView appInfo;

    int trips;
    int transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);


        trips = 5;
        transactions = 10;
        counter = findViewById(R.id.tripTransactionCounter);
        profileButton = findViewById(R.id.profileButton);
        logOutButton = findViewById(R.id.logoutButton);
        extractsButton = findViewById(R.id.extractsButton);
        appInfo = findViewById(R.id.appInfo);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_Screen.this, Account_Settings_Screen.class));
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //And delete tokens
                startActivity(new Intent(Settings_Screen.this, Start_Screen.class));
            }
        });

        extractsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Shows a list of all extracts
                Toast.makeText(Settings_Screen.this, "Needs to be created" , Toast.LENGTH_SHORT).show();
            }
        });

        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_Screen.this, App_Info_Screen.class));
            }
        });

        //Will be replaced by actual amount
        int trips = 5;
        int transactions = 10;
        counter.setText("Active trips: \t\t\t\t\t\t\t\t\t" + trips +"\n" +
                                "Active transactions: \t\t" + transactions);
    }


    public void enableNotification (View view) {
        if(((CheckBox)view).isChecked()) {
            //Activate notifications
        } else {
            //Disable notifications
        }
    }

    public void backButton(View view) {
        finish();
    }
}
