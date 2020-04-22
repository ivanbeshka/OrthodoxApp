package com.example.orthodoxapp.ui.map;

import androidx.lifecycle.ViewModel;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;

public class MapViewModel extends ViewModel {

  private ArrayList<Marker> markersData;
  private ArrayList<FindPlace> placesData;

  void setMarkersData(ArrayList<Marker> markers, ArrayList<FindPlace> places) {
    markersData = markers;
    placesData = places;
  }

  ArrayList<Marker> getMarkersData() {
    return markersData;
  }

  public ArrayList<FindPlace> getPlacesData() {
    return placesData;
  }
}
