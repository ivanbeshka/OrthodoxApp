package com.example.orthodoxapp.ui.msgs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.databinding.ListItemViewMsgBinding;

import java.util.LinkedList;
import java.util.List;


public class MyRecyclerViewMsgAdapter extends RecyclerView.Adapter<MyRecyclerViewMsgAdapter.ViewMsgHolder> {

    private List<Message> itemMsg = new LinkedList<>();

    public void setData(List<Message> data) {
        itemMsg.clear();
        itemMsg.addAll(data);
    }

    @NonNull
    @Override
    public ViewMsgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemViewMsgBinding bindingMsg = DataBindingUtil.inflate(inflater, R.layout.list_item_view_msg,
                parent, false);
        return new ViewMsgHolder(bindingMsg);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMsgHolder holder, int position) {
        holder.bind(itemMsg.get(position));
    }

    @Override
    public int getItemCount() {
        return itemMsg.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewMsgHolder extends RecyclerView.ViewHolder{

        ListItemViewMsgBinding bindingMsg;

        ViewMsgHolder(ListItemViewMsgBinding bindingMsg) {
            super(bindingMsg.getRoot());
            this.bindingMsg = bindingMsg;
        }

        void bind(Message msg){
            bindingMsg.setMessage(msg);

            bindingMsg.executePendingBindings();
        }
    }
}
