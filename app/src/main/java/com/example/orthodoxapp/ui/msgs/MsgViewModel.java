package com.example.orthodoxapp.ui.msgs;

import androidx.lifecycle.ViewModel;

import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.dataModel.User;

import java.util.LinkedList;
import java.util.List;

public class MsgViewModel extends ViewModel {

    private List<Message> mMessage = new LinkedList<>();

    public MsgViewModel() {

        User user = User.builder().name("vasiliy").status("online").build();

        Message msg = Message.builder().addressee(user).textMessage("gfjsdgsdfibg").build();

        for (int i = 0; i < 50; i++) {
            mMessage.add(msg);
        }


    }

    public List<Message> getmMessage() {
        return mMessage;
    }
}