package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Settings_Screen extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private Button profileButton;
    private Button logOutButton;
    private TextView appInfo;
    private TextView name;
    private TextView email;
private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        profileButton = findViewById(R.id.profileButton);
        logOutButton = findViewById(R.id.logoutButton);
        appInfo = findViewById(R.id.appInfo);
        name = findViewById(R.id.settings_name);
        email = findViewById(R.id.settings_email);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        /**
         * Sets name and email to user name and user email
         */
        name.setText(sharedPreferences.getString("Name",""));
        email.setText(sharedPreferences.getString("E-Mail", ""));

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_Screen.this, Account_Settings_Screen.class));
            }
        });

        /**
         * Removes shared prefs on logout
         */
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("Name");
                editor.remove("Password");
                editor.remove("E-Mail");
                editor.apply();
                finish();
                startActivity(new Intent(Settings_Screen.this, Start_Screen.class));
            }
        });


        /**
         * Opens app info screen
         */
        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Settings_Screen.this, App_Info_Screen.class));
            }
        });
    }

    /**
     * Returns to previous activity
     * @param view
     */
    public void backButton(View view) {
        finish();
        startActivity(new Intent(this,Trip_Overview_Screen.class));
    }
}
