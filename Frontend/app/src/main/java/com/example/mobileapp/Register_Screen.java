package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class Register_Screen extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPW;
    private TextInputLayout textInputPWConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

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
            startActivity(new Intent(Register_Screen.this, Start_Screen.class));
        }
    }
}
