package com.example.mobileapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileapp.model.CompleteTrip;
import com.example.mobileapp.model.Notification;
import com.example.mobileapp.model.Trip;
import com.example.mobileapp.model.User;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.TripService;
import com.example.mobileapp.networking.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MemberFragment extends Fragment {

    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mUserEmails = new ArrayList<>();
    private ArrayList<String> mUserImages = new ArrayList<>();
    private ArrayList<Boolean> mUserAdmin = new ArrayList<>();
    private String tripId;
    private UserService userService;
    private TripService tripService;
    private boolean isAdmin = false;
    private Single_Trip single_trip;
    private CompleteTrip completeTrip;
    private Button endTrip;
    private Button addMember;
    private Recycler_View_Adapter_User adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        single_trip = (Single_Trip) getActivity();
        tripId=single_trip.getiD();
        userService = RetrofitClient.getRetrofitInstance().create(UserService.class);
        tripService = RetrofitClient.getRetrofitInstance().create(TripService.class);

        @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask() { //TODO Sollte vllt static sein?
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    fillWithMember(tripId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }
        };
        try {
            asyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_member_fragement, container, false);

        endTrip= rootView.findViewById(R.id.leave_trip);
        addMember=rootView.findViewById(R.id.add_member);

        if(!isAdmin){
            endTrip.setVisibility(View.GONE);
            addMember.setVisibility(View.GONE);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_user_view);
        adapter = new Recycler_View_Adapter_User(this, mUserNames,
                mUserEmails, mUserImages, mUserAdmin,isAdmin,single_trip.geteMail(),completeTrip);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rootView.findViewById(R.id.add_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserButton();
            }
        });

        rootView.findViewById(R.id.leave_trip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTripButton();
            }
        });

        return rootView;
    }

    public void addUserButton() {
        MaterialAlertDialogBuilder addUser = new MaterialAlertDialogBuilder(getActivity());
        final TextInputLayout addUserLayout = new TextInputLayout(getActivity());
        final TextInputEditText addUserText = new TextInputEditText(getActivity());

        addUser.setTitle("Add user to trip");
        addUser.setMessage("Type in the E-Mail of the user you want to add.");
        addUserText.setHint("E-Mail");
        addUserLayout.setPadding(60, 0, 60, 30);
        addUserLayout.addView(addUserText);
        addUserLayout.setStartIconDrawable(R.drawable.person_icon);
        addUser.setView(addUserLayout);
        addUser.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Notification notification = new Notification();
                notification.setTripId(tripId);
                notification.setUserId(single_trip.getiD());
                notification.setNotificationType(2);
                notification.setNotificationMessage(""); // TODO Message?

                Call<String> call = userService.addUserToTrip(addUserText.getText().toString(),tripId,notification);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body()==null){
                            Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
                        }
                        mUserEmails.add(addUserText.getText().toString());
                        mUserAdmin.add(false);
                        mUserNames.add(response.body());
                        adapter.notifyItemChanged(mUserAdmin.size()-1);
                        adapter.notifyItemRangeChanged(mUserAdmin.size()-1,mUserAdmin.size());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(), "OnFailure", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
                return;
            }
        });
        addUser.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        addUser.show();
    }

    public void endTripButton() {
        MaterialAlertDialogBuilder endTrip = new MaterialAlertDialogBuilder(getActivity());
        endTrip.setTitle("End Trip");

        endTrip.setMessage("Do you want to end this trip? All active transactions will be summarized in a PDF file and send to each member of the trip. Furthermore " +
                "the trip will be deleted for ever user.");
        endTrip.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Send a notification to every member and create the PDF
                //Kick all Members and delete the trip
                getActivity().finish();
            }
        });
        endTrip.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        endTrip.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillWithMember(String tripId) throws IOException {

        Call<CompleteTrip> call = tripService.getCompleteTripById(tripId);
        completeTrip =call.execute().body();
        isAdmin= completeTrip.getAdmins().contains(single_trip.geteMail());
        completeTrip.getTripParticipants().forEach(participant -> {
            mUserImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
            mUserNames.add(participant.getName());
            mUserEmails.add(participant.geteMail());
            mUserAdmin.add(completeTrip.getAdmins().contains(participant.geteMail()));
        });
    }
}
