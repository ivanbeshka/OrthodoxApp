package com.example.orthodoxapp.ui.msgs;

import androidx.lifecycle.ViewModel;

import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.dataModel.User;

import java.util.LinkedList;
import java.util.List;

public class MsgViewModel extends ViewModel {

    private List<Message> mMessage = new LinkedList<>();

    public MsgViewModel() {

        User user = User.builder().firstName("").build();

        Message msg = Message.builder()
                .addresseeUid("m2qKjOfgQ1caRyuxHSOt5HND5eF3")
                .textMessage("gfjsdgsdfibg").addresseeName("vasiliy").build();
        Message msg1 = Message.builder().addresseeUid("m2qKjOfgQ1caRyuxHSOt5HND5eF3")
                .textMessage("bsebgesrgieg").addresseeName("vasiliy").build();

        for (int i = 0; i < 10; i++) {
            mMessage.add(msg);
            mMessage.add(msg1);
        }



    }

    public List<Message> getmMessage() {
        return mMessage;
    }
}