package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

        Glide.with(this).asBitmap().load(image).into(tripImage);
        tripName.setText(name);
        tripDuration.setText(duration);
    }

    public void backButton(View view) {
        finish();
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


}
