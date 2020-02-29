package com.example.orthodoxapp.dataModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class FindPlace {

    private double lat;
    private double lng;
    private String id;
    private String name;
    private String street;
    private String phoneNumber;
}
