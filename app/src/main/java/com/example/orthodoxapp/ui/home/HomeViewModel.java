package com.example.orthodoxapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private ArrayList<String> mText;

    public HomeViewModel() {

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