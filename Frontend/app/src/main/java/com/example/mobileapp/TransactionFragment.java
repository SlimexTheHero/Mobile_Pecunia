package com.example.mobileapp;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class TransactionFragment extends Fragment {

    //First User participating in the transaction, its the Accountuser himself, name is needed for admin
    private ArrayList <String> Debtor = new ArrayList<>();

    //Second User participating in the transaction
    private ArrayList <String> Creditor = new ArrayList<>();

    //Title of the transaction
    private ArrayList <String> mTitles = new ArrayList<>();

    //Displaying the amount in the groups currency
    private ArrayList <String> mAmount_Converted = new ArrayList<>();
    private ArrayList <String> mCurrency = new ArrayList<>();
    private ArrayList <String> mAmount = new ArrayList<>();
    private ArrayList <String> mCurrency_Converted = new ArrayList<>();

    //Point of Time of the Transaction
    private ArrayList <String> mDate = new ArrayList<>();
    private ArrayList <String> mTime = new ArrayList<>();

    Button newTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initImageBitmaps();
        View rootView = inflater.inflate(R.layout.fragment_transaction_fragement, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_transaction_view);
        ArrayList <ArrayList<String>> singleElement = new ArrayList<>();
        singleElement.add(Debtor);
        singleElement.add(Creditor);
        singleElement.add(mTitles);
        singleElement.add(mAmount);
        singleElement.add(mCurrency);
        singleElement.add(mAmount_Converted);
        singleElement.add(mCurrency_Converted);
        singleElement.add(mDate);
        singleElement.add(mTime);

        Recycler_View_Adapter_Transaction adapter = new Recycler_View_Adapter_Transaction(this, singleElement);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newTransaction = rootView.findViewById(R.id.add_transaction);
        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), New_Transaction_Screen.class));
            }
        });

        return rootView;
    }


    private void initImageBitmaps() {


        Debtor.add("Filip");
        Creditor.add("Bruno");
        mTitles.add("Restaurant");
        mAmount.add("50.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("€");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Jan");
        mTitles.add("Themenpark");
        mAmount.add("120.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("$");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Filip");
        Creditor.add("Jan");
        mTitles.add("Einkaufen");
        mAmount.add("1680.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("¥");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Bruno");
        mTitles.add("Bar");
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Bruno");
        mTitles.add("Bar");
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Bruno");
        mTitles.add("Bar");
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Bruno");
        mTitles.add("Bar");
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Bruno");
        mTitles.add("Bar");
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");
        mDate.add("25.02.2020");
        mTime.add("15:48");

        Debtor.add("Dennis");
        Creditor.add("Bruno");
        mTitles.add("Bar");
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");
        mDate.add("25.02.2020");
        mTime.add("15:48");
    }
}
