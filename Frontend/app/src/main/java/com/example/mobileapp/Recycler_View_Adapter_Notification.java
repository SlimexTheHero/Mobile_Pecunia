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

public class Recycler_View_Adapter_Notification extends RecyclerView.Adapter<Recycler_View_Adapter_Notification.ViewHolder> {

    private ArrayList<String> notificationId = new ArrayList<>();
    private ArrayList<Integer> notificationType = new ArrayList<>();
    private ArrayList<String> notificationTripName = new ArrayList<>();
    private ArrayList<String> notificationTripId = new ArrayList<>();
    private ArrayList<String> notificationTransactionId = new ArrayList<>();
    private ArrayList<String> notificationMessage = new ArrayList<>();
    private ArrayList<String> notificationUserId = new ArrayList<>();
    private Context context;
    private NotificationService notificationService;
    private TransactionService transactionService;
    int count;

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

    /**
     * Sets values with the information we got from the constructor
     * and sets OnClickListener
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (notificationType.get(position)) {
            case 0:
                holder.notificationTitle.setText("New Transaction");
                break;
            case 1:
                holder.notificationTitle.setText("Transaction deletion");
                break;
            case 2:
                holder.notificationTitle.setText("Added to Trip");
                break;
            case 3:
                holder.notificationTitle.setText("Removed from Trip");
                break;
            case 4:
                holder.notificationTitle.setText("Adminrights gained");
                break;
            default:
                return;
        }
        notificationService = RetrofitClient.getRetrofitInstance().create(NotificationService.class);
        transactionService = RetrofitClient.getRetrofitInstance().create(TransactionService.class);
        holder.groupName.setText(notificationTripName.get(position));
         count = 0;

        holder.notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (notificationType.get(position)) {
                    case 0:
                        transactionTwoDetails(position, "New Transaction");
                        break;
                    case 1:
                        transactionTwoDetails(position, "Transaction deletion");
                        break;
                    case 2:
                        transactionOneDetails(position, "Added to Trip");
                        break;
                    case 3:
                        transactionOneDetails(position, "Removed from Trip");
                        break;
                    case 4:
                        transactionOneDetails(position, "Adminrights gained");
                        break;
                    default:
                        return;
                }
            }
        });
    }

    /**
     * Creates AlertDialog for all notifications regarding a trip
     * Request backend to delete a notification if the user clicks on the corresponding action button
     * @param position
     * @param title
     */
    public void transactionOneDetails(int position, String title) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(context);
        seeDetails.setTitle(title);
        seeDetails.setMessage(notificationMessage.get(position));
        seeDetails.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                notifyItemChangeInThisAdapter(position);
                return;
            }
        });
        seeDetails.show();
    }

    private void notifyItemChangeInThisAdapter(int position){
    count++;
        this.notificationId.remove(position);
        this.notificationType.remove(position);
        this.notificationTripName.remove(position);
        this.notificationTripId.remove(position);
        this.notificationTransactionId.remove(position);
        this.notificationMessage.remove(position);
        this.notificationUserId.remove(position);
        notifyDataSetChanged();
    }


    /**
     * Creates AlertDialog for all notifications regarding a trip
     * Request backend to delete a notification if the user clicks on the corresponding action button
     * If a user accepts the transactions, the transaction will be displayed in the trip, otherwise it will be deleted
     * @param position
     * @param title
     */
    public void transactionTwoDetails(int position, String title) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(context);
        seeDetails.setTitle(title);
        seeDetails.setMessage(notificationMessage.get(position));
        seeDetails.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //---------------------- Accept Transaction ---------------------------
                if (notificationType.get(position) == 0) {
                    Call<String> call = transactionService.addTransactionToTrip(notificationTransactionId.get(position),
                            notificationTripId.get(position), notificationId.get(position));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    notifyItemChangeInThisAdapter(position);
                } else
                    //---------------------- Accept delete Transaction -----------------------
                    if (notificationType.get(position) == 1) {
                        Call<String> call = transactionService.deleteTransaction(notificationTransactionId.get(position),
                                notificationTripId.get(position), notificationId.get(position));
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                        notifyItemChangeInThisAdapter(position);
                    }


                return;
            }
        });
        seeDetails.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //---------------------- Decline Transaction invite ---------------------------
                if (notificationType.get(position) == 0) {
                    Call<String> call = transactionService.deleteTransactionInvite(notificationTransactionId.get(position), notificationId.get(position));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    notifyItemChangeInThisAdapter(position);
                }
                //---------------------- Decline Transaction deletion ---------------------------
                if (notificationType.get(position) == 1) {
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
                notifyItemChangeInThisAdapter(position);
                return;
            }
        });
        seeDetails.setNeutralButton("Decide later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //-------------------------Decide later-------------------------------------
                return;
            }
        });
        seeDetails.show();
    }

    @Override
    public int getItemCount() {
        return notificationTripName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
