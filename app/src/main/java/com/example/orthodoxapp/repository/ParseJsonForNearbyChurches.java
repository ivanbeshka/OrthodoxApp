package com.example.orthodoxapp.repository;

import android.content.Context;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.GetDataFromUrl;
import com.example.orthodoxapp.repository.createUrl.CreateUrlForNearbyChurches;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJsonForNearbyChurches implements GetDataFromUrl.AsyncResponse {

    private double mLat;
    private double mLng;
    private Context context;

    private CallbackNearbyChurches delegate;

    public ParseJsonForNearbyChurches(double lat, double lng, CallbackNearbyChurches delegate, Context context) {
        mLat = lat;
        mLng = lng;
        this.delegate = delegate;
        this.context = context;
    }

    public void parse(){

        CreateUrlForNearbyChurches url = new CreateUrlForNearbyChurches(mLat, mLng, context);
        GetDataFromUrl getDataFromUrl = new GetDataFromUrl(this);
        getDataFromUrl.execute(url.getNearbyChurchesUrl());
    }

    @Override
    public void getData(String s) {

        ArrayList<FindPlace> findPlaces = new ArrayList<>();

        try {
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        delegate.nearbyChurches(findPlaces);
    }

    public interface CallbackNearbyChurches{
        void nearbyChurches(ArrayList<FindPlace> churches);
    }
}
