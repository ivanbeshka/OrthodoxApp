package com.example.orthodoxapp.ui.news;

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

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private MyRecyclerViewNewsAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);


        final RecyclerView recyclerViewNews = root.findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewNewsAdapter();
        adapter.setData(newsViewModel.getPosts());
        recyclerViewNews.setAdapter(adapter);


        //decorations
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewNews.getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        recyclerViewNews.addItemDecoration(dividerItemDecoration);

        return root;
    }
}