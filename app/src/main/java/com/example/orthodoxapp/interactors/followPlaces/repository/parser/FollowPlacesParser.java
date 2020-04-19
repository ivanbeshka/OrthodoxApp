package com.example.orthodoxapp.interactors.followPlaces.repository.parser;

import com.example.orthodoxapp.dataModel.FindPlace;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowPlacesParser {

  public FindPlace parseFollowPlace(String s) throws JSONException {
    JSONObject result = new JSONObject(s);
    JSONObject info = result.getJSONObject("result");

    String phone = "";
    if (info.has("formatted_phone_number")) {
      phone = info.getString("formatted_phone_number");
    }

    String photoRef = "";
    if (info.has("photos")) {
      photoRef = info.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
    }

    String nameOfChurch = info.getString("name");
    String address = info.getString("formatted_address");
    String placeId = info.getString("place_id");

    FindPlace findPlace = FindPlace.builder()
        .name(nameOfChurch)
        .id(placeId)
        .phoneNumber(phone)
        .street(address)
        .photoRef(photoRef)
        .build();

    return findPlace;
  }
}
