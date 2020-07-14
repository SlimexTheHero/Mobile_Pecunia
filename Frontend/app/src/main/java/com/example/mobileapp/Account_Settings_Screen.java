package com.example.mobileapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.mobileapp.logic.ImageEncoder;
import com.example.mobileapp.model.User;
import com.example.mobileapp.networking.RetrofitClient;
import com.example.mobileapp.networking.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    String userEmail;

    Button applyChanges;

    Boolean unlocked = false;

    Context context = this;

    UserService userService;


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
        userService = RetrofitClient.getRetrofitInstance().create(UserService.class);
        userEmail = getSharedPreferences("sharedPrefs", MODE_PRIVATE).getString("E-Mail","");
        changeNameHolder.setHint(getSharedPreferences("sharedPrefs", MODE_PRIVATE).getString("Name",""));



        userProfile.setOnClickListener(v -> changePicture());

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                changeNameHolder.clearFocus();
                changePWHolder.clearFocus();
                changePWConfirmationHolder.clearFocus();
                imm.hideSoftInputFromWindow(changeNameHolder.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(changePWHolder.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(changePWConfirmationHolder.getWindowToken(), 0);
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
                unlockPWLayout.setPadding(60, 0, 60, 30);
                unlockPWLayout.addView(unlockPWText);
                unlockPWLayout.setStartIconDrawable(R.drawable.pw_icon);
                unlockPWText.setSingleLine();
                unlockPWText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                unlockPWLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                unlock.setView(unlockPWLayout);
                unlock.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<User> call = userService.getUserByEmail(userEmail);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(response.body().getPassword().equals(unlockPWText.getText().toString().trim())){
                                    unlockPw.setVisibility(View.GONE);
                                    unlockPWInputLayout.setVisibility(View.VISIBLE);
                                    unlocked = true;
                                }else{
                                    Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });

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
                if (!unlocked) {
                    if (changeNameHolder.getEditText().getText().toString().isEmpty()) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), Settings_Screen.class));
                    } else {
                        Call<String> call = userService.changeNameOfUser(userEmail,changeNameHolder.getEditText().getText().toString().trim());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                                        .edit()
                                        .remove("E-Mail")
                                        .remove("Password")
                                        .remove("Name")
                                        .apply();
                                finish();
                                startActivity(new Intent(getApplicationContext(),Start_Screen.class));
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }


                } else {
                    if (changeNameHolder.getEditText().getText().toString().isEmpty() && changePWHolder.getEditText().getText().toString().isEmpty()) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), Settings_Screen.class));
                    } else if (changePWHolder.getEditText().getText().toString().isEmpty()) {
                        Call<String> call = userService.changeNameOfUser(userEmail,changeNameHolder.getEditText().getText().toString().trim());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                                        .edit()
                                        .remove("E-Mail")
                                        .remove("Password")
                                        .remove("Name")
                                        .apply();
                                Toast.makeText(getApplicationContext(),"Changes applied",Toast.LENGTH_SHORT);
                                finish();
                                startActivity(new Intent(getApplicationContext(),Start_Screen.class));
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });

                    }else{
                        if(validatePWConfirmation()&&changeNameHolder.getEditText().getText().toString().isEmpty()){
                            //Anfrage pw
                            Toast.makeText(context, "Nur PW ändern", Toast.LENGTH_SHORT).show();
                            Call<String> call = userService.changePWOfUser(userEmail,changePWText.getText().toString().trim());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                                            .edit()
                                            .remove("E-Mail")
                                            .remove("Password")
                                            .remove("Name")
                                            .apply();
                                    Toast.makeText(getApplicationContext(),"Changes applied",Toast.LENGTH_SHORT);
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),Start_Screen.class));
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });

                        }
                        else{
                            Call<String> call = userService.changeNameAndPWOfUser(userEmail,
                                    changeNameHolder.getEditText().getText().toString().trim(),
                                    changePWText.getText().toString().trim());
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                                            .edit()
                                            .remove("E-Mail")
                                            .remove("Password")
                                            .remove("Name")
                                            .apply();
                                    Toast.makeText(getApplicationContext(),"Changes applied",Toast.LENGTH_SHORT);
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),Start_Screen.class));
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });

                        }
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
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            userProfile.setImageURI(imageUri);
            InputStream imageStream = null;
/*                imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = new ImageEncoder().encodeImage(selectedImage);

                Call<String> call = userService.addImgToUser(encodedImage,getSharedPreferences
                        ("sharedPrefs", MODE_PRIVATE).getString("E-Mail",""));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.err.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Format nicht unterstützt", Toast.LENGTH_SHORT).show();
 */
        }
    }

    public void backButton(View view) {
        finish();
        startActivity(new Intent(this, Settings_Screen.class));
    }
}
