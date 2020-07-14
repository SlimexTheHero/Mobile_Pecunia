package com.example.mobileapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mobileapp.model.CompleteTrip;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TransactionService;
import com.example.mobileapp.networking.TripService;
import com.example.mobileapp.networking.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;


public class TransactionFragment extends Fragment {

    private ArrayList <String> Debtor = new ArrayList<>();
    private ArrayList <String> Creditor = new ArrayList<>();
    private ArrayList <String> mTitles = new ArrayList<>();
    private ArrayList <String> mCurrency = new ArrayList<>();
    private ArrayList <String> mAmount = new ArrayList<>();
    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mUserEmails = new ArrayList<>();
    private ArrayList<String> mTransactionsId =new ArrayList<>();
    private ArrayList <String> mDate = new ArrayList<>();

    Single_Trip single_trip;
    String tripId;
    CompleteTrip completeTrip;
    private UserService userService;
    private TripService tripService;
    private TransactionService transactionService;

    Button newTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_transaction_fragement, container, false);
        single_trip = (Single_Trip) getActivity();
        tripId = single_trip.getiD();
        userService = RetrofitClient.getRetrofitInstance().create(UserService.class);
        tripService = RetrofitClient.getRetrofitInstance().create(TripService.class);
        transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);

        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    fillWithTransactions(tripId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }
        };
        try {
            asyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_transaction_view);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Participants_Names",mUserNames);
        bundle.putStringArrayList("Participants_EMail",mUserEmails);
        bundle.putString("TripId",tripId);

        /**
         * Sets values with the information we got from the constructor
         * and sets OnClickListener
         */
        Recycler_View_Adapter_Transaction adapter = new Recycler_View_Adapter_Transaction(Debtor,
                Creditor,mTitles,mAmount,mCurrency,this,mDate,single_trip.geteMail(),
                single_trip.getiD(),single_trip.getTripName(),mTransactionsId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newTransaction = rootView.findViewById(R.id.add_transaction);
        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), New_Transaction_Screen.class).putExtras(bundle));
            }
        });

        return rootView;
    }

    /**
     * Request backend to return transaction information to later build the transaction element in the overview
     * @param tripId
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillWithTransactions(String tripId) throws IOException {

        Call<CompleteTrip> call = tripService.getCompleteTripById(tripId);
        completeTrip =call.execute().body();
        completeTrip.getTripParticipants().forEach(participant -> {
            mUserNames.add(participant.getName());
            mUserEmails.add(participant.geteMail());
        });

        completeTrip.getTransactions().forEach(transaction -> {
            mTransactionsId.add(transaction.getTransactionId());
            Debtor.add(transaction.getDebtor());
            Creditor.add(transaction.getCreditor());
            mTitles.add(transaction.getLocation());
            mAmount.add(String.valueOf(transaction.getLoan()));
            mCurrency.add(transaction.getCurrency());
            mDate.add(transaction.getDate());
        });
    }

}
