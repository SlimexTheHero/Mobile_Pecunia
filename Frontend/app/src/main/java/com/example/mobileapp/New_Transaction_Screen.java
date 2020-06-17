package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class New_Transaction_Screen extends AppCompatActivity {


    Spinner currencyDropdown;
    String currency;

    TextInputEditText vTitle;
    String title;

    TextInputEditText vAmount;
    String amount;

    TextView participant;
    String participantHolder;

    String debtor;
    String creditor;
    RadioGroup decider;

    String testingindx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction_screen);


        vTitle = findViewById(R.id.new_transaction_title);

        vAmount = findViewById(R.id.new_transaction_amount);

        decider = findViewById(R.id.transaction_decider);
        participant = findViewById(R.id.second_participant);

        currencyDropdown = findViewById(R.id.currency_dropdown);
        String[] currencies = new String[]{"€ EUR", "$ USD" , "£ GBP"};
        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(this, R.layout.transaction_dropdown_item, currencies);
        currencyDropdown.setAdapter(currency_adapter);

        participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseParticipant();
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

    public void createTransaction(View view) {

        decideDebtorOrCreditor();
        title = vTitle.getText().toString();
        amount = vAmount.getText().toString();
        String text = "Title: " + title + "\n" +
                "From: " + debtor + "\n" +
                "To: " + creditor + "\n" +
                "Amount: " + amount + "\n" +
                "Currency: " + currency + "\n" +
                "Test: " + testingindx;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
                participantHolder = names[savePosition[0]].toString();

            }
        });
        chooseUser.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        testingindx = String.valueOf(idx);
        if(idx == 1) {
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
