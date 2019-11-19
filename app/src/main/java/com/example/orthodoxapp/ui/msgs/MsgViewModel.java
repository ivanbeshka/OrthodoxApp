package com.example.orthodoxapp.ui.msgs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MsgViewModel extends ViewModel {

    private ArrayList<String> mText;

    public MsgViewModel() {
        mText = new ArrayList<>();
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
        mText.add("Ivan");
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