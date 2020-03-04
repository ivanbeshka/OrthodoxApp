package com.example.orthodoxapp.interactors.nearbyPlaces.repository.parser;

import com.example.orthodoxapp.dataModel.FindPlace;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NearbyPlacesParser {

  public ArrayList<FindPlace> parseFindPlaceList(String s) throws JSONException {

    ArrayList<FindPlace> findPlaces = new ArrayList<>();

    JSONObject resultObject = new JSONObject(s);
    JSONArray arrayWithPlaces = resultObject.getJSONArray("results");

    for (int i = 0; i < arrayWithPlaces.length(); i++) {

      JSONObject place = arrayWithPlaces.getJSONObject(i);
      JSONObject placeLocation = place.getJSONObject("geometry").getJSONObject("location");

      double lat = Double.valueOf(placeLocation.getString("lat"));
      double lng = Double.valueOf(placeLocation.getString("lng"));

      FindPlace findPlace = FindPlace.builder().lat(lat).lng(lng)
          .id(place.getString("place_id"))
          .name(place.getString("name"))
          .build();

      findPlaces.add(findPlace);

    }

    return findPlaces;
  }
}