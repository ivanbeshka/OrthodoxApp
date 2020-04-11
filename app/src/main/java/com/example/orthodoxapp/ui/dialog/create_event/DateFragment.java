package com.example.orthodoxapp.ui.dialog.create_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.orthodoxapp.R;

public class DateFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_select_date, container, false);
    return root;
  }

  static DateFragment newInstance(int position){
    DateFragment fragment = new DateFragment();
    Bundle args = new Bundle();
    args.putInt("POS", position);
    fragment.setArguments(args);
    return fragment;
  }
}
