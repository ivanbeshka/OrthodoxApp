package com.example.orthodoxapp.interactors.nearbyPlaces;

import com.example.orthodoxapp.interactors.nearbyPlaces.repository.NearbyPlacesRepository;
import com.example.orthodoxapp.interactors.nearbyPlaces.tasks.GetNearbyPlacesDataTask;
import com.example.orthodoxapp.interactors.nearbyPlaces.tasks.GetNearbyPlacesDataTask.AsyncResponse;

public class NearbyPlacesInteractor {

  private NearbyPlacesRepository nearbyPlacesRepository = new NearbyPlacesRepository();

  public void getFindPlaceList(String url, AsyncResponse response) {
    GetNearbyPlacesDataTask getNearbyPlacesDataTask = new GetNearbyPlacesDataTask(response, nearbyPlacesRepository);
    getNearbyPlacesDataTask.execute(url);
  }
}
