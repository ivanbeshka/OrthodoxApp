package com.example.orthodoxapp.interactors.followPlaces.repository.parser;

import android.util.Log;
import com.example.orthodoxapp.dataModel.FindPlace;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowPlacesParser {

  public FindPlace parseFollowPlace(String s) throws JSONException {
    Log.d("RAW_PLACE", s);
    JSONObject result = new JSONObject(s);
    JSONObject info = result.getJSONObject("result");

    String phone = null;
    if (info.has("formatted_phone_number")) {
       phone = info.getString("formatted_phone_number");
    }

    String nameOfChurch = info.getString("name");
    String address = info.getString("formatted_address");
    String placeId = info.getString("place_id");
    FindPlace findPlace = FindPlace.builder()
        .name(nameOfChurch)
        .id(placeId)
        .phoneNumber(phone)
        .street(address)
        .build();

    return findPlace;
  }
}
