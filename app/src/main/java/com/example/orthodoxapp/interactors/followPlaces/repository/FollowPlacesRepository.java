package com.example.orthodoxapp.interactors.followPlaces.repository;

import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.followPlaces.repository.parser.FollowPlacesParser;
import com.example.orthodoxapp.interactors.utils.DownloadUrl;
import java.io.IOException;
import org.json.JSONException;

public class FollowPlacesRepository {

  private FollowPlacesParser parser = new FollowPlacesParser();
  private DownloadUrl downloadUrl = new DownloadUrl();

  public FindPlace getFollowPlace(String url) throws IOException, JSONException {
    return parser.parseFollowPlace(downloadUrl.readUrl(url));
  }
}
