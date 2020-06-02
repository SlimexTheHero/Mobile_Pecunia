package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class Lost_PW extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_pw);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputCode = findViewById(R.id.text_input_reset_code);

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

    private boolean validateCode() {
        String codeInput = textInputCode.getEditText().getText().toString();

        if (codeInput.isEmpty()) {
            textInputCode.setError("Field cannot be empty");
            return false;
        } else if (codeInput.length()!=6) {
            textInputCode.setError("Code is to short");
            return false;
        } else {
            textInputCode.setError(null);
            return true;
        }
    }

    public void resetPW (View v) {
        if (!validateEmail() || (!validateCode())) {
            return;
        } else {
            startActivity(new Intent(Lost_PW.this, New_PW_Screen.class));
        }

    }

    public void sendEmail(View v) {
        if (!validateEmail()) {
            return;
        }
        textInputEmail.setEnabled(false);
        textInputEmail.setEndIconVisible(false);
        Toast.makeText(this, "Sending E-Mail ...", Toast.LENGTH_SHORT).show();
    }

    public void backButton(View view) {
        finish();
    }
}
