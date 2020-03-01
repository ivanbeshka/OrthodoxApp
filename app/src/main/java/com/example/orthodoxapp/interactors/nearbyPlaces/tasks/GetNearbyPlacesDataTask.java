package com.example.orthodoxapp.interactors.nearbyPlaces.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.nearbyPlaces.repository.NearbyPlacesRepository;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;


public class GetNearbyPlacesDataTask extends AsyncTask<String, String, ArrayList<FindPlace>> {

  private static final String TAG = "GetDataFromUrl";

  private ArrayList<FindPlace> places;

  private final AsyncResponse delegate;

  private final NearbyPlacesRepository repository;

  public GetNearbyPlacesDataTask(AsyncResponse delegate, NearbyPlacesRepository repository) {
    this.delegate = delegate;
    this.repository = repository;
  }

  @Override
  protected ArrayList<FindPlace> doInBackground(String... strings) {
    String url = strings[0];

    try {
      places = repository.getFindPlaceList(url);
    } catch (IOException e) {
      Log.e(TAG, e.getMessage());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return places;
  }

  @Override
  protected void onPostExecute(ArrayList<FindPlace> googlePlacesData) {
    delegate.searchingChurches(googlePlacesData);
  }

  public interface AsyncResponse {
    void searchingChurches(ArrayList<FindPlace> findPlaces);
  }
}
