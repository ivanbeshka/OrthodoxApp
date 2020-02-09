package com.example.orthodoxapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.orthodoxapp.dataModel.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends BaseActivity {

    EditText etFirstName, etSurName, etEmail, etPassword, etRepeatPass;
    Button btnRegister, btnGoLogin;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference database;

    ConstraintLayout root;


    //TODO realisation
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {

            hideKeyboard(root);

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String repPass = etRepeatPass.getText().toString();
            String firstName = etFirstName.getText().toString();
            String surName = etSurName.getText().toString();


            //checks valid edit text
            if (TextUtils.isEmpty(firstName)) {
                Toast.makeText(getApplicationContext(), "Please, enter first name", Toast.LENGTH_LONG).show();
            }

            if (TextUtils.isEmpty(surName)) {
                Toast.makeText(getApplicationContext(), "Please, enter surname", Toast.LENGTH_LONG).show();
            }

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

            Toast.makeText(this, R.string.please_wait, Toast.LENGTH_SHORT).show();
            showProgressBar();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {


                            firebaseUser = firebaseAuth.getCurrentUser();
                            User user = new User(firstName, surName, firebaseUser.getUid(), "default", email);
                                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(email)
                                    .build();
                            firebaseUser.updateProfile(profileUpdate);

                            addUserToFirebase(user);

                            Snackbar.make(root, R.string.right_registration, 3000).show();

                        } else {
                            Toast.makeText(getApplicationContext(), R.string.email_or_pass_not_right, Toast.LENGTH_SHORT).show();
                        }

                        hideProgressBar();
                    });

        });

        btnGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstNameCreate);
        etSurName = findViewById(R.id.etSurNameCreate);
        etEmail = findViewById(R.id.etEmailCreate);
        etPassword = findViewById(R.id.etPasswordCreate);
        etRepeatPass = findViewById(R.id.etRepeatPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoLogin = findViewById(R.id.btnGoToLogin);
        root = findViewById(R.id.rootRegister);
        setProgressBar(R.id.progressBarCreate);
    }

    private void addUserToFirebase(User user){

        //give user id
        String uid = firebaseUser.getUid();

        //connection to database
        database = FirebaseDatabase.getInstance().getReference("users").child(uid);

        //add user data to firebase
        database.setValue(user);
    }
}
