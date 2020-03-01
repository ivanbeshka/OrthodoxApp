package com.example.orthodoxapp.interactors.followPlaces.repository.parser;

import com.example.orthodoxapp.dataModel.FindPlace;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowPlacesParser {

  public FindPlace parseFollowPlace(String s) throws JSONException{
    JSONObject result = new JSONObject(s);
    JSONObject info = result.getJSONObject("result");

    String nameOfChurch = info.getString("name");
    String phoneNumber = info.getString("formatted_phone_number");
    String address = info.getString("formatted_address");
    String placeId = info.getString("place_id");
    FindPlace findPlace = FindPlace.builder()
        .name(nameOfChurch)
        .id(placeId)
        .street(address)
        .phoneNumber(phoneNumber).build();

    return findPlace;
  }
}
