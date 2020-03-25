package com.example.orthodoxapp.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.databinding.ItemChannelMsgBinding;
import com.example.orthodoxapp.ui.dialog.RecyclerViewDialogAdapter.ViewHolderChannelDialog;
import java.util.ArrayList;

public class RecyclerViewDialogAdapter extends RecyclerView.Adapter<ViewHolderChannelDialog> {

  private ArrayList<Message> msgData;
  private Context context;

  RecyclerViewDialogAdapter(ArrayList<Message> msgData, Context context) {
    this.msgData = msgData;
    this.context = context;
  }

  @NonNull
  @Override
  public ViewHolderChannelDialog onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemChannelMsgBinding binding = DataBindingUtil
        .inflate(LayoutInflater.from(context),
            R.layout.item_channel_msg,
            parent, false);

    return new ViewHolderChannelDialog(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolderChannelDialog holder, int position) {
    holder.bind(msgData.get(position));
  }

  @Override
  public int getItemCount() {
    return msgData.size();
  }


  static class ViewHolderChannelDialog extends RecyclerView.ViewHolder {

    ItemChannelMsgBinding msgBinding;

    ViewHolderChannelDialog(ItemChannelMsgBinding msgBinding) {
      super(msgBinding.getRoot());
      this.msgBinding = msgBinding;
    }

    void bind(Message message) {
      msgBinding.setMessage(message);
      msgBinding.executePendingBindings();
    }
  }
}

