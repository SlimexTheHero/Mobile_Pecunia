package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class New_Transaction_Screen extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    Spinner currencyDropdown;
    String currency;

    TextInputEditText vTitle;
    String title;

    TextView participant;
    String participantHolder;

    TextInputEditText vAmount;
    String amount;

    TextView vDate;
    String date;

    LinearLayout transactionLayout;

    String debtor;
    String creditor;
    RadioGroup decider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction_screen);

        transactionLayout = findViewById(R.id.new_transaction_layout);
        vTitle = findViewById(R.id.new_transaction_title);
        vDate = findViewById(R.id.new_transaction_date);
        vAmount = findViewById(R.id.new_transaction_amount);
        decider = findViewById(R.id.transaction_decider);
        participant = findViewById(R.id.second_participant);
        currencyDropdown = findViewById(R.id.currency_dropdown);

        String[] currencies = new String[]{"€ EUR", "$ USD" , "£ GBP"};
        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(this, R.layout.transaction_dropdown_item, currencies);
        currencyDropdown.setAdapter(currency_adapter);

        //
        transactionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vAmount.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(vAmount.getWindowToken(),0);
            }
        });

        vDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseParticipant();
            }
        });

        vTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTitle();
            }
        });

        currencyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //currency = groupcurrency
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = DateFormat.getDateInstance().format(calendar.getTime());
        vDate.setText(date);
    }

    public void chooseDate() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }




    public void createTransaction(View view) {

        decideDebtorOrCreditor();
        title = vTitle.getText().toString();
        amount = vAmount.getText().toString();
        String text = "Title: " + title + "\n" +
                "From: " + debtor + "\n" +
                "To: " + creditor + "\n" +
                "Amount: " + amount + "\n" +
                "Currency: " + currency + "\n" +
                "Date: " + date;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void chooseTitle() {

        String[] titles = new String[] {"Restaurant", "Bar", "Mall", "Beach", "City",
                "Custom title"};
        final EditText setOwnTitle = new EditText(this);



        int checkedTitle = -1;
        final int[] saveTitlePosition = new int[1];
        MaterialAlertDialogBuilder chooseTitle = new MaterialAlertDialogBuilder(this);
        chooseTitle.setTitle("Choose a title");
        setOwnTitle.setHint("Location");
        setOwnTitle.setPaddingRelative(220,0,100,30);
        setOwnTitle.setBackground(null);
        chooseTitle.setSingleChoiceItems(titles, checkedTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which != 5) {
                    setOwnTitle.setInputType(InputType.TYPE_NULL);
                    setOwnTitle.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(setOwnTitle.getWindowToken(),0);
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

                if(saveTitlePosition[0] == 5) {
                    title = setOwnTitle.getText().toString();
                } else {
                    title = titles[saveTitlePosition[0]].toString();
                }
                vTitle.setText(title);
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

    public void chooseParticipant() {

        //Will be replaced by all members of a trip minus account user
        String[] names = new String[] {"Dani", "Bruno", "Dennis", "Filip", "Jan", "Philip"};


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

                participantHolder = names[savePosition[0]].toString();
                participant.setText(names[savePosition[0]].toString());
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

    public void decideDebtorOrCreditor() {
        int radioButtonID = decider.getCheckedRadioButtonId();
        View radioButton = decider.findViewById(radioButtonID);
        int idx = decider.indexOfChild(radioButton);
        if(idx == 0) {
            debtor = participantHolder;
            creditor = "Test";
            //creditor = Accountuser
        } else {
            creditor = participantHolder;
            debtor = "Test";
            //debtor = Accountuser;
        }
    }

    public void backButton(View view) {
        finish();
    }
}
