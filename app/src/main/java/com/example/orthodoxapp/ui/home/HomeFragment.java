package com.example.orthodoxapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.MainActivity;
import com.example.orthodoxapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MyRecyclerViewHomeAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        final RecyclerView recyclerViewHome = root.findViewById(R.id.recyclerViewHome);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewHomeAdapter(getContext(), homeViewModel.getText());
        recyclerViewHome.setAdapter(adapter);

        //decorations
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewHome.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        recyclerViewHome.addItemDecoration(dividerItemDecoration);

        return root;
    }
}