package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileapp.model.Transaction;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TransactionService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New_Transaction_Screen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Layouts,Views and their associated texts
    Spinner currencyDropdown;
    String currency;

    TextInputLayout locationHolder;
    TextInputEditText vLocation;
    String location;

    TextInputLayout participantHolder;
    TextInputEditText vParticipant;
    String participant;

    TextInputLayout amountHolder;
    TextInputEditText vAmount;
    String amount;

    TextInputLayout dateHolder;
    TextInputEditText vDate;
    String date;

    private int flag = 0;
    public static final int FLAG_PICK_DATE = 2;
    private TransactionService transactionService;
    private String userEmail;

    //ScreenLayout
    LinearLayout transactionLayout;


    //Strings for decision
    String debtor;
    String creditor;
    RadioGroup decider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction_screen);

        //Gives the views their content
        transactionLayout = findViewById(R.id.new_transaction_layout);
        vLocation = findViewById(R.id.new_transaction_title);
        vDate = findViewById(R.id.new_transaction_date);
        vAmount = findViewById(R.id.new_transaction_amount);
        decider = findViewById(R.id.transaction_decider);
        vParticipant = findViewById(R.id.second_participant);
        currencyDropdown = findViewById(R.id.currency_dropdown);
        transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("E-Mail", "");


        //List of all supported currencies, will be swapped with DB
        //Creation of dropdown for currencies
        String[] currencies = new String[]{"€ EUR", "$ USD", "£ GBP"};
        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(this, R.layout.transaction_dropdown_item, currencies);
        currencyDropdown.setAdapter(currency_adapter);

        //Remove focus of input and removes keyboard from the screen
        transactionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vAmount.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(vAmount.getWindowToken(), 0);
            }
        });


        //Calls for participant-, location-, date-lists and currency dropdown
        vDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                setFlag(FLAG_PICK_DATE);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
                return;
            }
        });

        vParticipant.setOnClickListener(v -> chooseParticipant());

        vLocation.setOnClickListener(v -> chooseLocation());

        currencyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO currency = groupcurrency
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
        if (flag == FLAG_PICK_DATE) {
            vDate.setText(DateFormat.getDateInstance().format(calendar.getTime()));
            date = DateFormat.getDateInstance().format(calendar.getTime());
        }
    }

    /**
     * Creates transaction and displays the input as a toast, also checks if there is any input
     *
     * @param view with no view the calendar wont be displayed
     */
    public void createTransaction(View view) {
        vDate.setText(date);
        decideDebtorOrCreditor();
        location = vLocation.getText().toString();
        amount = vAmount.getText().toString();
        if (!validateUser() | !validateAmount() | !validateLocation() | !validateDate()) {
            return;
        }
        String text = "Title: " + location + "\n" +
                "From: " + debtor + "\n" +
                "To: " + creditor + "\n" +
                "Amount: " + amount + "\n" +
                "Currency: " + currency + "\n" +
                "Date: " + date;


        Transaction transaction = new Transaction(debtor, creditor, cleanCurrency(currency),
                Double.valueOf(amount), location, date);
        Call<String> call = transactionService.addTransaction(transaction,participant , text,
                getIntent().getExtras().getString("TripId"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();


    }

    private String cleanCurrency(String currency) {
        String cleanCurrency = "EUR";
        switch (currency) {
            case "$ USD":
                cleanCurrency = "USD";
                break;
            case "€ EUR":
                cleanCurrency = "EUR";
                break;
            case "£ GBP":
                cleanCurrency = "GBP";
                break;
            default:
                cleanCurrency = "EUR";
                break;
        }
        return cleanCurrency;
    }


    /**
     * Creates dialog with a list off all locations and allows for custom location
     */
    public void chooseLocation() {

        String[] titles = new String[]{"Restaurant", "Bar", "Mall", "Beach", "City",
                "Custom title"};
        final EditText setOwnTitle = new EditText(this);
        int checkedTitle = -1;
        final int[] saveTitlePosition = new int[1];
        MaterialAlertDialogBuilder chooseTitle = new MaterialAlertDialogBuilder(this);
        chooseTitle.setTitle("Choose a location");
        setOwnTitle.setHint("Location");
        setOwnTitle.setPaddingRelative(220, 0, 100, 30);
        setOwnTitle.setBackground(null);

        //Creates items and checks if custom or normal
        chooseTitle.setSingleChoiceItems(titles, checkedTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which != 5) {
                    setOwnTitle.setInputType(InputType.TYPE_NULL);
                    setOwnTitle.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(setOwnTitle.getWindowToken(), 0);
                } else {
                    setOwnTitle.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    setOwnTitle.setClickable(true);
                }
                saveTitlePosition[0] = which;
            }
        });
        chooseTitle.setView(setOwnTitle);


        chooseTitle.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (saveTitlePosition[0] == 5) {
                    location = setOwnTitle.getText().toString();
                } else {
                    location = titles[saveTitlePosition[0]].toString();
                }
                vLocation.setText(location);
            }
        });


        chooseTitle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        chooseTitle.show();
    }

    /**
     * Creates dialog with a list off all members of a trip
     */
    public void chooseParticipant() {
        //Will be replaced by all members of a trip minus account user
        ArrayList<String> listWithEmails= getIntent().getExtras().getStringArrayList("Participants_EMail");
        listWithEmails.remove(userEmail);
        String[] names = listWithEmails.toArray(new String[listWithEmails.size()]);


        int checkedName = -1;
        final int[] savePosition = new int[1];
        MaterialAlertDialogBuilder chooseUser = new MaterialAlertDialogBuilder(this);
        chooseUser.setTitle("Choose a participant");
        chooseUser.setSingleChoiceItems(names, checkedName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                savePosition[0] = which;
            }
        });


        chooseUser.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                participant = names[savePosition[0]].toString();
                vParticipant.setText(names[savePosition[0]].toString());
            }
        });

        chooseUser.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        chooseUser.show();
    }


    /**
     * Checks if userfield is empty
     *
     * @return false if empty, true if not
     */
    public boolean validateUser() {
        participantHolder = findViewById(R.id.participant_holder);
        if (participant == null) {
            participantHolder.setError("Field cannot be empty");
            return false;
        } else {
            participantHolder.setError(null);
            return true;
        }
    }


    /**
     * Checks if amountfield is empty
     *
     * @return false if empty, true if not
     */
    public boolean validateAmount() {
        amountHolder = findViewById(R.id.amount_holder);
        if (amount == null | amount.isEmpty()) {
            amountHolder.setError("Field cannot be empty");
            return false;
        } else {
            amountHolder.setError(null);
            return true;
        }
    }


    /**
     * Checks if locationfield is empty
     *
     * @return false if empty, true if not
     */
    public boolean validateLocation() {
        locationHolder = findViewById(R.id.location_holder);
        if (location == null | location.isEmpty()) {
            locationHolder.setError("Field cannot be empty");
            return false;
        } else {
            locationHolder.setError(null);
            return true;
        }
    }


    /**
     * Checks if datefield is empty
     *
     * @return false if empty, true if not
     */
    public boolean validateDate() {
        dateHolder = findViewById(R.id.date_holder);
        if (date == null) {
            dateHolder.setError("Field cannot be empty");
            return false;
        } else {
            dateHolder.setError(null);
            return true;
        }
    }


    /**
     * Decider if the participants are either debtor or creditor
     */
    public void decideDebtorOrCreditor() {
        int radioButtonID = decider.getCheckedRadioButtonId();
        View radioButton = decider.findViewById(radioButtonID);
        int idx = decider.indexOfChild(radioButton);
        if (idx == 0) {
            debtor = participant;
            creditor = userEmail;//TODO creditor wird null gesetzt
        } else {
            creditor = participant;
            debtor = userEmail;
        }
    }


    public void backButton(View view) {
        finish();
    }
}
