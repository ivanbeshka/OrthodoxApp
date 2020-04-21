package com.example.orthodoxapp.ui.createUrl;

import android.content.Context;
import android.util.Log;
import com.example.orthodoxapp.R;

public class UrlForFollowChurches {

    private final String START_WITH = "https://maps.googleapis.com/maps/api/place/details/json?";

    public String create(String churchID, Context context){
        StringBuilder sb = new StringBuilder(START_WITH);
        sb.append("place_id=" + churchID);
        sb.append("&key=" + context.getString(R.string.google_api_key));
        Log.d("urlFollow", sb.toString());
        return sb.toString();
    }
}
