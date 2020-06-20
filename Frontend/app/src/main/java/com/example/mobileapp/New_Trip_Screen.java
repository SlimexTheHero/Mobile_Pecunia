package com.example.mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    ImageView addMemberButton;

    LinearLayout addMemberLayout;

    private int flag = 0;
    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;


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
        addMemberLayout = findViewById(R.id.add_member_layout);
        addMemberButton = findViewById(R.id.add_member_button);

        addMemberLayout.getChildCount();


        //Not really working
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newMember = new EditText(getApplication());
                newMember.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                newMember.setHint("E-Mail");
                newMember.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                newMember.setHintTextColor(Color.parseColor("#716528"));
                addMemberLayout.addView(newMember);

            }
        });

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
                DialogFragment datePickerFragment = new DatePickerFragment();
                setFlag(FLAG_START_DATE);
                datePickerFragment.show(getSupportFragmentManager(), "dateStartPicker");
                return;
            }
        });

        vEndDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                setFlag(FLAG_END_DATE);
                datePickerFragment.show(getSupportFragmentManager(), "dateEndPicker");
                return;
            }
        });
    }


    public void setFlag(int i) {
        flag = i;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if (flag == FLAG_START_DATE) {
            vStartDuration.setText(DateFormat.getDateInstance().format(calendar.getTime()));
        } else if (flag == FLAG_END_DATE) {
            vEndDuration.setText(DateFormat.getDateInstance().format(calendar.getTime()));
        }
    }

    public void createTrip (View view) {
        name = vName.getText().toString();
        startDuration = vStartDuration.getText().toString();
        endDuration = vEndDuration.getText().toString();
        String text = "Title: " + name + "\n" +
                "From: " + startDuration + "\n" +
                "To: " + endDuration + "\n" +
                "Amount: " + addMemberLayout.getChildCount();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
