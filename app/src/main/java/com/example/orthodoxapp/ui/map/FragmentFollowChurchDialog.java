package com.example.orthodoxapp.ui.map;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import org.jetbrains.annotations.NotNull;


public class FragmentFollowChurchDialog extends DialogFragment {

  private String churchID;
  private String churchName;

  FragmentFollowChurchDialog(String churchID, String churchName) {
    this.churchID = churchID;
    this.churchName = churchName;
  }

  @NotNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    builder.setMessage("Follow on " + churchName + " ?")
        .setPositiveButton("Follow", (dialog, which) -> {
          DatabaseReference databaseReference = FirebaseHelper.getUserFollowsChurchIdPath(churchID);
          databaseReference.setValue(true);

          Toast.makeText(getContext(), "Follow successful", Toast.LENGTH_SHORT).show();
        });

    return builder.create();
  }

}
