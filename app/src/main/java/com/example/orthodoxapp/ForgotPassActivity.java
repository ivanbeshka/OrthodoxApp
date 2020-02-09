package com.example.orthodoxapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends BaseActivity {

    private EditText etForgotEmail;
    private Button btnSendPass;
    private FirebaseAuth firebaseAuth;
    private ConstraintLayout root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();

        firebaseAuth = FirebaseAuth.getInstance();

        btnSendPass.setOnClickListener(v -> {

            hideKeyboard(root);

            String email = etForgotEmail.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), R.string.pls_enter_email, Toast.LENGTH_LONG).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), R.string.pls_enter_right_email, Toast.LENGTH_LONG).show();
                return;
            }

            showProgressBar();

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), R.string.rest_right,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.error_send_pass,Toast.LENGTH_SHORT).show();
                }

                hideProgressBar();
            });

        });
    }

    private void initViews() {
        etForgotEmail = findViewById(R.id.etForgotEmail);
        btnSendPass = findViewById(R.id.btnPassGiveMe);
        root = findViewById(R.id.forgotPassLayout);
        setProgressBar(R.id.progressBarForgot);
    }
}
