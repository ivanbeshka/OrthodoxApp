package com.example.orthodoxapp.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.ui.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ToolsFragment extends Fragment {

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_tools, container, false);

    Button btnLogOut = root.findViewById(R.id.btn_logout);
    btnLogOut.setOnClickListener(v -> {

      FirebaseAuth.getInstance().signOut();
      Intent intent = new Intent(getContext(), LoginActivity.class);
      startActivity(intent);
      getActivity().finish();
    });

    Button btnActivist = root.findViewById(R.id.btn_im_activist);

    if (FirebaseAuth.getInstance().getCurrentUser().isAnonymous()) {
      btnActivist.setVisibility(View.GONE);
      btnLogOut.setText("Выйти");
    }

    btnActivist.setOnClickListener(v -> {
      new FragmentIAmActivist().show(getParentFragmentManager(), "show activist dialog");
    });

    return root;
  }
}