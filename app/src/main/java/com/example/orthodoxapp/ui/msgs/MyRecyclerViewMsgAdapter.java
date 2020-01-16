package com.example.orthodoxapp.ui.msgs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
    private Fragment fragment;

    public MyRecyclerViewMsgAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
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
        if(fragment.getActivity() != null){
            NavController navController = Navigation.findNavController(fragment.getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_dialog);
        }


    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolderMsg extends RecyclerView.ViewHolder {

        ItemViewMsgBinding bindingMsg;

        ViewHolderMsg(ItemViewMsgBinding bindingMsg) {
            super(bindingMsg.getRoot());
            this.bindingMsg = bindingMsg;
        }

        void bind(Message message) {
            bindingMsg.setMessage(message);
            bindingMsg.executePendingBindings();
        }

    }

}

