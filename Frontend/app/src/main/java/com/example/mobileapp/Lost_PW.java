package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_pw);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputCode = findViewById(R.id.text_input_reset_code);
        sendEmail = findViewById(R.id.send_email_button);

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
                Toast.makeText(getApplicationContext(), "Sending E-Mail ...", Toast.LENGTH_SHORT).show();
                try {
                    GMailSender sender = new GMailSender();
                    sender.sendMail("Password reset code",
                            "This is your 6 Digit Code: \n" +
                            code, "stanicakfilip@gmail.com", "stanicakfilip@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });

    }

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

    public void backButton(View view) {
        finish();
    }
}
