package com.example.orthodoxapp.ui.dialog.create_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.orthodoxapp.R;

public class TimeFragment extends Fragment {

  private TimePicker timePicker;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_select_time, container, false);

    timePicker = root.findViewById(R.id.time_picker);
    timePicker.setIs24HourView(true);

    return root;
  }

  String getTime() {

    String hour = timePicker.getCurrentHour().toString();
    String minute = timePicker.getCurrentMinute().toString();
    if (hour.equals("0")) {
      hour = "00";
    }
    if (minute.equals("0") || minute.equals("1") || minute.equals("2") || minute.equals("3")
        || minute.equals("4") || minute.equals("5") || minute.equals("6") || minute.equals("7")
        || minute.equals("8") || minute.equals("9")) {

      minute = "0" + minute;
    }

    return hour + ":" + minute;
  }
}
