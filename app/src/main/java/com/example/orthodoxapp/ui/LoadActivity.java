package com.example.orthodoxapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadActivity extends AppCompatActivity {

  private FirebaseAuth firebaseAuth;
  private FirebaseAuth.AuthStateListener authStateListener;

  public static PlacesClient placesClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme_Launcher);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load);

    initPlaceClient();
    authorizeInFirebase();
  }

  private void authorizeInFirebase() {
    firebaseAuth = FirebaseAuth.getInstance();
    authStateListener = firebaseAuth -> {
      FirebaseUser user = firebaseAuth.getCurrentUser();
      if (user == null) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
      } else {
        user = FirebaseHelper.getFirebaseUser();
        if (user != null) {
          Toast.makeText(getApplicationContext(), "Hello " + user.getDisplayName() + " !",
              Toast.LENGTH_LONG)
              .show();
          startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
      }
      finish();
    };
  }

  private void initPlaceClient() {
    if (!Places.isInitialized()) {
      Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
    }
    placesClient = Places.createClient(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    firebaseAuth.addAuthStateListener(authStateListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (authStateListener != null) {
      firebaseAuth.removeAuthStateListener(authStateListener);
    }
  }
}
