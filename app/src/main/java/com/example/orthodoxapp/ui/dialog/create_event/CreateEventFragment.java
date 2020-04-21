package com.example.orthodoxapp.ui.dialog.create_event;

import static com.example.orthodoxapp.ui.dialog.DialogFragment.RC_CREATE_EVENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.orthodoxapp.R;

public class CreateEventFragment extends DialogFragment {

  static final int RC_TARGET_FRAGMENT_TIME = 5000;
  final static int RC_TARGET_FRAGMENT_DATE = 4000;

  private Button btnCancel;
  private Button btnContinue;
  private Button btnAddDate;
  private Button btnAddTime;

  private TextView tvFromDate;
  private TextView tvToDate;
  private TextView tvTime;
  private EditText etDescription;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_create_event, container, false);

    initView(root);
    btnCancel.setOnClickListener(v -> dismiss());
    btnContinue.setOnClickListener(v -> {
      if (tvFromDate.getText().equals("") || tvToDate.getText().equals("")
          || tvTime.getText().equals("") || etDescription.getText().toString().equals("")) {
        Toast.makeText(getContext(), R.string.not_all_inf, Toast.LENGTH_LONG).show();
      }else {
        Intent intent = new Intent();
        String msg = "From " + tvFromDate.getText() + " to " + tvToDate.getText() + "\n" +
            "Time: " + tvTime.getText() + "\n" +
            "Description: "+ "\n" + etDescription.getText().toString();
        intent.putExtra("msg", msg);
        getTargetFragment().onActivityResult(RC_CREATE_EVENT, 1, intent);
      }
    });

    btnAddDate.setOnClickListener(v -> {
      FragmentTransaction ft = getParentFragmentManager().beginTransaction();
      Fragment prev = getParentFragmentManager().findFragmentByTag("select_date");
      if (prev != null) {
        ft.remove(prev);
      }
      ft.addToBackStack(null);
      SelectDateFragment selectDateFragment = new SelectDateFragment();
      selectDateFragment.setTargetFragment(this, RC_TARGET_FRAGMENT_DATE);
      selectDateFragment.show(ft, "select_date");
    });

    btnAddTime.setOnClickListener(v -> {
      FragmentTransaction ft = getParentFragmentManager().beginTransaction();
      Fragment prev = getParentFragmentManager().findFragmentByTag("select_time");
      if (prev != null) {
        ft.remove(prev);
      }
      ft.addToBackStack(null);
      SelectTimeFragment fragment = new SelectTimeFragment();
      fragment.setTargetFragment(this, RC_TARGET_FRAGMENT_TIME);
      fragment.show(ft, "select_time");
    });

    return root;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_TARGET_FRAGMENT_DATE) {
      tvFromDate.setText(data.getStringExtra("fromDate"));
      tvToDate.setText(data.getStringExtra("toDate"));
    }

    if (requestCode == RC_TARGET_FRAGMENT_TIME) {
      tvTime.setText(data.getStringExtra("time"));
    }
  }

  private void initView(View root) {
    btnCancel = root.findViewById(R.id.btn_cancel_timetable);
    btnContinue = root.findViewById(R.id.btn_continue_timetable);
    btnAddDate = root.findViewById(R.id.btn_add_date);
    btnAddTime = root.findViewById(R.id.btn_add_time);

    tvFromDate = root.findViewById(R.id.tv_date_from);
    tvToDate = root.findViewById(R.id.tv_date_to);
    tvTime = root.findViewById(R.id.tv_time);
    etDescription = root.findViewById(R.id.et_description);
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
