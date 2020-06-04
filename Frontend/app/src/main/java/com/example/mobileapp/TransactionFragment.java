package com.example.mobileapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TransactionFragment extends Fragment {

    private static final String TAG = "Debug";
    private ArrayList <String> mUserNamesOne = new ArrayList<>();
    private ArrayList <String> mUserNamesTwo = new ArrayList<>();
    private ArrayList <String> mTitles = new ArrayList<>();
    private ArrayList <Boolean> mGiveOrGet = new ArrayList<>();
    private ArrayList <String> mAmount = new ArrayList<>();
    private ArrayList <String> mCurrency = new ArrayList<>();

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
        Recycler_View_Adapter_Transaction adapter = new Recycler_View_Adapter_Transaction(this,mUserNamesOne,mUserNamesTwo,mTitles,mGiveOrGet,mAmount,mCurrency);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return rootView;
    }

    private void initImageBitmaps() {

        mUserNamesOne.add("Filip");
        mUserNamesTwo.add("Bruno");
        mTitles.add("Restaurant");
        mGiveOrGet.add(true);
        mAmount.add("50.00");
        mCurrency.add("€");



        mUserNamesOne.add("Dennis");
        mUserNamesTwo.add("Jan");
        mTitles.add("Themenpark");
        mGiveOrGet.add(false);
        mAmount.add("120.00");
        mCurrency.add("$");



        mUserNamesOne.add("Filip");
        mUserNamesTwo.add("Jan");
        mTitles.add("Einkaufen");
        mGiveOrGet.add(false);
        mAmount.add("1680.00");
        mCurrency.add("¥");


        mUserNamesOne.add("Dennis");
        mUserNamesTwo.add("Bruno");
        mTitles.add("Bar");
        mGiveOrGet.add(true);
        mAmount.add("20.00");
        mCurrency.add("£");

    }
}
