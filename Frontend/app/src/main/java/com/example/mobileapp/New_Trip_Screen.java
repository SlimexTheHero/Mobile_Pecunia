package com.example.mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class New_Trip_Screen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int PICK_IMAGE = 100;
    CircleImageView tripProfile;
    Uri imageUri;
    LinearLayout tripLayout;

    TextInputLayout nameHolder;
    TextInputEditText vName;
    String name;

    TextInputLayout startDurationHolder;
    TextInputEditText vStartDuration;
    String startDuration;

    TextInputLayout endDurationHolder;
    TextInputEditText vEndDuration;
    String endDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_trip_screen);

        tripLayout = findViewById(R.id.new_trip_layout);
        tripProfile = findViewById(R.id.trip_picture);
        nameHolder = findViewById(R.id.trip_name_holder);
        vName = findViewById(R.id.create_trip_name);
        startDurationHolder = findViewById(R.id.trip_start_date_holder);
        vStartDuration = findViewById(R.id.create_start_trip_duration);
        endDurationHolder = findViewById(R.id.trip_end_date_holder);
        vEndDuration = findViewById(R.id.create_end_trip_duration);


        tripProfile.setOnClickListener(v -> changePicture());

        tripLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vName.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(vName.getWindowToken(),0);
                vStartDuration.clearFocus();
                imm.hideSoftInputFromWindow(vStartDuration.getWindowToken(),0);
                vEndDuration.clearFocus();
                imm.hideSoftInputFromWindow(vEndDuration.getWindowToken(),0);
            }
        });

        vStartDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseStartDate();
            }
        });

        vEndDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseEndDate();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH,month);
        calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendarEnd.set(Calendar.YEAR, year);
        calendarEnd.set(Calendar.MONTH,month);
        calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        startDuration = DateFormat.getDateInstance().format(calendarStart.getTime());
        endDuration = DateFormat.getDateInstance().format(calendarEnd.getTime());
        vStartDuration.setText(startDuration);
        vEndDuration.setText(endDuration);
    }
    public void chooseStartDate() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datestart picker");
    }

    public void chooseEndDate() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "dateend picker");
    }

    public void createTrip () {
        name = vName.getText().toString();
        startDuration = vStartDuration.getText().toString();
        endDuration = vEndDuration.getText().toString();
    }

    public void changePicture() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE ) {
            imageUri = data.getData();
            tripProfile.setImageURI(imageUri);
        }
    }

    public void backButton(View view) {
        finish();
    }


}
