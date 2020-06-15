package com.example.orthodoxapp.ui.channels;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;

public class ChannelFragment extends Fragment {

  private static final String TAG = "CHANNELFRAGMENT";

  private ChannelViewModel channelViewModel;
  private RecyclerView recyclerViewChannel;
  private ShimmerFrameLayout shimmerContainer;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    channelViewModel = new ViewModelProvider(requireActivity()).get(ChannelViewModel.class);
    initChannels();
    View root = inflater.inflate(R.layout.fragment_channels, container, false);

    recyclerViewChannel = root.findViewById(R.id.rv_channels);
    shimmerContainer = root.findViewById(R.id.shimmer_view_container);

    //decorations
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        recyclerViewChannel.getContext(),
        new LinearLayoutManager(getContext()).getOrientation());
    recyclerViewChannel.addItemDecoration(dividerItemDecoration);

    return root;
  }

  private void initChannels() {
    channelViewModel.getData().observe(getViewLifecycleOwner(), observer);
  }

  private Observer<ArrayList<FindPlace>> observer = new Observer<ArrayList<FindPlace>>() {
    @Override
    public void onChanged(ArrayList<FindPlace> places) {
      RecyclerViewChannelAdapter adapter = new RecyclerViewChannelAdapter(ChannelFragment.this,
          places);
      recyclerViewChannel.setLayoutManager(new LinearLayoutManager(getContext()));
      recyclerViewChannel.setAdapter(adapter);
    }
  };

  @Override
  public void onResume() {
    super.onResume();

    //this is crutch
    recyclerViewChannel.setVisibility(View.GONE);
    shimmerContainer.startShimmer();

    new Handler().postDelayed(() -> {
      shimmerContainer.stopShimmer();
      shimmerContainer.setVisibility(View.GONE);
      recyclerViewChannel.setVisibility(View.VISIBLE);
    }, 4000);

  }
}