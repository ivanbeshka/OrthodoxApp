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

    builder.setMessage("Подписаться на " + churchName + " ?")
        .setPositiveButton("Да", (dialog, which) -> {
          DatabaseReference databaseReference = FirebaseHelper.getUserFollowsChurchIdPath(churchID);
          databaseReference.setValue(true);

          Toast.makeText(getContext(), "Вы успешно подписались", Toast.LENGTH_SHORT).show();
        })
        .setNegativeButton("Нет", (dialog, which) -> {
          dismiss();
        });

    return builder.create();
  }

}
