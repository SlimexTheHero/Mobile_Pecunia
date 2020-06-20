package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import java.util.ArrayList;

public class Trip_Overview_Screen extends AppCompatActivity {

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripImages = new ArrayList<>();
    private ArrayList<String> mTripDuration = new ArrayList<>();
    private ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main_screen);
        initImageBitmaps();
        settings = findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Trip_Overview_Screen.this, Settings_Screen.class));
            }
        });

    }

    public void createNewTrip(View view) {
        startActivity(new Intent(this, New_Trip_Screen.class));
    }

    private void initImageBitmaps(){

        mTripImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mTripNames.add("Trondheim");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mTripNames.add("Portugal");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.redd.it/j6myfqglup501.jpg");
        mTripNames.add("Max. 25 Zeichen, wegen Space");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mTripNames.add("Mahahual");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.redd.it/k98uzl68eh501.jpg");
        mTripNames.add("Frozen Lake");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.redd.it/glin0nwndo501.jpg");
        mTripNames.add("White Sands Desert");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.redd.it/obx4zydshg601.jpg");
        mTripNames.add("Austrailia");
        mTripDuration.add("21.05 - 28.08");

        mTripImages.add("https://i.imgur.com/ZcLLrkY.jpg");
        mTripNames.add("Washington");
        mTripDuration.add("21.05 - 28.08");

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Recycler_View_Adapter_Group adapter = new Recycler_View_Adapter_Group(mTripNames, mTripImages, mTripDuration, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed(){
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(close);

    }

}
