package com.example.mobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

class Recycler_View_Adapter_Transaction extends RecyclerView.Adapter<Recycler_View_Adapter_Transaction.ViewHolder> {

    private ArrayList<String> mUserNamesOne = new ArrayList<>();
    private ArrayList <String> mUserNamesTwo = new ArrayList<>();
    private ArrayList <String> mTitles = new ArrayList<>();
    private ArrayList <Boolean> mGiveOrGet = new ArrayList<>();
    private ArrayList <String> mAmount = new ArrayList<>();
    private ArrayList <String> mCurrency = new ArrayList<>();
    private TransactionFragment mContext;

    public Recycler_View_Adapter_Transaction(TransactionFragment mContext, ArrayList<String> mUserNamesOne, ArrayList<String> mUserNamesTwo, ArrayList<String> mTitles, ArrayList<Boolean> mGiveOrGet, ArrayList<String> mAmount, ArrayList<String> mCurrency) {
        this.mUserNamesOne = mUserNamesOne;
        this.mUserNamesTwo = mUserNamesTwo;
        this.mTitles = mTitles;
        this.mGiveOrGet = mGiveOrGet;
        this.mAmount = mAmount;
        this.mCurrency = mCurrency;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.userName.setText(mUserNamesTwo.get(position));
        holder.title.setText(mTitles.get(position));
        holder.amount.setText(mAmount.get(position));
        holder.currency.setText(mCurrency.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView title;
        TextView amount;
        TextView currency;
        LinearLayout transactionLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.person_two);
            title = itemView.findViewById(R.id.transaction_title);
            amount = itemView.findViewById(R.id.transaction_amount);
            currency = itemView.findViewById(R.id.transaction_currency);
            transactionLayout = itemView.findViewById(R.id.transaction_layout);
        }
    }
}
