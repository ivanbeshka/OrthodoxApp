package com.example.orthodoxapp.ui.news;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.dataModel.Post;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.databinding.ListItemViewNewsBinding;

import java.util.LinkedList;
import java.util.List;

public class MyRecyclerViewNewsAdapter extends RecyclerView.Adapter<MyRecyclerViewNewsAdapter.ViewNewsHolder> {


    private List<Post> itemPost = new LinkedList<>();


    public void setData(List<Post> data) {
        itemPost.clear();
        itemPost.addAll(data);
    }


    @NonNull
    @Override
    public ViewNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemViewNewsBinding bindingNews = DataBindingUtil.inflate(inflater, R.layout.list_item_view_news, parent, false);
        return new ViewNewsHolder(bindingNews);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewNewsHolder holder, int position) {
        holder.bind(itemPost.get(position));

    }

    @Override
    public int getItemCount() {
        return itemPost.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewNewsHolder extends RecyclerView.ViewHolder {

        ListItemViewNewsBinding bindingNews;

        ViewNewsHolder(ListItemViewNewsBinding bindingNews) {
            super(bindingNews.getRoot());
            this.bindingNews = bindingNews;
        }

        void bind(Post post) {
            bindingNews.setPost(post);

            bindingNews.executePendingBindings();
        }

    }

}
