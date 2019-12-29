package com.example.orthodoxapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends BaseActivity {

    EditText etName, etEmail, etPassword, etRepeatPass;
    Button btnRegister, btnGoLogin;
    FirebaseAuth firebaseAuth;
    ConstraintLayout root;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String repPass = etRepeatPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), R.string.pls_enter_email, Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), R.string.pls_enter_right_email, Toast.LENGTH_LONG).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), R.string.pass_length, Toast.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(repPass)) {
                    Toast.makeText(getApplicationContext(), R.string.pass_not_equals, Toast.LENGTH_LONG).show();
                    return;
                }

                showProgressBar();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    hideKeyboard(root);
                                    Snackbar.make(root, R.string.right_registration, 8000).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.email_or_pass_not_right, Toast.LENGTH_SHORT).show();
                                }

                                hideProgressBar();
                            }
                        });

            }
        });

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    private void initViews() {
        etName = findViewById(R.id.etNameCreate);
        etEmail = findViewById(R.id.etEmailCreate);
        etPassword = findViewById(R.id.etPasswordCreate);
        etRepeatPass = findViewById(R.id.etRepeatPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoLogin = findViewById(R.id.btnGoToLogin);
        root = findViewById(R.id.rootRegister);
        setProgressBar(R.id.progressBarCreate);
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
