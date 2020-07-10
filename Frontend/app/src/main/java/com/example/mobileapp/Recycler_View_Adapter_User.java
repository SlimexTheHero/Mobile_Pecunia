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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class Recycler_View_Adapter_User extends RecyclerView.Adapter<Recycler_View_Adapter_User.ViewHolder> {

    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mUserImages = new ArrayList<>();
    private ArrayList<Boolean> mUserAdmin = new ArrayList<>();
    private MemberFragment mContext;
    private boolean isAdmin;

    public Recycler_View_Adapter_User(MemberFragment mContext, ArrayList<String> mUserNames,
                                      ArrayList<String> mUserImages, ArrayList<Boolean> mUserAdmin,
                                      boolean isAdmin) {
        this.mUserNames = mUserNames;
        this.mUserImages = mUserImages;
        this.mUserAdmin = mUserAdmin;
        this.mContext = mContext;
        this.isAdmin= isAdmin;
    }



    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        if(isAdmin){
            if (mUserAdmin.get(position)) {
                holder.userAdmin.setImageResource(R.drawable.admin_crown);
            } else {
                holder.userAdmin.setImageResource(R.drawable.no_admin_crown);
            }
        }else{
            if (mUserAdmin.get(position)) {
                holder.userAdmin.setImageResource(R.drawable.admin_crown);
            }
        }



        //Remove User
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser(position);
            }
        });

        //Set Admin
        holder.userAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdmin(position);
            }
        });
    }





    public void setAdmin (int position) {

        MaterialAlertDialogBuilder confirmAdmin = new MaterialAlertDialogBuilder(mContext.getActivity());
        confirmAdmin.setTitle("Admin rights");

        if (!mUserAdmin.get(position)) {
            confirmAdmin.setIcon(R.drawable.no_admin_crown);
            confirmAdmin.setMessage("Do you want to give " + mUserNames.get(position) + " Admin rights?");
            confirmAdmin.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mUserAdmin.set(position, true);
                    Recycler_View_Adapter_User.this.notifyItemChanged(position);
                }
            });
            confirmAdmin.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
        } else {
            confirmAdmin.setIcon(R.drawable.admin_crown);
            //Check ob er nicht der letzte Admin ist, wenn ja ist keine Admin entnahme möglich
            confirmAdmin.setMessage("Do you want to remove your own Admin rights?");
            confirmAdmin.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mUserAdmin.set(position, false);
                    Recycler_View_Adapter_User.this.notifyItemChanged(position);
                }
            });
            confirmAdmin.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
        }
        confirmAdmin.show();
    }

    public void removeUser (int position) {
        MaterialAlertDialogBuilder deleteUser = new MaterialAlertDialogBuilder(mContext.getActivity());
        deleteUser.setTitle("Remove User");
        deleteUser.setMessage("Are you sure you want to remove " + mUserNames.get(position) + " from this trip? Only an admin can add him back to this trip");
        deleteUser.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mContext.getActivity().finish();
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
