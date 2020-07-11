package com.example.mobileapp;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

class Recycler_View_Adapter_Transaction extends RecyclerView.Adapter<Recycler_View_Adapter_Transaction.ViewHolder> {

    private ArrayList<String> debtor;
    private ArrayList<String> creditor;
    private ArrayList<String> mTitles;
    private ArrayList<String> mAmount;
    private ArrayList<String> mCurrency;
    private TransactionFragment mContext;
    private ArrayList<String> mDate;

    public Recycler_View_Adapter_Transaction(ArrayList<String> debtor, ArrayList<String> creditor,
                                             ArrayList<String> mTitles, ArrayList<String> mAmount,
                                             ArrayList<String> mCurrency, TransactionFragment mContext,
                                             ArrayList<String> mDate) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.mTitles = mTitles;
        this.mAmount = mAmount;
        this.mCurrency = mCurrency;
        this.mContext = mContext;
        this.mDate = mDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction_receive, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.userName.setText(creditor.get(position));
        holder.title.setText(mTitles.get(position));
        holder.amount.setText(mAmount.get(position));
        holder.currency.setText(mCurrency.get(position));

        holder.transactionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionDetails(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public void transactionDetails(int position) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(mContext.getActivity());
        seeDetails.setTitle(mTitles.get(position));
        String amount = "Amount: " + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + mAmount.get(position) + "\t" + mCurrency.get(position);
        String one = "Debtor: " + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + debtor.get(position);
        String two = "Creditor: " + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + creditor.get(position);
        String date = "Transaction Date: " + "\t\t\t\t" + mDate.get(position);
        seeDetails.setMessage(amount + "\n" + "\n" + one + "\n" + two + "\n" + date + "\n");
        seeDetails.setNeutralButton("Ask for Deletion", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        seeDetails.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        seeDetails.show();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView title;
        TextView amount;
        TextView amount_converted;
        TextView currency_converted;
        TextView currency;
        LinearLayout transactionLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.person_two);
            title = itemView.findViewById(R.id.transaction_title);
            amount = itemView.findViewById(R.id.transaction_amount);
            amount_converted = itemView.findViewById(R.id.transaction_amount_converted);
            currency_converted = itemView.findViewById(R.id.transaction_currency);
            currency = itemView.findViewById(R.id.transaction_currency_basis);
            transactionLayout = itemView.findViewById(R.id.transaction_layout);
        }
    }
}
