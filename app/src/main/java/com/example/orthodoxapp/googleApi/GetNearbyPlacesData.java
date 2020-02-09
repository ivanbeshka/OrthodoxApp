package com.example.orthodoxapp.googleApi;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private static final String TAG = "GetNearbyPlacesData";

    private String googlePlacesData;
    private GoogleMap googleMap;
    private String url;

    @Override
    protected String doInBackground(Object... objects) {
        googleMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject resultObject = new JSONObject(s);
            JSONArray arrayWithPlaces = resultObject.getJSONArray("results");

            for (int i = 0; i < arrayWithPlaces.length(); i++) {

                JSONObject place = arrayWithPlaces.getJSONObject(i);
                JSONObject placeLocation = place.getJSONObject("geometry").getJSONObject("location");

                String lat = placeLocation.getString("lat");
                String lng = placeLocation.getString("lng");

                String placeName = place.getString("name");


                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(placeName)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
