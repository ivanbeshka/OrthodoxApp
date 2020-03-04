package com.example.orthodoxapp.ui.createUrl;

import android.content.Context;
import com.example.orthodoxapp.R;

public class CreateUrlForNearbyChurches {

    private final int SEARCH_RADIUS = 1000;
    private final String START_WITH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    public String createUrlForNearbyChurches(double lat, double lng, Context context){
        StringBuilder sb = new StringBuilder(START_WITH);
        //add location in request
        sb.append("location=" + lat + "," + lng);
        //add search zone
        sb.append("&radius=" + SEARCH_RADIUS);
        //type
        sb.append("&type=church");
        //key
        sb.append("&key=" + context.getString(R.string.google_api_key));

        return sb.toString();
    }
}