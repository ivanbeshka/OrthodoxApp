package com.example.orthodoxapp.ui.map;

import android.app.Activity;

import com.example.orthodoxapp.R;

public class CreateUrl {

    private final int SEARCH_RADIUS = 1000;
    private final String START_WITH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    private Activity activity;

    public CreateUrl(Activity activity) {
        this.activity = activity;
    }

    public String getNearbyChurchesUrl(double lat, double lng) {
        StringBuilder sb = new StringBuilder(START_WITH);
        //add location in request
        sb.append("location=" + lat + "," + lng);
        //add search zone
        sb.append("&radius=" + SEARCH_RADIUS);
        //type
        sb.append("&type=church");
        //key
        sb.append("&key=" + activity.getResources().getString(R.string.google_api_key));

        return sb.toString();
    }

}
