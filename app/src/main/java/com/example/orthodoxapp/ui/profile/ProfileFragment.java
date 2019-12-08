package com.example.orthodoxapp.ui.profile;

import android.app.ActionBar;
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
import com.example.orthodoxapp.ui.profile.MyRecyclerViewProfileAdapter;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private MyRecyclerViewProfileAdapter adapter;
    private RecyclerView recyclerViewProfile;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.app_bar_profile, container, false);

        setHasOptionsMenu(true);



        recyclerViewProfile = root.findViewById(R.id.recyclerViewProfile);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewProfile.setLayoutManager(linearLayoutManager);
        adapter = new MyRecyclerViewProfileAdapter();
        adapter.setData(profileViewModel.getPosts());
        recyclerViewProfile.setAdapter(adapter);

        //decorations
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewProfile.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        recyclerViewProfile.addItemDecoration(dividerItemDecoration);

        return root;
    }
}