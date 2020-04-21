package com.example.orthodoxapp.ui.dialog.create_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.orthodoxapp.R;

public class DateFragment extends Fragment {

  DatePicker datePicker;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_select_date, container, false);

    datePicker = root.findViewById(R.id.datePicker);

    return root;
  }

  String getDate() {
    int day = datePicker.getDayOfMonth();
    int month = datePicker.getMonth() + 1;
    int year = datePicker.getYear();

    return day + "/" + month + "/" + year;
  }

}
