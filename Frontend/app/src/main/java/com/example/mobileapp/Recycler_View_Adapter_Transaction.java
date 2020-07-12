package com.example.mobileapp;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.model.Notification;
import com.example.mobileapp.networking.NotificationService;
import com.example.mobileapp.networking.RetrofitClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Recycler_View_Adapter_Transaction extends RecyclerView.Adapter<Recycler_View_Adapter_Transaction.ViewHolder> {

    private ArrayList<String> debtor;
    private ArrayList<String> creditor;
    private ArrayList<String> mTitles;
    private ArrayList<String> mAmount;
    private ArrayList<String> mCurrency;
    private TransactionFragment mContext;
    private ArrayList<String> mDate;
    private String activeUserEmail;
    private String activeTripId;
    private String tripName;
    private ArrayList<String> mTransactionsId;
    private NotificationService notificationService;

    public Recycler_View_Adapter_Transaction(ArrayList<String> debtor, ArrayList<String> creditor,
                                             ArrayList<String> mTitles, ArrayList<String> mAmount,
                                             ArrayList<String> mCurrency, TransactionFragment mContext,
                                             ArrayList<String> mDate, String activeUserEmail, String activeTripId,
                                             String tripName, ArrayList<String> mTransactionsId) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.mTitles = mTitles;
        this.mAmount = mAmount;
        this.mCurrency = mCurrency;
        this.mContext = mContext;
        this.mDate = mDate;
        this.activeUserEmail = activeUserEmail;
        this.activeTripId = activeTripId;
        this.tripName = tripName;
        this.mTransactionsId = mTransactionsId;
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
        notificationService = RetrofitClient.getRetrofitInstance().create(NotificationService.class);
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
        String text = "Title: " + tripName + "\n" +
                "Transactiontitle: "+""+"\n"+ // TODO Title der Transaktion hinzufügen
                "From: " + debtor.get(position) + "\n" +
                "To: " + creditor.get(position) + "\n" +
                "Amount: " + mAmount.get(position) + "\n" +
                "Currency: " + mCurrency.get(position) + "\n" +
                "Date: " + mDate.get(position);
        seeDetails.setMessage(text);
        seeDetails.setNeutralButton("Ask for Deletion", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String notificationReceiver = debtor.get(position);
                if (activeUserEmail.equals(debtor.get(position))) {
                    notificationReceiver = creditor.get(position);
                }
                Notification notification = new Notification();
                notification.setUserId(notificationReceiver);
                notification.setTripName(tripName);
                notification.setNotificationType(1);
                notification.setNotificationMessage(activeUserEmail + " asks for deleting the following transaction\n"+text);
                notification.setTransactionId(mTransactionsId.get(position));
                Call<String> call = notificationService.createDeleteTransactionNotification(notification);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
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
        TextView currency;
        LinearLayout transactionLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.person_two);
            title = itemView.findViewById(R.id.transaction_title);
            amount = itemView.findViewById(R.id.transaction_amount);
            currency = itemView.findViewById(R.id.transaction_currency_basis);
            transactionLayout = itemView.findViewById(R.id.transaction_layout);
        }
    }
}
