package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobileapp.model.Notification;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TripService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Single_Trip extends AppCompatActivity {
    private TextView tripName;
    private ImageView tripImage;
    private TextView tripDuration;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MemberFragment memberFragement;
    private TransactionFragment transactionFragement;
    private String tripId;
    private String eMail;
    private ArrayList<String> participants;
    private ArrayList<String> transactions;
    private ArrayList<String> admins;
    private ImageView leaveTrip;
    private TripService tripService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        eMail = sharedPreferences.getString("E-Mail", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_trip);
        tripService = RetrofitClient.getRetrofitInstance().create(TripService.class);

        tripImage = findViewById(R.id.trip_image);
        tripName = findViewById(R.id.trip_name);
        tripDuration = findViewById(R.id.trip_duration);
        viewPager = findViewById(R.id.tab_view);
        tabLayout = findViewById(R.id.tab_layout);
        leaveTrip = findViewById(R.id.leave_trip_image);

        memberFragement = new MemberFragment();
        transactionFragement = new TransactionFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(memberFragement, "Member");
        viewPagerAdapter.addFragment(transactionFragement, "Transaction");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.person_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.transaction_bill);

        Bundle bundle = getIntent().getExtras();

        int image = bundle.getInt("Image");
        String duration = bundle.getString("Duration").toString();
        String name = bundle.getString("Name").toString();
        tripId = bundle.getString("Id");
        admins = bundle.getStringArrayList("Admins");
        participants = bundle.getStringArrayList("Participants");
        transactions = bundle.getStringArrayList("Transactions");


        Glide.with(this).asBitmap().load(image).into(tripImage);
        tripName.setText(name);
        tripDuration.setText(duration);

        leaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveTripButton();
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

    /**
     * Leaves trip and removes user from it
     */
    public void leaveTripButton() {
        if(admins.size()!=1) {
            MaterialAlertDialogBuilder leaveTrip = new MaterialAlertDialogBuilder(this);
            leaveTrip.setTitle("Leave Trip");

            leaveTrip.setMessage("Do you want to leave this trip? Be aware that once you leave this trip only an admin can add you back.");
            leaveTrip.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Notification notification = new Notification();
                    Call<String> call = tripService.deleteUserFromTrip(eMail, tripId, notification);
                    call.enqueue(new Callback<String>() {

                        /**
                         * Requests backend to remove user from trip
                         * @param call
                         * @param response
                         */
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    finish();
                    startActivity(new Intent(getApplicationContext(), Trip_Overview_Screen.class));
                }
            });
            leaveTrip.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            leaveTrip.show();
        }else{
            Toast.makeText(getApplicationContext(),"Last admin can´t leave the trip",Toast.LENGTH_LONG).show();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }


    public String getiD() {
        return tripId;
    }

    public String geteMail() {
        return eMail;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public ArrayList<String> getAdmins() {
        return admins;
    }

    public String getTripName() {
        return tripName.getText().toString();
    }
}
