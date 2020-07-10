package com.example.mobileapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.model.CompleteTrip;
import com.example.mobileapp.model.Notification;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TripService;
import com.example.mobileapp.networking.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recycler_View_Adapter_User extends RecyclerView.Adapter<Recycler_View_Adapter_User.ViewHolder> {

    private ArrayList<String> mUserEMails = new ArrayList<>();
    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mUserImages = new ArrayList<>();
    private ArrayList<Boolean> mUserAdmin = new ArrayList<>();
    private String actualUserEMail;
    private MemberFragment mContext;
    private boolean isAdmin;
    private TripService tripService;
    private CompleteTrip completeTrip;

    public Recycler_View_Adapter_User(MemberFragment mContext, ArrayList<String> mUserNames, ArrayList<String> mUserEMails,
                                      ArrayList<String> mUserImages, ArrayList<Boolean> mUserAdmin,
                                      boolean isAdmin, String actualUserEMail, CompleteTrip completeTrip) {
        this.mUserNames = mUserNames;
        this.mUserEMails = mUserEMails;
        this.mUserImages = mUserImages;
        this.mUserAdmin = mUserAdmin;
        this.mContext = mContext;
        this.isAdmin = isAdmin;
        this.actualUserEMail = actualUserEMail;
        this.completeTrip = completeTrip;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tripService = RetrofitClient.getRetrofitInstance().create(TripService.class);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_group, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mUserImages.get(position))
                .into(holder.userImage);
        holder.userName.setText(mUserNames.get(position));

        if (isAdmin) {
            if (mUserAdmin.get(position)) {
                holder.userAdmin.setImageResource(R.drawable.admin_crown);
            } else {
                holder.userAdmin.setImageResource(R.drawable.no_admin_crown);
            }
        } else {
            if (mUserAdmin.get(position)) {
                holder.userAdmin.setImageResource(R.drawable.admin_crown);
            }
        }


        //Remove User
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    removeUser(position);
                }
            }
        });

        //Set Admin
        holder.userAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    setAdmin(position);
                }
            }
        });
    }


    public void setAdmin(int position) {
            MaterialAlertDialogBuilder confirmAdmin = new MaterialAlertDialogBuilder(mContext.getActivity());
            confirmAdmin.setTitle("Admin rights");

            if (!mUserAdmin.get(position)) {
                confirmAdmin.setIcon(R.drawable.no_admin_crown);
                confirmAdmin.setMessage("Do you want to give " + mUserNames.get(position) + " Admin rights?");
                confirmAdmin.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserAdmin.set(position, true);
                        Call<String> call = tripService.addAdminToTrip(mUserEMails.get(position), completeTrip.getTripId());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        mUserAdmin.remove(position);
                        Recycler_View_Adapter_User.this.notifyItemChanged(position);
                        Recycler_View_Adapter_User.this.notifyItemRangeChanged(position, mUserNames.size());
                    }
                });
                confirmAdmin.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                confirmAdmin.show();
            } else if(mUserEMails.get(position).equals(actualUserEMail)) {
                confirmAdmin.setIcon(R.drawable.admin_crown);
                //Check ob er nicht der letzte Admin ist, wenn ja ist keine Admin entnahme möglich
                confirmAdmin.setMessage("Do you want to remove your own Admin rights?");
                confirmAdmin.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserAdmin.set(position, false);
                        Call<String> call = tripService.deleteAdminFromTrip(mUserEMails.get(position),
                                completeTrip.getTripId());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        Recycler_View_Adapter_User.this.notifyItemChanged(position);
                    }
                });
                confirmAdmin.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                confirmAdmin.show();
            }
    }

    public void removeUser(int position) {
        if (!actualUserEMail.equals(mUserEMails.get(position)) || !mUserAdmin.get(position)) {
            MaterialAlertDialogBuilder deleteUser = new MaterialAlertDialogBuilder(mContext.getActivity());
            deleteUser.setTitle("Remove User");
            deleteUser.setMessage("Are you sure you want to remove " + mUserNames.get(position) + " from this trip? Only an admin can add him back to this trip");
            deleteUser.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Recycler_View_Adapter_User.this.notifyItemChanged(position);
                    Notification notification = new Notification();
                    notification.setNotificationMessage("");
                    notification.setNotificationType(3);
                    notification.setTripName(completeTrip.getTripName());
                    notification.setUserId(mUserEMails.get(position));
                    notification.setTripId(completeTrip.getTripId());
                    Call<String> call = tripService.deleteUserFromTrip(mUserEMails.get(position),
                            completeTrip.getTripId(), notification);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }

                    });
                    mUserNames.remove(position);
                    mUserAdmin.remove(position);
                    Recycler_View_Adapter_User.this.notifyItemRemoved(position);
                    Recycler_View_Adapter_User.this.notifyItemRangeChanged(position, mUserNames.size());
                }
            });
            deleteUser.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            deleteUser.show();
        }
    }

    @Override
    public int getItemCount() {
        return mUserNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        TextView userName;
        LinearLayout userLayout;
        ImageView userAdmin;

        public ViewHolder(View itemView) {
            super(itemView);

            userAdmin = itemView.findViewById(R.id.admin_status);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            userLayout = itemView.findViewById(R.id.user_layout);
        }
    }
}
