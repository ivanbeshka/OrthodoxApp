package com.example.orthodoxapp.ui.msgs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.databinding.ItemViewMsgBinding;

import java.util.LinkedList;
import java.util.List;

public class MyRecyclerViewMsgAdapter extends RecyclerView.Adapter<MyRecyclerViewMsgAdapter.ViewHolderMsg>
        implements ItemClickListener {

    private List<Message> listMsg = new LinkedList<>();
    private Context context;

    public MyRecyclerViewMsgAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Message> data) {
        listMsg.clear();
        listMsg.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolderMsg onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewMsgBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_view_msg,
                parent, false);

        return new ViewHolderMsg(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMsg holder, int position) {
        Message msg = listMsg.get(position);
        holder.bind(msg);
        //clickListener
        holder.bindingMsg.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public void onItemClick(Message message) {
        Toast.makeText(context, message.getTextMessage(), Toast.LENGTH_LONG).show();
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolderMsg extends RecyclerView.ViewHolder {

        ItemViewMsgBinding bindingMsg;

        ViewHolderMsg(ItemViewMsgBinding bindingMsg) {
            super(bindingMsg.getRoot());
            this.bindingMsg = bindingMsg;
        }

        public void bind(Message message) {
            bindingMsg.setMessage(message);
            bindingMsg.executePendingBindings();
        }

    }

}

