package com.example.mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account_Settings_Screen extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    CircleImageView userProfile;
    Uri imageUri;
    LinearLayout accountLayout;

    TextInputLayout changeNameHolder;
    TextInputEditText changeName;

    LinearLayout unlockPw;
    LinearLayout unlockPWInputLayout;

    TextInputLayout changePWHolder;
    TextInputEditText changePWText;

    TextInputLayout changePWConfirmationHolder;
    TextInputEditText changePWConfirmationText;

    Button applyChanges;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings_screen);

        userProfile = findViewById(R.id.user_picture);
        accountLayout = findViewById(R.id.account_settings_layout);
        changeNameHolder = findViewById(R.id.change_name_holder);
        changeName = findViewById(R.id.change_name);
        unlockPw = findViewById(R.id.unlock_pw);
        unlockPWInputLayout = findViewById(R.id.input_pw_changes);
        applyChanges = findViewById(R.id.applyChanges);
        changePWHolder = findViewById(R.id.change_pw_holder);
        changePWText = findViewById(R.id.change_pw);
        changePWConfirmationHolder = findViewById(R.id.change_pw_confirm_holder);
        changePWConfirmationText = findViewById(R.id.change_pw_confirm);


        userProfile.setOnClickListener(v -> changePicture());

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                changeName.clearFocus();
                changePWText.clearFocus();
                changePWConfirmationText.clearFocus();
                imm.hideSoftInputFromWindow(changeName.getWindowToken(),0);
                imm.hideSoftInputFromWindow(changePWText.getWindowToken(),0);
                imm.hideSoftInputFromWindow(changePWConfirmationText.getWindowToken(),0);
            }
        });

        unlockPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder unlock = new MaterialAlertDialogBuilder(context);
                final TextInputLayout unlockPWLayout = new TextInputLayout(context);
                final TextInputEditText unlockPWText = new TextInputEditText(context);
                unlock.setTitle("Unlock password change");
                unlock.setMessage("To unlock the password change, confirm your actual password.");
                unlockPWText.setHint("Actual password");
                unlockPWLayout.setPadding(60,0,60,30);
                unlockPWLayout.addView(unlockPWText);
                unlockPWLayout.setStartIconDrawable(R.drawable.pw_icon);
                unlockPWText.setSingleLine();
                unlockPWText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                unlockPWLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                unlock.setView(unlockPWLayout);
                unlock.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Hier wird der Check mit dem momentanen PW stattfinden
                        unlockPw.setVisibility(View.GONE);
                        unlockPWInputLayout.setVisibility(View.VISIBLE);
                    }
                });
                unlock.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                unlock.show();
            }
        });

        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unlockPw.getVisibility() != View.VISIBLE) {
                    if (!validatePWConfirmation()) {
                        return;
                    } else {
                        Toast.makeText(context, "Changes applied", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    if (changeNameHolder.getEditText().getText().toString().isEmpty() | changePWHolder.getEditText().getText().toString().isEmpty()) {
                        Toast.makeText(context, "No changes were made", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "Changes applied", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    private boolean validatePWConfirmation() {
        if (changePWConfirmationHolder.getEditText().getText().toString().isEmpty()) {
            changePWConfirmationHolder.setError("Field cannot be empty if you want to change the password");
            return false;
        } else if (!changePWConfirmationHolder.getEditText().getText().toString().equals(changePWHolder.getEditText().getText().toString())) {
            changePWConfirmationHolder.setError("The password doesn´t match");
            return false;
        } else {
            changePWConfirmationHolder.setError(null);
            return true;
        }
    }

    public void changePicture() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE ) {
            imageUri = data.getData();
            userProfile.setImageURI(imageUri);
        }
    }

    public void backButton(View view) {
        finish();
    }
}
