package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class New_Transaction_Screen extends AppCompatActivity {


    Spinner currencyDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction_screen);

    }
}
