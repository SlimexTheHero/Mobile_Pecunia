package com.example.mobileapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loading_Dialog {

    Activity activity;
    AlertDialog dialog;

    Loading_Dialog (Activity activity) {
        this.activity = activity;
    }

    /**
     * Starts Loading screen
     */
    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_screen, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    /**
     * Ends loading screen
     */
    void dismissDialog() {
        dialog.dismiss();
    }
}
