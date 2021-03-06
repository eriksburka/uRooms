/*
 * If a user forgets their password they can input their email
 * and firebase will send that user a forgot password link
 */


package com.halaltokens.halaltokens.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.halaltokens.halaltokens.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {


    FirebaseAuth firebaseAuth;
    EditText editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById(R.id.forgot_password_button).setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.edit_text_email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_password_button:
                onClickForgot();
                break;
        }
    }

    public void onClickForgot() {

        String email = editEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid UCD email");
            editEmail.requestFocus();
            return;
        }

        if (!email.toLowerCase().contains("ucd.ie") && !email.toLowerCase().contains("ucdconnect.ie")) {
            editEmail.setError("Please enter a valid UCD email");
            editEmail.requestFocus();
            return;
        }
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Password Reset")
                        .setContentText("Your password reset link has been sent.")
                        .setConfirmClickListener(sweetAlertDialog -> {
                            Intent i = new Intent(ForgotPassword.this, LoginScreen.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        })
                        .show();


            } else {
                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
