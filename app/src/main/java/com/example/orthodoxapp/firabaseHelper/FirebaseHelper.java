package com.example.orthodoxapp.firabaseHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

  private static String getUserUid() {
    return FirebaseAuth.getInstance().getCurrentUser().getUid();
  }

  public static FirebaseUser getFirebaseUser() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }

  public static DatabaseReference getUserFollowsChurchIdPath(String churchId) {
    return FirebaseDatabase.getInstance()
        .getReference("users").child(getUserUid()).child("follows").child(churchId);
  }

  public static DatabaseReference getUserFollowsPath() {
    return FirebaseDatabase.getInstance()
        .getReference("users").child(getUserUid()).child("follows");
  }

  public static DatabaseReference getUserActivistPath() {
    return FirebaseDatabase.getInstance().getReference("users").child(getUserUid())
        .child("activist");
  }

  public static DatabaseReference getChurchMsgPath(String churchId) {
    return FirebaseDatabase.getInstance().getReference("places").child(churchId).child("msgs");
  }

}
