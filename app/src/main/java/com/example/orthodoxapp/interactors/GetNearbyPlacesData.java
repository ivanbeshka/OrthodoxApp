package com.example.orthodoxapp.interactors;

import android.os.AsyncTask;
import android.util.Log;

import com.example.orthodoxapp.dataModel.FindPlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class GetNearbyPlacesData extends AsyncTask<String, String, String> {

    private static final String TAG = "GetNearbyPlacesData";

    private String googlePlacesData;

    private AsyncResponse delegate;

    public GetNearbyPlacesData(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

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

            ArrayList<FindPlace> findPlaces = new ArrayList<>();

            for (int i = 0; i < arrayWithPlaces.length(); i++) {

                JSONObject place = arrayWithPlaces.getJSONObject(i);
                JSONObject placeLocation = place.getJSONObject("geometry").getJSONObject("location");

                double lat = Double.valueOf(placeLocation.getString("lat"));
                double lng = Double.valueOf(placeLocation.getString("lng"));

                FindPlace FindPlace = new FindPlace(lat, lng,
                        place.getString("place_id"),
                        place.getString("name"),
                        place.getString("vicinity")
                );

                findPlaces.add(FindPlace);
            }

            delegate.searchingChurches(findPlaces);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public interface AsyncResponse {
        void searchingChurches(ArrayList<FindPlace> findPlaces);
    }
}
