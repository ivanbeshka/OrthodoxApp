package com.example.orthodoxapp.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import java.util.List;

public class MyRecyclerViewDialogAdapter extends RecyclerView.Adapter {

  private List<Message> itemMsg;

  private Context context;

  public MyRecyclerViewDialogAdapter(List<Message> itemMsg, @NonNull Context context) {
    this.itemMsg = itemMsg;
    this.context = context;
  }

  public void setData(List<Message> data) {
    itemMsg.clear();
    itemMsg.addAll(data);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    View viewChannelDialog = inflater.inflate(R.layout.item_channel_msg, parent, false);
    ViewHolderChannelDialog viewHolder = new ViewHolderChannelDialog(viewChannelDialog);

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

  }


  @Override
  public int getItemCount() {
    return 0;
  }


  public class ViewHolderChannelDialog extends RecyclerView.ViewHolder {

    private TextView tvMsgBody, tvMsgTime;

    ViewHolderChannelDialog(@NonNull View itemView) {
      super(itemView);
      tvMsgBody = itemView.findViewById(R.id.tv_channel_message_body);
      tvMsgTime = itemView.findViewById(R.id.tv_channel_msg_time);
    }
  }
}

