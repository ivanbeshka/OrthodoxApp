package com.example.orthodoxapp.ui.createUrl;

import android.content.Context;
import com.example.orthodoxapp.R;

public class UrlForPhoto {

  private final String START_WITH = "https://maps.googleapis.com/maps/api/place/photo?";

  public String create(String photoRef, Context context){
    StringBuilder builder = new StringBuilder(START_WITH);

    builder.append("maxwidth=" + 400);
    builder.append("&maxheight=" + 400);
    builder.append("&photoreference=" + photoRef);
    builder.append("&key=" + context.getString(R.string.google_api_key));

    return builder.toString();
  }
}
