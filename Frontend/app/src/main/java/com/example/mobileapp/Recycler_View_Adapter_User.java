package com.example.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class Recycler_View_Adapter_User extends RecyclerView.Adapter<Recycler_View_Adapter_User.ViewHolder> {

    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mUserImages = new ArrayList<>();
    private ArrayList<Boolean> mUserAdmin = new ArrayList<>();
    private MemberFragment mContext;

    public Recycler_View_Adapter_User(MemberFragment mContext, ArrayList<String> mUserNames, ArrayList<String> mUserImages, ArrayList<Boolean> mUserAdmin) {
        this.mUserNames = mUserNames;
        this.mUserImages = mUserImages;
        this.mUserAdmin = mUserAdmin;
        this.mContext = mContext;
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

        if (mUserAdmin.get(position)) {
            holder.userAdmin.setImageResource(R.drawable.admin_crown);
        } else {
            holder.userAdmin.setImageResource(R.drawable.no_admin_crown);
        }


        holder.userAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeAdminStatus(position)) {
                    mUserAdmin.set(position,true);
                    Recycler_View_Adapter_User.this.notifyItemChanged(position);
                } else {
                    mUserAdmin.set(position,false);
                    Recycler_View_Adapter_User.this.notifyItemChanged(position);
                }
            }
        });
    }


    public boolean changeAdminStatus(int position) {
        if (mUserAdmin.get(position)) {
            return false;
        } else {
            return true;
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
