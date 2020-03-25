package com.example.orthodoxapp.ui.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class FragmentIAmActivist extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

    DatabaseReference reference = FirebaseHelper.getUserActivistPath();

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
