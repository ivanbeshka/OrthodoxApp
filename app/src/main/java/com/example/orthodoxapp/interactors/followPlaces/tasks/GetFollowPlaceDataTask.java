package com.example.orthodoxapp.interactors.followPlaces.tasks;

import android.os.AsyncTask;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.followPlaces.repository.FollowPlacesRepository;
import java.io.IOException;
import org.json.JSONException;

public class GetFollowPlaceDataTask extends AsyncTask<String, String, FindPlace> {

  private FindPlace findPlace;
  private final AsyncResponse delegate;
  private final FollowPlacesRepository repository;

  public GetFollowPlaceDataTask(AsyncResponse delegate, FollowPlacesRepository repository) {
    this.delegate = delegate;
    this.repository = repository;
  }


  @Override
  protected FindPlace doInBackground(String... strings) {
    String url = strings[0];

    try {
      findPlace = repository.getFollowPlace(url);
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }

    return findPlace;
  }

  @Override
  protected void onPostExecute(FindPlace findPlace) {
    delegate.followPlace(findPlace);
  }

  public interface AsyncResponse {
    void followPlace(FindPlace findPlace);
  }
}
