package com.example.mobileapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TransactionFragment extends Fragment {

    //First User participating in the transaction, its the Accountuser himself, name is needed for admin
    private ArrayList <String> mUserNamesOne = new ArrayList<>();

    //Second User participating in the transaction
    private ArrayList <String> mUserNamesTwo = new ArrayList<>();

    //Title of the transaction
    private ArrayList <String> mTitles = new ArrayList<>();

    //Decider if money is received or has to be given
    private ArrayList <Boolean> mGiveOrGet = new ArrayList<>();

    //Displaying the amount in the groups currency
    private ArrayList <String> mAmount_Converted = new ArrayList<>();
    private ArrayList <String> mCurrency = new ArrayList<>();
    private ArrayList <String> mAmount = new ArrayList<>();
    private ArrayList <String> mCurrency_Converted = new ArrayList<>();
    //private RequestQueue mQueue;

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
        Recycler_View_Adapter_Transaction adapter = new Recycler_View_Adapter_Transaction(this,mUserNamesOne,mUserNamesTwo,mTitles,mGiveOrGet,mAmount,mCurrency,mAmount_Converted,mCurrency_Converted);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return rootView;
    }

    private void initImageBitmaps() {

        //mQueue = Volley.newRequestQueue(getActivity());
        //jsonParse();

        mUserNamesOne.add("Filip");
        mUserNamesTwo.add("Bruno");
        mTitles.add("Restaurant");
        mGiveOrGet.add(true);
        mAmount.add("50.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("€");

        mUserNamesOne.add("Dennis");
        mUserNamesTwo.add("Jan");
        mTitles.add("Themenpark");
        mGiveOrGet.add(false);
        mAmount.add("120.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("$");



        mUserNamesOne.add("Filip");
        mUserNamesTwo.add("Jan");
        mTitles.add("Einkaufen");
        mGiveOrGet.add(false);
        mAmount.add("1680.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("¥");


        mUserNamesOne.add("Dennis");
        mUserNamesTwo.add("Bruno");
        mTitles.add("Bar");
        mGiveOrGet.add(true);
        mAmount.add("20.00");
        mCurrency.add("€");
        mAmount_Converted.add("2.00");
        mCurrency_Converted.add("£");

    }




    /*
    private void jsonParse() {
        String url = "https://api.exchangeratesapi.io/latest";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject rates = response.getJSONObject("rates");
                    int canada = rates.getInt("CAD");
                    int hongkong = rates.getInt("HKD");
                    int island = rates.getInt("ISK");
                    int denmark = rates.getInt("DKK");
                    int hungary = rates.getInt("HUF");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    } */
}
