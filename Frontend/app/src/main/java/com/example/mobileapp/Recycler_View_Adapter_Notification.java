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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class Recycler_View_Adapter_Notification extends RecyclerView.Adapter<Recycler_View_Adapter_Notification.ViewHolder>{


    private ArrayList<Integer> notificationType = new ArrayList<>();
    private ArrayList<String> notificationTitle = new ArrayList<>();
    private ArrayList<String> notificationGroup = new ArrayList<>();
    private ArrayList<String> notificationMessage = new ArrayList<>();
    private Context context;

    public Recycler_View_Adapter_Notification(Context context, ArrayList<Integer> notificationType, ArrayList<String> notificationTitle, ArrayList<String> notificationGroup, ArrayList<String> notificationMessage) {
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.notificationGroup = notificationGroup;
        this.notificationMessage = notificationMessage;
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
        holder.groupName.setText(notificationGroup.get(position));
        holder.notificationTitle.setText(notificationTitle.get(position));

        holder.notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (notificationType.get(position)) {
                    case 0:
                    case 1:
                        transactionTwoDetails(position);
                        break;
                    case 2:
                    case 3:
                    case 4:
                        transactionOneDetails(position);
                        break;
                    default:
                        return;
                }
            }
        });
    }

    public void transactionOneDetails (int position) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(context);
        seeDetails.setTitle(notificationTitle.get(position));
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
                //Here comes the deletion of the Notification
                return;
            }
        });
    }

    public void transactionTwoDetails (int position) {
        MaterialAlertDialogBuilder seeDetails = new MaterialAlertDialogBuilder(context);
        seeDetails.setTitle(notificationTitle.get(position));
        seeDetails.setMessage(notificationMessage.get(position));
        seeDetails.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Notification will be deleted
                return;
            }
        });
        seeDetails.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Notification will be deleted
                return;
            }
        });
        seeDetails.setNeutralButton("Decide later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationTitle.size();
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
