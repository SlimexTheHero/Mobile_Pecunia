package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Start_Screen extends AppCompatActivity {

    private TextView register;
    private Button login;
    private TextView pw_lost;
    private EditText email;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView)findViewById(R.id.textView4);
        login = (Button)findViewById(R.id.btnLogin);
        pw_lost = (TextView)findViewById(R.id.textView2);

        email = (EditText)findViewById(R.id.email_ph);
        password = (EditText)findViewById(R.id.editText2);



        /**
         * Opens Register Screen
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start_Screen.this, Register_Screen.class));
            }
        });

        pw_lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start_Screen.this, Lost_PW.class));
            }
        });


        /**
         * Testusage for wrong PW/E-Mail input, has to be connected to our DB
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setError("Username or PW wrong");
                password.setError("Username or PW wrong");
            }
        });


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
