package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Lost_PW extends AppCompatActivity {

    private ImageView back_button;
    private TextView input_email;
    private Button send_email;
    private EditText email_ph;
    private TextView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_pw);

        back_button = (ImageView)findViewById(R.id.back_button);
        send_email = (Button)findViewById(R.id.btnSendEmail);
        input_email = (TextView)findViewById(R.id.email_stuck);
        email_ph = (EditText)findViewById(R.id.email_ph);
        notification = (TextView)findViewById(R.id.email_notification);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lost_PW.this, Start_Screen.class));
            }
        });


        /**
         * Makes the inputEmail not click and changeable again. So the user
         * knows to witch e-mail the reset code was send.
         */
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_ph.setVisibility(View.GONE);
                input_email.setVisibility(View.VISIBLE);
                input_email.setText(email_ph.getText());
                notification.setText("E-Mail was send");
            }
        });

    }


}
