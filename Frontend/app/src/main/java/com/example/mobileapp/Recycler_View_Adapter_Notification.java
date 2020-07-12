package com.example.mobileapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.networking.NotificationService;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TransactionService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Recycler_View_Adapter_Notification extends RecyclerView.Adapter<Recycler_View_Adapter_Notification.ViewHolder>{

    private ArrayList<String> notificationId= new ArrayList<>();
    private ArrayList<Integer> notificationType = new ArrayList<>();
    private ArrayList<String> notificationTripName = new ArrayList<>();
    private ArrayList<String> notificationTripId = new ArrayList<>();
    private ArrayList<String> notificationTransactionId = new ArrayList<>();
    private ArrayList<String> notificationMessage = new ArrayList<>();
    private ArrayList<String> notificationUserId = new ArrayList<>();
    private Context context;
    private NotificationService notificationService;
    private TransactionService transactionService;

    public Recycler_View_Adapter_Notification(ArrayList<String> notificationId, ArrayList<Integer> notificationType,
                                              ArrayList<String> notificationTripName,
                                              ArrayList<String> notificationTripId, ArrayList<String> notificationTransactionId,
                                              ArrayList<String> notificationMessage, ArrayList<String> notificationUserId,
                                              Context context) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.notificationTripName = notificationTripName;
        this.notificationTripId = notificationTripId;
        this.notificationTransactionId = notificationTransactionId;
        this.notificationMessage = notificationMessage;
        this.notificationUserId = notificationUserId;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notifications_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.groupName.setText(notificationTripName.get(position));
        notificationService = RetrofitClient.getRetrofitInstance().create(NotificationService.class);
        transactionService = transactionService;

        holder.notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (notificationType.get(position)) {
                    case 0:
                        holder.notificationTitle.setText("New Transaction");
                        transactionTwoDetails(position,"New Transaction");
                        break;
                    case 1:
                        holder.notificationTitle.setText("Transaction deletion");
                        transactionTwoDetails(position,"Transaction deletion");
                        break;
                    case 2:
                        holder.notificationTitle.setText("Added to Trip");
                        transactionOneDetails(position,"Added to Trip");
                        break;
                    case 3:
                        holder.notificationTitle.setText("Removed from Trip");
                        transactionOneDetails(position,"Removed from Trip");
                        break;
                    case 4:
                        holder.notificationTitle.setText("Adminrights gained");
                        transactionOneDetails(position,"Adminrights gained");
                        break;
                    default:
                        return;
                }
            }
        });
    }

    public void transactionOneDetails (int position,String title) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(context);
        seeDetails.setTitle(title);
        seeDetails.setMessage(notificationMessage.get(position));
        seeDetails.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                return;
            }
        });
        seeDetails.setNeutralButton("Delete Notification", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<String> call = notificationService.deleteNotification(notificationId.get(position));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                return;
            }
        });
        seeDetails.show();
    }

    public void transactionTwoDetails (int position,String title) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(context);
        seeDetails.setTitle(title);
        seeDetails.setMessage(notificationMessage.get(position));
        seeDetails.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Notification will be accepted
                //---------------------- Accept Transaction ---------------------------
                if(notificationType.get(position)==0){
                    Call<String> call = transactionService.addTransactionToTrip(notificationTransactionId.get(position),
                            notificationTripId.get(position),notificationId.get(position));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }else
                    //---------------------- Accept delete Transaction -----------------------
                    if(notificationType.get(position)==1){
                    Call<String> call = transactionService.deleteTransaction(notificationTransactionId.get(position),
                            notificationTripId.get(position),notificationId.get(position));
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {

                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                }
                return;
            }
        });
        seeDetails.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Notification will be deleted
                //---------------------- Decline Transaction invite ---------------------------
                if(notificationType.get(position)==0){
                    Call<String> call = transactionService.deleteTransactionInvite(notificationTransactionId.get(position),notificationId.get(position));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                //---------------------- Decline Transaction deletion ---------------------------
                if(notificationType.get(position)==1){
                    Call<String> call = notificationService.deleteNotification(notificationId.get(position));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                return;
            }
        });
        seeDetails.setNeutralButton("Decide later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Decide later
                return;
            }
        });
        seeDetails.show();
    }

    @Override
    public int getItemCount() {
        return notificationId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView notificationTitle;
        TextView groupName;
        LinearLayout notificationLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notification_transaction_type);
            groupName = itemView.findViewById(R.id.notification_group_name);
            notificationLayout = itemView.findViewById(R.id.notification_layout);
        }
    }
}
