package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;


public class Lost_PW extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputCode;
    private String email;
    private Button sendEmail;
    private Button resetPW;
    private boolean emailSend = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_pw);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputCode = findViewById(R.id.text_input_reset_code);
        sendEmail = findViewById(R.id.send_email_button);
        resetPW = findViewById(R.id.reset_password_button);


        /**
         * Creates an instance of our email service and send a password reset code
         */
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail()) {
                    return;
                }
                Random rand = new Random();
                int codeInt = rand.nextInt(999999);
                String code = String.format("%06d", codeInt);
                textInputEmail.setEnabled(false);
                textInputEmail.setEndIconVisible(false);
                SendMail sm = new SendMail(context, email,
                        "Pecunia Password reset",
                        "This is your 6 digit code to reset your password: \n" + code);
                sm.execute();
                emailSend = true;
            }
        });


        resetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPW();
            }
        });

    }


    /**
     * Validates the email input field
     * If the email input field is empty it throws an error
     * @return
     */
    private boolean validateEmail() {
        email = textInputEmail.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            textInputEmail.setError("Field cannot be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }


    /**
     * Validates the code input field
     * If the email was not send by the user clicking on "send email" it throws an error
     * If the code input field is empty it throws an error
     * If the code is to short it throws an error
     * @return false if and error occurs, else true
     */
    private boolean validateCode() {
        String codeInput = textInputCode.getEditText().getText().toString();
        if (!emailSend) {
            textInputEmail.setError("You need to send the email before");
            return false;
        } else if (codeInput.isEmpty()) {
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



    /**
     * If validation succeeded it opens the reset PW screen
     */
    public void resetPW () {
        if (!validateEmail() || (!validateCode())) {
            return;
        } else {
            startActivity(new Intent(Lost_PW.this, New_PW_Screen.class));
        }

    }

    /**
     * Returns to previous activity
     * @param view
     */
    public void backButton(View view) {
        finish();
    }
}
