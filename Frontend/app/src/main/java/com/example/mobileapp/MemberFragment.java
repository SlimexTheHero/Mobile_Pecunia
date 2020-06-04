package com.example.mobileapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


        return rootView;
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
