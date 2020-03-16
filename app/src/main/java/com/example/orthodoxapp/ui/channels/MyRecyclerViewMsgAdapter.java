package com.example.orthodoxapp.ui.channels;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.databinding.ItemChannelBinding;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewMsgAdapter extends
    RecyclerView.Adapter<MyRecyclerViewMsgAdapter.ViewHolderMsg>
    implements ItemClickListener {

  private List<FindPlace> places = new ArrayList<>();
  private Fragment fragment;

  MyRecyclerViewMsgAdapter(Fragment fragment) {
    this.fragment = fragment;
  }

  public void setData(List<FindPlace> data) {
    Log.d("pre setData", places.toString());
    places.clear();
    notifyDataSetChanged();
    places.addAll(data);
    Log.d("post setData", places.toString());
  }

  @NonNull
  @Override
  public ViewHolderMsg onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemChannelBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_channel,
        parent, false);

    return new ViewHolderMsg(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolderMsg holder, int position) {
    holder.bind(places.get(position));
    //clickListener
    holder.bindingChannel.setItemClickListener(this);
  }

  @Override
  public int getItemCount() {
    return places.size();
  }

  @Override
  public void onItemClick(FindPlace findPlace) {
    if (fragment.getActivity() != null) {
      Bundle bundle = new Bundle();
      bundle.putString("placeId", findPlace.getId());
      NavController navController = Navigation
          .findNavController(fragment.getActivity(), R.id.nav_host_fragment);
      navController.navigate(R.id.nav_dialog, bundle);
    }
  }

  // stores and recycles views as they are scrolled off screen
  static class ViewHolderMsg extends RecyclerView.ViewHolder {

    ItemChannelBinding bindingChannel;

    ViewHolderMsg(ItemChannelBinding bindingChannel) {
      super(bindingChannel.getRoot());
      this.bindingChannel = bindingChannel;
    }

    void bind(FindPlace findPlace) {
      bindingChannel.setPlace(findPlace);
      bindingChannel.executePendingBindings();
    }

  }

}

