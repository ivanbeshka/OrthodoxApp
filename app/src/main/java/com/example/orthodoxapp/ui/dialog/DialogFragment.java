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
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.example.orthodoxapp.ui.MainActivity;
import com.example.orthodoxapp.ui.dialog.DialogViewModel.DialogViewModelFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogFragment extends Fragment {

  private FindPlace findPlace;

  private ImageButton btnSendMsg;
  private EditText etMsg;
  private RecyclerView recyclerViewDialog;
  private DialogViewModel viewModel;

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
    assert getArguments() != null;
    findPlace = getArguments().getParcelable("place");

    viewModel = new ViewModelProvider(this,
        new DialogViewModelFactory(findPlace.getId())).get(DialogViewModel.class);

    initView(root);

    initDialog();

    btnSendMsg.setOnClickListener(v -> {
      sendMessage();
    });

    return root;
  }

  private void sendMessage() {
    String textMsg = etMsg.getText().toString();

    Date date = new Date();
    String pattern = "dd MMMM yyyy hh:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    Message msg = Message.builder()
        .placeUid(findPlace.getId())
        .textMessage(textMsg)
        .messageDate(sdf.format(date)).build();

    viewModel.addMsg(msg);

    etMsg.setText("");
  }

  private void initDialog() {
    viewModel.getData().observe(getViewLifecycleOwner(), observer);
  }

  private Observer<ArrayList<Message>> observer = new Observer<ArrayList<Message>>() {
    @Override
    public void onChanged(ArrayList<Message> messages) {
      RecyclerViewDialogAdapter dialogAdapter = new RecyclerViewDialogAdapter(messages,
          getContext());
      recyclerViewDialog.setLayoutManager(new LinearLayoutManager(getContext()));
      recyclerViewDialog.setAdapter(dialogAdapter);
    }
  };

  private void unsubscribe() {
    DatabaseReference databaseReference = FirebaseHelper
        .getUserFollowsChurchIdPath(findPlace.getId());

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

  private void initView(View root) {
    ((MainActivity) getActivity()).setToolbarTitle(findPlace.getName());
    LinearLayout postSendPanel = root.findViewById(R.id.layout_input_chat);
    checkIfActivist(postSendPanel);

    recyclerViewDialog = root.findViewById(R.id.recycler_view_dialog);
    btnSendMsg = root.findViewById(R.id.btn_send_msg);
    etMsg = root.findViewById(R.id.et_my_msg);
  }

  private void checkIfActivist(View postSendPanel) {
    DatabaseReference reference = FirebaseHelper.getUserActivistPath();
    reference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          postSendPanel.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

}
