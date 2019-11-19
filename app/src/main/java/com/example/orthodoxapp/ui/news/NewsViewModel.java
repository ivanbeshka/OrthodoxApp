package com.example.orthodoxapp.ui.news;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NewsViewModel extends ViewModel {

    private ArrayList<String> mText;

    public NewsViewModel() {

        mText = new ArrayList<>();
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
    }

    public ArrayList<String> getText() {
        return mText;
    }
}