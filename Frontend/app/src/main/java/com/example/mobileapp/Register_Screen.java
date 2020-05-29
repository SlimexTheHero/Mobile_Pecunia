package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Register_Screen extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText pw;
    private EditText pw_confirm;

    private Button register;

    private ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        name = (EditText)findViewById(R.id.email_ph);
        email = (EditText)findViewById(R.id.editText2);
        pw = (EditText)findViewById(R.id.editText3);
        pw_confirm = (EditText)findViewById(R.id.editText4);
        register = (Button)findViewById(R.id.btnRegister);
        back_button = (ImageView)findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_start = new Intent(Register_Screen.this
                        , Start_Screen.class);
                startActivity(back_to_start);
            }
        });


    }

}
