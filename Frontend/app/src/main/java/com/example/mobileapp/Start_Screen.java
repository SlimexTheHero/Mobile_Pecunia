package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mobileapp.model.User;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.UserService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Start_Screen extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPW;
    private UserService userService;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String EMAIL = "E-Mail";
    public static final String NAME = "Name";
    public static final String PASSWORD = "Password";

    private String userEmail;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userService = RetrofitClient.getRetrofitInstance().create(UserService.class);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPW = findViewById(R.id.text_input_pw);

    }



    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field cannot be empty");
            return false;
        } else {
            textInputEmail.setError(null);

            return true;
        }
    }

    public void forgotPW(View v) {
        startActivity(new Intent(Start_Screen.this, Lost_PW.class));
    }

    public void joinNow(View v) {
        startActivity(new Intent(Start_Screen.this, Register_Screen.class));
    }

    private boolean validatePW() {
        String pwInput = textInputPW.getEditText().getText().toString().trim();

        if (pwInput.isEmpty()) {
            textInputPW.setError("Field cannot be empty");
            return false;
        } else {
            textInputPW.setError(null);

            return true;
        }
    }


    public void login(View v) {
        if (!validateEmail() | !validatePW()) {
            return;
        }
        //Toast.makeText(this, "Logging in ...", Toast.LENGTH_SHORT).show();
        Call<User> call = userService.getUserByEmail(textInputEmail.getEditText().getText().toString().trim());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "Logging in ...", Toast.LENGTH_SHORT).show();
                System.out.println("Das ist eine Antwort: " + response.body());
                if (response.body() == null) {
                    textInputEmail.setError("Invalid Data");
                    textInputPW.setError("Invalid Data");
                } else {
                    User testUser = response.body();
                    userEmail = testUser.geteMail();
                    userName = testUser.getName();
                    userPassword = testUser.getPassword();
                    saveData();
                    startActivity(new Intent(Start_Screen.this,
                            Trip_Overview_Screen.class).putExtra("UserName",testUser.geteMail()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Make sure to have a connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setUserEmail(String input) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(EMAIL);
        editor.putString(EMAIL, input);
        editor.apply();
    }

    public void setUserName(String input) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(NAME);
        editor.putString(NAME, input);
        editor.apply();
    }

    public void setUserPassword(String input) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PASSWORD);
        editor.putString(PASSWORD, input);
        editor.apply();
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, "");
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(NAME, "");
    }

    public String getUserPassword() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, "");
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, userEmail);
        editor.putString(NAME, userName);
        editor.putString(PASSWORD, userPassword);
        editor.commit();
    }

    /**
     * When hitting the back button in Android the App will close, instead
     * of returning to the top activity in the stack
     */
    @Override
    public void onBackPressed() {
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(close);

    }


}
