package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class App_Info_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info_screen);
    }

    /**
     * Returns to previous activity
     * @param v
     */
    public void backButton (View v) {
        finish();
        startActivity(new Intent(this, Settings_Screen.class));
    }
}
