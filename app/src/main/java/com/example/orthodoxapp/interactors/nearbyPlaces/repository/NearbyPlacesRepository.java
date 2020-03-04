package com.example.orthodoxapp.interactors.nearbyPlaces.repository;

import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.nearbyPlaces.repository.parser.NearbyPlacesParser;
import com.example.orthodoxapp.interactors.utils.DownloadUrl;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;

public class NearbyPlacesRepository {

  private NearbyPlacesParser parser = new NearbyPlacesParser();
  private DownloadUrl downloadUrl = new DownloadUrl();

  public ArrayList<FindPlace> getFindPlaceList(String url) throws IOException, JSONException {
    return parser.parseFindPlaceList(downloadUrl.readUrl(url));
  }
}
