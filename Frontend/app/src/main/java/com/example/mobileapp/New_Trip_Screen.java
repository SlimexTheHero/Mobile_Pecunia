package com.example.mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileapp.model.Transaction;
import com.example.mobileapp.model.Trip;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TripService;
import com.example.mobileapp.networking.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New_Trip_Screen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
//TODO validator
    //TODO Tastaurlayout member hinzufügen

    //TODO Bild
    private static final int PICK_IMAGE = 100;
    CircleImageView tripProfile;
    Uri imageUri;
    LinearLayout tripLayout;

    Spinner currencyDropdownTrip;
    String currency;

    TextInputLayout nameHolder;
    TextInputEditText vName;
    String name;

    TextInputLayout startDurationHolder;
    TextInputEditText vStartDuration;
    String startDuration;

    TextInputLayout endDurationHolder;
    TextInputEditText vEndDuration;
    String endDuration;

    String totalDuration;

    TextInputLayout addMemberNameLayout;
    TextInputEditText addMemberNameText;

    ImageView addMemberButton;
    ListView addMemberLayout;

    TripService tripService;
    ArrayList<String> arrayList = new ArrayList<>();


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
        addMemberButton = findViewById(R.id.add_member_button);
        addMemberLayout = findViewById(R.id.add_member_layout);
        addMemberNameLayout = findViewById(R.id.add_member_holder);
        addMemberNameText = findViewById(R.id.add_member_name);
        currencyDropdownTrip = findViewById(R.id.currency_dropdown_trip);
        tripService = RetrofitClient.getRetrofitInstance().create(TripService.class);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        String[] currencies = new String[]{"€ EUR", "$ USD" , "£ GBP"};
        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(this, R.layout.transaction_dropdown_item, currencies);
        currencyDropdownTrip.setAdapter(currency_adapter);

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateMemberName()) {
                    return;
                }
                arrayList.add(addMemberNameLayout.getEditText().getText().toString());
                addMemberLayout.setAdapter(arrayAdapter);
                addMemberNameText.getText().clear();
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
                addMemberNameText.clearFocus();
                imm.hideSoftInputFromWindow(addMemberNameText.getWindowToken(),0);
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

        addMemberLayout.setOnItemClickListener((parent, view, position, id) -> {
            MaterialAlertDialogBuilder deleteDialog = new MaterialAlertDialogBuilder(this);
            deleteDialog.setTitle("Remove " + addMemberLayout.getItemAtPosition(position).toString());
            deleteDialog.setMessage("Do you want to remove " + addMemberLayout.getItemAtPosition(position).toString() + " from this trip?");
            deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arrayList.remove(position);
                    arrayAdapter.notifyDataSetChanged();
                }
            });
            deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            deleteDialog.show();
        });

        currencyDropdownTrip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currency = "€ EUR";
            }
        });
    }

    public boolean validateMemberName () {
        String nameInput = addMemberNameText.getText().toString();
        if(nameInput.isEmpty()) {
            addMemberNameLayout.setError("Field cannot be empty");
            return false;
        } else {
            addMemberNameLayout.setError(null);
            return true;
        }
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
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("E-Mail", "");
        ArrayList<String> admins = new ArrayList<>();
        ArrayList<String> transactions = new ArrayList<>();
        admins.add(userEmail);
        arrayList.add(userEmail);
        Set<String> set = new HashSet<>(arrayList);
        arrayList.clear();
        arrayList.addAll(set);
        name = vName.getText().toString();
        startDuration = vStartDuration.getText().toString();
        endDuration = vEndDuration.getText().toString();
        totalDuration = startDuration + " - " + endDuration;
        String cleanCurrency ="";
        Trip trip = new Trip();
        switch(currency){
            case "$ USD":
                cleanCurrency="USD";
                break;
            case"€ EUR":
                cleanCurrency="EUR";
                break;
            case "£ GBP":
                cleanCurrency="GBP";
                break;
            default: cleanCurrency="EUR";
            break;
        }

        trip.setAdmins(admins);
        trip.setTransactions(transactions);
        trip.setTripDuration(totalDuration);
        trip.setTripName(name);
        trip.setTripParticipants(arrayList);

        trip.setCurrency(cleanCurrency);



        Call<String> call = tripService.addTrip(trip,userEmail);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


        String text = "Title: " + name + "\n" +
                "From: " + startDuration + "\n" +
                "To: " + endDuration + "\n" +
                "Total: " + totalDuration;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        finish(); // Recyler Vied muss in Trip Overview geupdated werden, hier gruppe hinzufügen
        startActivity(new Intent(this, Trip_Overview_Screen.class));
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
        startActivity(new Intent(this,Trip_Overview_Screen.class));
    }


}
