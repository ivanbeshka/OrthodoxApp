package com.example.orthodoxapp.dataModel;

import java.util.Date;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Message {

    private UUID msgId;
    private User sender;
    private User addressee;
    private String textMessage;
    @Builder.Default
    private Date messageDate = new Date();

}
