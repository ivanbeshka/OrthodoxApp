package com.example.orthodoxapp.interactors.followPlaces;

import com.example.orthodoxapp.interactors.followPlaces.repository.FollowPlacesRepository;
import com.example.orthodoxapp.interactors.followPlaces.tasks.GetFollowPlaceDataTask;
import com.example.orthodoxapp.interactors.followPlaces.tasks.GetFollowPlaceDataTask.AsyncResponse;

public class FollowPlaceInteractor {

  private FollowPlacesRepository repository = new FollowPlacesRepository();

  public void getFollowPlace(String url, AsyncResponse asyncResponse){
    GetFollowPlaceDataTask task = new GetFollowPlaceDataTask(asyncResponse, repository);
    task.execute(url);
  }
}
