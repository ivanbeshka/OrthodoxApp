package com.example.orthodoxapp.ui.dialog.create_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.orthodoxapp.R;

public class CreateEventFragment extends DialogFragment {

  private Button btnCancel;
  private Button btnContinue;
  private Button btnAddDateAndTime;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_create_event, container, false);

    initView(root);
    btnCancel.setOnClickListener(v -> dismiss());
    btnContinue.setOnClickListener(v -> dismiss());
    btnAddDateAndTime.setOnClickListener(v -> {
      FragmentTransaction ft = getParentFragmentManager().beginTransaction();
      Fragment prev = getParentFragmentManager().findFragmentByTag("select_time_and_date");
      if (prev != null){
        ft.remove(prev);
      }
      ft.addToBackStack(null);
      SelectTimeAndDateFragment selectTimeAndDateFragment = new SelectTimeAndDateFragment();
      selectTimeAndDateFragment.show(ft, "select_time_and_date");
    });

    return root;
  }

  private void initView(View root) {
    btnCancel = root.findViewById(R.id.btn_cancel_timetable);
    btnContinue = root.findViewById(R.id.btn_continue_timetable);
    btnAddDateAndTime = root.findViewById(R.id.btn_add_date);
  }

  @Override
  public void onResume() {
    super.onResume();
    ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
    params.height = LayoutParams.MATCH_PARENT;
    params.width = LayoutParams.MATCH_PARENT;
    getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
  }
}
