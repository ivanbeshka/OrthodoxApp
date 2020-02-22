package com.example.orthodoxapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 9001;

    private EditText etEmail, etPass;
    private Button btnLogin, btnCreateAcc, btnForgotPass;
    private View btnSignInGoogle;
    private ConstraintLayout root;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private Intent intentMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();

        intentMain = new Intent(getApplicationContext(), MainActivity.class);


        //registration Google account
        btnSignInGoogle.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        btnLogin.setOnClickListener(v -> {

            hideKeyboard(root);

            String email, password;
            email = etEmail.getText().toString();
            password = etPass.getText().toString();

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

            showProgressBar();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            startActivity(intentMain);

                        } else {

                            Toast.makeText(getApplicationContext(), R.string.email_or_pass_not_right, Toast.LENGTH_SHORT).show();
                        }


                    });

            hideProgressBar();
        });

        btnForgotPass.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class)));

        btnCreateAcc.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegistrationActivity.class)));
    }

    //activity gets Google api result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        showProgressBar();

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Snackbar.make(findViewById(R.id.login_layout), R.string.auth_fail, Snackbar.LENGTH_SHORT).show();
            }
        }

        hideProgressBar();
    }

    //auth if google account
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        showProgressBar();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(intentMain);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(findViewById(R.id.login_layout), R.string.auth_fail, Snackbar.LENGTH_SHORT).show();
                    }


                });

        hideProgressBar();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        btnCreateAcc = findViewById(R.id.btnRegister);
        btnForgotPass = findViewById(R.id.btnForgotPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle);
        root = findViewById(R.id.login_layout);
        setProgressBar(R.id.progressBarLogin);

    }

}
