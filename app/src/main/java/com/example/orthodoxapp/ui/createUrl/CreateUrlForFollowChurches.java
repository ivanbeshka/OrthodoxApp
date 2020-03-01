package com.example.orthodoxapp.ui.createUrl;

import android.content.Context;
import com.example.orthodoxapp.R;

public class CreateUrlForFollowChurches {

    private final String START_WITH = "https://maps.googleapis.com/maps/api/place/details/json?";

    public CreateUrlForFollowChurches() {
    }

    public String createUrlForFollowChurches(String churchID, Context context){
        StringBuilder sb = new StringBuilder(START_WITH);
        sb.append("place_id=" + churchID);
        sb.append("&key=" + context.getString(R.string.google_api_key));

        return sb.toString();
    }
}
