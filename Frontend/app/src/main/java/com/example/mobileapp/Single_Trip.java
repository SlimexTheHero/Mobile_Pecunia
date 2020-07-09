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
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Single_Trip extends AppCompatActivity {

    private TextView tripName;
    private ImageView tripImage;
    private TextView tripDuration;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MemberFragment memberFragement;
    private TransactionFragment transactionFragement;
    private String tripId;
    private ArrayList<String> participants;
    private ArrayList<String> transactions;
    private ArrayList<String> admins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_trip);

        tripImage = findViewById(R.id.trip_image);
        tripName = findViewById(R.id.trip_name);
        tripDuration = findViewById(R.id.trip_duration);
        viewPager = findViewById(R.id.tab_view);
        tabLayout = findViewById(R.id.tab_layout);

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

        String image = bundle.getString("Image").toString();
        String duration = bundle.getString("Duration").toString();
        String name = bundle.getString("Name").toString();
        tripId = bundle.getString("Id");

        Glide.with(this).asBitmap().load(image).into(tripImage);
        tripName.setText(name);
        tripDuration.setText(duration);
    }

    public void backButton(View view) {
        finish();
    }

    public void leaveTripButton(View view) {
        MaterialAlertDialogBuilder leaveTrip = new MaterialAlertDialogBuilder(this);
        leaveTrip.setTitle("Leave Trip");

        //Kommende IF Abfrage ob
        //1. Man ein Admin ist
        //2. Eine Aktive Transaktion hat
        //Punkte 1 und 2 können ignoriert werden falls der Trip in der Vergangenheit liegt.

        leaveTrip.setMessage("Do you want to leave this trip? Be aware that once you leave this trip only an admin can add you back.");
        leaveTrip.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Delete User from Group, so he cant see it anymore
                finish();
            }
        });
        leaveTrip.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        leaveTrip.show();
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

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public ArrayList<String> getAdmins() {
        return admins;
    }
}
