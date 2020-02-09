package com.example.orthodoxapp.dataModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String firstName;
    private String surName;
    private String id;
    private String photo;
    private String email;

}
