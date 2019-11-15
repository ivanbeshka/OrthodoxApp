package com.example.orthodoxapp.ui.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orthodoxapp.R;

import java.util.ArrayList;

public class MyRecyclerViewHomeAdapter extends RecyclerView.Adapter<MyRecyclerViewHomeAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private ArrayList<String> mData;

    // data is passed into the constructor
    MyRecyclerViewHomeAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_view_rvh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String people = mData.get(position);
        holder.myTextView.setText(people);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.FSName);
        }

        @Override
        public void onClick(View view) {
        }
    }

    String getItem(int id) {
        return mData.get(id);
    }

}
