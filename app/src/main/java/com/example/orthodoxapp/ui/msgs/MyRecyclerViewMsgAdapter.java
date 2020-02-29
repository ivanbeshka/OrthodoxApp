package com.example.orthodoxapp.ui.msgs;

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
import com.example.orthodoxapp.databinding.ItemViewMsgBinding;
import java.util.LinkedList;
import java.util.List;

public class MyRecyclerViewMsgAdapter extends RecyclerView.Adapter<MyRecyclerViewMsgAdapter.ViewHolderMsg>
        implements ItemClickListener {

    private List<FindPlace> places = new LinkedList<>();
    private Fragment fragment;

    MyRecyclerViewMsgAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(List<FindPlace> data) {
        places.clear();
        places.addAll(data);
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
        holder.bind(places.get(position));
        //clickListener
        holder.bindingMsg.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public void onItemClick(FindPlace findPlace) {
        if(fragment.getActivity() != null){
            Log.d("dialog", "preinit");
            Bundle bundle = new Bundle();
            //bundle.putParcelable("msg", );
            NavController navController = Navigation.findNavController(fragment.getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_dialog, bundle);
        }
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolderMsg extends RecyclerView.ViewHolder {

        ItemViewMsgBinding bindingMsg;

        ViewHolderMsg(ItemViewMsgBinding bindingMsg) {
            super(bindingMsg.getRoot());
            this.bindingMsg = bindingMsg;
        }

        void bind(FindPlace findPlace) {
            bindingMsg.setPlace(findPlace);
            bindingMsg.executePendingBindings();
        }

    }

}

