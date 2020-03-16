package com.example.orthodoxapp.ui.map;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.jetbrains.annotations.NotNull;


public class FragmentFollowChurchDialog extends DialogFragment {

    private String churchID;

    FragmentFollowChurchDialog(String churchID) {
        this.churchID = churchID;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        builder.setMessage("Follow on church?")
                .setPositiveButton("Follow", (dialog, which) -> {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("users").child(uid).child("follows").child(churchID);
                    databaseReference.setValue(true);


                    Toast.makeText(getContext(), "Follow successful", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                });

        return builder.create();
    }

}
