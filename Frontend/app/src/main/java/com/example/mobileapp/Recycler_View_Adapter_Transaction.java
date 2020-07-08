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

    private ArrayList<String> Debtor;
    private ArrayList <String> Creditor;
    private ArrayList <String> mTitles;
    private ArrayList <String> mAmount;
    private ArrayList <String> mAmount_Converted;
    private ArrayList <String> mCurrency_Converted;
    private ArrayList <String> mCurrency;
    private TransactionFragment mContext;
    private ArrayList <String> mDate;
    private ArrayList <String> mTime;

    public Recycler_View_Adapter_Transaction(TransactionFragment mContext, ArrayList<ArrayList<String>> mContent) {
        this.mContext = mContext;
        Debtor = mContent.get(0);
        Creditor = mContent.get(1);
        mTitles = mContent.get(2);
        mAmount = mContent.get(3);
        mCurrency = mContent.get(4);
        mAmount_Converted = mContent.get(5);
        mCurrency_Converted = mContent.get(6);
        mDate = mContent.get(7);
        mTime = mContent.get(8);
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

        holder.userName.setText(Creditor.get(position));
        holder.title.setText(mTitles.get(position));
        holder.amount.setText(mAmount.get(position));
        holder.currency.setText(mCurrency.get(position));

        holder.transactionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionDetails(position);
            }
        });

        if (!mCurrency.get(position).equals(mCurrency_Converted.get(position))) {
            holder.currency_converted.setText(mCurrency_Converted.get(position));
            holder.amount_converted.setText(mAmount_Converted.get(position));
        } else {
            holder.currency_converted.setText("");
            holder.amount_converted.setText("");
        }


    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public void transactionDetails (int position) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(mContext.getActivity());
        seeDetails.setTitle(mTitles.get(position));
        String amount = "Amount: " + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + mAmount.get(position) + "\t" + mCurrency.get(position);
        String one = "Debtor: " + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + Debtor.get(position);
        String two = "Creditor: " + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + Creditor.get(position);
        String date = "Transaction Date: " + "\t\t\t\t" + mDate.get(position);
        String time = "Transaction Time: " + "\t\t\t\t" + mTime.get(position);
        seeDetails.setMessage(amount + "\n" + "\n" + one + "\n" + two + "\n" + date + "\n" + time);
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
