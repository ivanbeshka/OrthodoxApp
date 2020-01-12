package com.example.orthodoxapp.dataModel;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.val;


@Data
@Builder(toBuilder = true)
public class User {

    private String name;
    private String id;
    @val
    private  int phoneNumber;
    private int bDay;
    @Builder.Default
    private int friends = 0;
    private String city;
    private String photo;
    private String email;
    private String status;


}
