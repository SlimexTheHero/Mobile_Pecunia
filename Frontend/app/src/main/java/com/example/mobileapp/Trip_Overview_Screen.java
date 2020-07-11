package com.example.mobileapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.mobileapp.model.Trip;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TripService;
import com.example.mobileapp.networking.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trip_Overview_Screen extends AppCompatActivity {

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripImages = new ArrayList<>();
    private ArrayList<String> mTripDuration = new ArrayList<>();
    private ArrayList<String> mTripId = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTripParticipants = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTransactions = new ArrayList<>();
    private ArrayList<ArrayList<String>> mAdmins = new ArrayList<>();
    private ImageView settings;
    private ImageView notifications;
    private TripService tripService;
    private Loading_Dialog loading_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_main_screen);
        //initImageBitmaps();

        loading_dialog = new Loading_Dialog(Trip_Overview_Screen.this);
        tripService = RetrofitClient.getRetrofitInstance().create(TripService.class);
        settings = findViewById(R.id.settings_button);
        notifications = findViewById(R.id.notifications);

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Trip_Overview_Screen.this, Notifications_Screen.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Trip_Overview_Screen.this, Settings_Screen.class));
            }
        });
        buildTripsTable();

        loading_dialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_dialog.dismissDialog();
            }
        }, 2000);
    }


    public void createNewTrip(View view) {
        finish();
        startActivity(new Intent(this, New_Trip_Screen.class));
    }

    private void buildTripsTable() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("E-Mail", "");
        Call<List<Trip>> call = tripService.getTripsByUser(userEmail);
        call.enqueue(new Callback<List<Trip>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                List<Trip> trips = response.body();
                if (response.body() == null) {
                    initRecyclerView();
                } else {
                    trips.forEach(trip -> {
                        mTripImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
                        mTripNames.add(trip.getTripName());
                        mTripDuration.add(trip.getTripDuration());
                        mTripId.add(trip.getTripId());
                        mTripParticipants.add((ArrayList<String>) trip.getTripParticipants());
                        mAdmins.add((ArrayList<String>) trip.getAdmins());
                        mTransactions.add((ArrayList<String>) trip.getTransactions());

                    });
                    initRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Make sure to have a connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initImageBitmaps() {

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
        Recycler_View_Adapter_Group adapter = new Recycler_View_Adapter_Group(mTripNames, mTripImages,
                mTripDuration, mTripId, mTripParticipants, mTransactions, mAdmins, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(close);

    }

}
