package com.example.mobileapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "Recycler_View_Adapter_Group";

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripImages = new ArrayList<>();
    private ArrayList<String> mTripDuration = new ArrayList<>();
    private Context mContext;

    public Recycler_View_Adapter_Group(ArrayList<String> mTripNames, ArrayList<String> mTripImages, ArrayList<String> mTripDuration, Context mContext) {
        this.mTripNames = mTripNames;
        this.mTripImages = mTripImages;
        this.mTripDuration = mTripDuration;
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
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(mTripImages.get(position))
                .into(holder.image);

        holder.tripName.setText(mTripNames.get(position));
        holder.tripDuration.setText(mTripDuration.get(position));

        holder.tripElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on " + mTripNames.get(position));
                openTrip(position);
            }
        });
    }

    public void openTrip(int position) {
        Intent intent = new Intent(mContext, Single_Trip.class);
        Bundle content = new Bundle();
        content.putString("Name", mTripNames.get(position));
        content.putString("Duration", mTripDuration.get(position));
        intent.putExtras(content);
        mContext.startActivity(intent);
    }

    private void imageToBit(CircleImageView image) {

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
