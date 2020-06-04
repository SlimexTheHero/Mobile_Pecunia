package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Start_Screen extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPW;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputEmail = findViewById(R.id.text_input_email);
        textInputPW = findViewById(R.id.text_input_pw);

    }

        private boolean validateEmail () {
            String emailInput = textInputEmail.getEditText().getText().toString().trim();

            if(emailInput.isEmpty()) {
                textInputEmail.setError("Field cannot be empty");
                return false;
            } else {
                textInputEmail.setError(null);

                return true;
            }
        }

        public void forgotPW (View v) {
            startActivity(new Intent(Start_Screen.this, Lost_PW.class));
        }

        public void joinNow (View v) {
            startActivity(new Intent(Start_Screen.this, Register_Screen.class));
        }

        private boolean validatePW () {
            String pwInput = textInputPW.getEditText().getText().toString().trim();

            if(pwInput.isEmpty()) {
                textInputPW.setError("Field cannot be empty");
                return false;
            } else {
                textInputPW.setError(null);

                return true;
            }
        }
        

        public void login (View v) {
            if (!validateEmail() | !validatePW()) {
                return;
            }
            startActivity(new Intent(Start_Screen.this, Trip_Overview_Screen.class));
            Toast.makeText(this, "Logging in ...", Toast.LENGTH_SHORT).show();
        }

    /**
     * When hitting the back button in Android the App will close, instead
     * of returning to the top activity in the stack
     */
    @Override
    public void onBackPressed(){
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(close);

    }


}
