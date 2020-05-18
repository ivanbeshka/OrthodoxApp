package com.example.orthodoxapp.ui.dialog.create_event;

import static com.example.orthodoxapp.ui.dialog.DialogFragment.RC_CREATE_EVENT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.orthodoxapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;

public class CreateEventFragment extends DialogFragment {

  static final int RC_TARGET_FRAGMENT_TIME = 5000;
  final static int RC_TARGET_FRAGMENT_DATE = 4000;

  private Button btnCancel;
  private Button btnContinue;
  private Button btnAddDate;
  private Button btnAddTime;

  private TextView tvDate;
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
      if (tvDate.getText().equals("") ||
          tvTime.getText().equals("") || etDescription.getText().toString().equals("")) {
        Toast.makeText(getContext(), R.string.not_all_inf, Toast.LENGTH_LONG).show();
      } else {
        Intent intent = new Intent();
        String msg = "Дата: " + tvDate.getText() + "\n" +
            "Время: " + tvTime.getText() + "\n" +
            "Описание: " + "\n" + etDescription.getText().toString();
        intent.putExtra("msg", msg);
        getTargetFragment().onActivityResult(RC_CREATE_EVENT, 1, intent);
        dismiss();
      }
    });

    btnAddDate.setOnClickListener(v -> {

      MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder
          .dateRangePicker();
      builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
      builder.setTitleText("Выберите дату");
      builder.setTheme(resolveOrThrow(getContext()));
      MaterialDatePicker picker = builder.build();
      picker.addOnPositiveButtonClickListener(selection -> tvDate.setText(picker.getHeaderText()));
      picker.show(getParentFragmentManager(), picker.toString());

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

    if (requestCode == RC_TARGET_FRAGMENT_TIME) {
      tvTime.setText(data.getStringExtra("time"));
    }
  }

  private void initView(View root) {
    btnCancel = root.findViewById(R.id.btn_cancel_timetable);
    btnContinue = root.findViewById(R.id.btn_continue_timetable);
    btnAddDate = root.findViewById(R.id.btn_add_date);
    btnAddTime = root.findViewById(R.id.btn_add_time);

    tvDate = root.findViewById(R.id.tv_date);
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

  //get theme for datepicker
  private static int resolveOrThrow(Context context) {
    TypedValue typedValue = new TypedValue();
    if (context.getTheme().resolveAttribute(R.attr.materialCalendarTheme, typedValue, true)) {
      return typedValue.data;
    }
    throw new IllegalArgumentException(context.getResources().getResourceName(
        R.attr.materialCalendarTheme));
  }
}
