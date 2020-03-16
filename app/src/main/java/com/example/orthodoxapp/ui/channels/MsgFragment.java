package com.example.orthodoxapp.ui.channels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orthodoxapp.R;

public class MsgFragment extends Fragment {

  private MyRecyclerViewMsgAdapter adapter;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    MsgViewModel msgViewModel = new ViewModelProvider(this).get(MsgViewModel.class);
    View root = inflater.inflate(R.layout.fragment_channels, container, false);

    RecyclerView recyclerViewMsg = root.findViewById(R.id.rv_channels);
    recyclerViewMsg.setLayoutManager(new LinearLayoutManager(getContext()));

    adapter = new MyRecyclerViewMsgAdapter(this);
    recyclerViewMsg.setAdapter(adapter);

    msgViewModel.getData().observe(getViewLifecycleOwner(), findPlaces -> {
        adapter.setData(findPlaces);
    });

    //decorations
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerViewMsg.getContext(),
        new LinearLayoutManager(getContext()).getOrientation());
    recyclerViewMsg.addItemDecoration(dividerItemDecoration);

    return root;
  }
}