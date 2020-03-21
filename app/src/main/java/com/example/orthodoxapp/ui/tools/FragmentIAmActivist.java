package com.example.orthodoxapp.ui.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.orthodoxapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentIAmActivist extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("activist");

    alertDialog.setMessage(R.string.agreement)
        .setPositiveButton("agree", (dialog, which) -> {
          reference.setValue(true);
        })
        .setNegativeButton("not agree", (dialog, which) -> {
          dialog.dismiss();
        });

    return alertDialog.create();
  }
}
