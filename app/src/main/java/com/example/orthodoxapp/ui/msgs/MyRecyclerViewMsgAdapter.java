package com.example.orthodoxapp.ui.msgs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.MainActivity;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.databinding.ItemViewMsgBinding;


import java.util.LinkedList;
import java.util.List;

public class MyRecyclerViewMsgAdapter extends RecyclerView.Adapter<MyRecyclerViewMsgAdapter.ViewHolderMsg>
        implements ItemClickListener {

    private List<Message> listMsg = new LinkedList<>();
    private Context context;
    private FragmentTransaction fragmentTransaction;

    public MyRecyclerViewMsgAdapter(Context context, FragmentTransaction fragmentTransaction) {
        this.context = context;
        this.fragmentTransaction = fragmentTransaction;
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
        holder.bind(listMsg.get(position));
        //clickListener
        holder.bindingMsg.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public void onItemClick(Message message) {

//        fragmentTransaction.replace(R.id.nav_host_fragment, new DialogFragment());
//        fragmentTransaction.commit();
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

