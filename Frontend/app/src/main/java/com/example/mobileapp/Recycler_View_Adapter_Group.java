package com.example.mobileapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class Recycler_View_Adapter_Group extends RecyclerView.Adapter<Recycler_View_Adapter_Group.ViewHolder>{

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripImages = new ArrayList<>();
    private ArrayList<String> mTripDuration = new ArrayList<>();
    private ArrayList<String> mTripIds = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTripParticipants=new ArrayList<>();
    private ArrayList<ArrayList<String>> mTransactions=new ArrayList<>();
    private ArrayList<ArrayList<String>> mAdmins=new ArrayList<>();
    private Context mContext;

    public Recycler_View_Adapter_Group(ArrayList<String> mTripNames, ArrayList<String> mTripImages,
                                       ArrayList<String> mTripDuration, ArrayList<String> mTripIds,
                                       ArrayList<ArrayList<String>> mTripParticipants,
                                       ArrayList<ArrayList<String>> mTransactions,
                                       ArrayList<ArrayList<String>> mAdmins, Context mContext) {
        this.mTripNames = mTripNames;
        this.mTripImages = mTripImages;
        this.mTripDuration = mTripDuration;
        this.mTripIds = mTripIds;
        this.mTripParticipants = mTripParticipants;
        this.mTransactions = mTransactions;
        this.mAdmins = mAdmins;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mTripImages.get(position))
                .into(holder.image);

        holder.tripName.setText(mTripNames.get(position));
        holder.tripDuration.setText(mTripDuration.get(position));

        holder.tripElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrip(position);
            }
        });
    }

    public void openTrip(int position) {
        Intent intent = new Intent(mContext, Single_Trip.class);
        Bundle content = new Bundle();
        content.putString("Name", mTripNames.get(position));
        content.putString("Duration", mTripDuration.get(position));
        content.putString("Image", mTripImages.get(position));
        content.putString("Id",mTripIds.get(position));
        content.putStringArrayList("Participants",mTripParticipants.get(position));
        content.putStringArrayList("Transactions",mTransactions.get(position));
        content.putStringArrayList("Admins",mAdmins.get(position));
        intent.putExtras(content);
        ((Trip_Overview_Screen)mContext).finish();
        mContext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return mTripNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView tripName;
        TextView tripDuration;
        LinearLayout tripElement;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.trip_image);
            tripName = itemView.findViewById(R.id.trip_name);
            tripDuration = itemView.findViewById(R.id.trip_duration);
            tripElement = itemView.findViewById(R.id.trip_element);

        }
    }
}
