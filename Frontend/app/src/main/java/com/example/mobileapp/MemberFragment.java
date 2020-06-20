package com.example.mobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;



public class MemberFragment extends Fragment {

    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mUserImages = new ArrayList<>();
    private ArrayList<Boolean> mUserAdmin = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        initImageBitmaps();
        View rootView = inflater.inflate(R.layout.fragment_member_fragement, container, false);



        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_user_view) ;
        Recycler_View_Adapter_User adapter = new Recycler_View_Adapter_User(this,mUserNames, mUserImages, mUserAdmin);

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
                leaveTripButton();
            }
        });

        return rootView;
    }

    public void addUserButton () {
        MaterialAlertDialogBuilder addUser = new MaterialAlertDialogBuilder(getActivity());
        final TextInputLayout addUserLayout = new TextInputLayout(getActivity());
        final TextInputEditText addUserText = new TextInputEditText(getActivity());

        addUser.setTitle("Add user to trip");
        addUser.setMessage("Type in the E-Mail of the user you want to add.");
        addUserText.setHint("E-Mail");
        addUserLayout.setPadding(60,0,60,30);
        addUserLayout.addView(addUserText);
        addUserLayout.setStartIconDrawable(R.drawable.person_icon);
        addUser.setView(addUserLayout);
        addUser.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    public void leaveTripButton() {
        MaterialAlertDialogBuilder leaveTrip = new MaterialAlertDialogBuilder(getActivity());
        leaveTrip.setTitle("Leave Trip");

        //Kommende IF Abfrage ob
        //1. Man ein Admin ist
        //2. Eine Aktive Transaktion hat
        //Punkte 1 und 2 können ignoriert werden falls der Trip in der Vergangenheit liegt.

        leaveTrip.setMessage("Do you want to leave this trip? Be aware that once you leave this trip only an admin can add you back.");
        leaveTrip.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Delete User from Group, so he cant see it anymore
                getActivity().finish();
            }
        });
        leaveTrip.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        leaveTrip.show();
    }



    private void initImageBitmaps() {

        mUserImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mUserNames.add("Bruno");
        mUserAdmin.add(true);

        mUserImages.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mUserNames.add("Dennis");
        mUserAdmin.add(true);

        mUserImages.add("https://i.redd.it/j6myfqglup501.jpg");
        mUserNames.add("Filip");
        mUserAdmin.add(false);

        mUserImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mUserNames.add("Jan");
        mUserAdmin.add(false);

        mUserImages.add("https://i.redd.it/k98uzl68eh501.jpg");
        mUserNames.add("Philip");
        mUserAdmin.add(false);

        mUserImages.add("https://i.redd.it/glin0nwndo501.jpg");
        mUserNames.add("Dani");
        mUserAdmin.add(false);

    }
}
