package com.example.orthodoxapp.ui.channels;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.orthodoxapp.R;

public class MsgFragment extends Fragment {

  private MyRecyclerViewMsgAdapter adapter;
  private SwipeRefreshLayout swipeRefreshLayout;
  private MsgViewModel msgViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {

    msgViewModel = new ViewModelProvider(this).get(MsgViewModel.class);
    View root = inflater.inflate(R.layout.fragment_channels, container, false);

    swipeRefreshLayout = root.findViewById(R.id.channels_refresh_layout);
    swipeRefreshLayout.setOnRefreshListener(this::refreshContent);
    NavController navController = Navigation
        .findNavController(getActivity(), R.id.nav_host_fragment);

    RecyclerView recyclerViewMsg = root.findViewById(R.id.rv_channels);
    recyclerViewMsg.setLayoutManager(new LinearLayoutManager(getContext()));

    adapter = new MyRecyclerViewMsgAdapter(this);
    recyclerViewMsg.setAdapter(adapter);

    //decorations
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerViewMsg.getContext(),
        new LinearLayoutManager(getContext()).getOrientation());
    recyclerViewMsg.addItemDecoration(dividerItemDecoration);

    navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
      if (destination.getId() == R.id.nav_msgs){
        swipeRefreshLayout.setRefreshing(true);
        refreshContent();
      }
    });

    return root;
  }

  private void refreshContent() {
    new Handler().postDelayed(() -> {
      adapter.setData(msgViewModel.getData());
      swipeRefreshLayout.setRefreshing(false);
    }, 4000);

  }

}