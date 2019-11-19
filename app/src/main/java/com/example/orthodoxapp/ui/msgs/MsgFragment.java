package com.example.orthodoxapp.ui.msgs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.R;

public class MsgFragment extends Fragment {

    private MsgViewModel msgViewModel;
    private MyRecyclerViewMsgAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        msgViewModel = ViewModelProviders.of(this).get(MsgViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        final RecyclerView recyclerViewMsg = root.findViewById(R.id.recyclerViewMsg);
        recyclerViewMsg.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewMsgAdapter(getContext(), msgViewModel.getText());
        recyclerViewMsg.setAdapter(adapter);

        //decorations
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMsg.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        recyclerViewMsg.addItemDecoration(dividerItemDecoration);

        return root;
    }
}