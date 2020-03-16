package com.example.orthodoxapp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.orthodoxapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogFragment extends Fragment {

  private DialogViewModel mViewModel;
  private MyRecyclerViewDialogAdapter dialogAdapter;
  private String placeId;

  private FirebaseUser firebaseUser;
  private DatabaseReference database;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    inflater.inflate(R.menu.menu_channel, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();

    switch (id) {
      case R.id.menu_item_unsubscribe:
        unsubscribe();
    }
    return super.onOptionsItemSelected(item);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_dialog, container, false);

    ImageButton btnSendMsg = root.findViewById(R.id.btn_send_msg);
    EditText etMsg = root.findViewById(R.id.et_my_msg);

    assert getArguments() != null;
    placeId = getArguments().getString("placeId");

    initializeDialog();

    return root;
  }

  private void unsubscribe() {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
        .getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        .child("follows").child(placeId);

    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        dataSnapshot.getRef().removeValue();
        Toast.makeText(getContext(), "unsubscribe successful", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  private void initializeDialog() {

  }

}
