package com.example.orthodoxapp.dataModel;

public class FindPlace {

    private double lat;
    private double lng;
    private String id;
    private String name;
    private String street;

    public FindPlace(double lat, double lng, String id, String name, String street) {
        this.lat = lat;
        this.lng = lng;
        this.id = id;
        this.name = name;
        this.street = street;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }
}
