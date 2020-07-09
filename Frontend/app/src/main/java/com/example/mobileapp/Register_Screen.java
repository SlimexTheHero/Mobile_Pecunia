package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobileapp.model.User;
import com.example.mobileapp.networking.RetrofitClient;

import com.example.mobileapp.networking.UserService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;


public class Register_Screen extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPW;
    private TextInputLayout textInputPWConfirm;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        userService = RetrofitClient.getRetrofitInstance().create(UserService.class);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputName = findViewById(R.id.text_input_name);
        textInputPW = findViewById(R.id.text_input_pw);
        textInputPWConfirm = findViewById(R.id.text_input_pw_confirm);

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

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty()) {
            textInputName.setError("Field cannot be empty");
            return false;
        } else {
            textInputName.setError(null);

            return true;
        }
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


    private boolean confirmPW() {
        String pwInput = textInputPW.getEditText().getText().toString().trim();
        String secondPWInput = textInputPWConfirm.getEditText().getText().toString().trim();

        if (secondPWInput.isEmpty() ) {
            textInputPWConfirm.setError("Field cannot be empty");
            return false;
        } else if (!pwInput.equals(secondPWInput)) {
            textInputPWConfirm.setError("The Password doesn´t match");
            return false;
        }
        else {
            textInputPWConfirm.setError(null);

            return true;
        }
    }

    public void backButton(View view) {
        finish();
    }

    public void register(View view) {
        if (!validateEmail() | !validateName() | !validatePW() | !confirmPW()) {
            return;
        } else {
            Toast.makeText(this, "Registrierung erfolgreich", Toast.LENGTH_SHORT).show();
            User temp = new User();
            temp.seteMail(textInputEmail.getEditText().getText().toString());
            temp.setName(textInputName.getEditText().getText().toString());
            temp.setPassword(textInputPW.getEditText().getText().toString());
            Call<String> call = userService.registrateUser(temp);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("OK")){
                        startActivity(new Intent(Register_Screen.this, Start_Screen.class));
                    }else{
                        Toast.makeText(getApplicationContext(), "E-Mail already in use", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Make sure to have a connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
