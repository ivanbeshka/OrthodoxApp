package com.example.orthodoxapp.ui.map;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

class MapStateManager {

  //TODO SAVE MARKERS

  private static final String LONGITUDE = "longitude";
  private static final String LATITUDE = "latitude";
  private static final String ZOOM = "zoom";
  private static final String BEARING = "bearing";
  private static final String TILT = "tilt";

  private static final String PREFS_NAME ="mapCameraState";

  private SharedPreferences mapStatePrefs;

  MapStateManager(Context context) {
    mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }

  void saveMapState(GoogleMap mapMie) {
    SharedPreferences.Editor editor = mapStatePrefs.edit();
    CameraPosition position = mapMie.getCameraPosition();

    editor.putFloat(LATITUDE, (float) position.target.latitude);
    editor.putFloat(LONGITUDE, (float) position.target.longitude);
    editor.putFloat(ZOOM, position.zoom);
    editor.putFloat(TILT, position.tilt);
    editor.putFloat(BEARING, position.bearing);
    editor.apply();
  }

  CameraPosition getSavedCameraPosition() {
    double latitude = mapStatePrefs.getFloat(LATITUDE, 0);
    if (latitude == 0) {
      return null;
    }
    double longitude = mapStatePrefs.getFloat(LONGITUDE, 0);
    LatLng target = new LatLng(latitude, longitude);

    float zoom = mapStatePrefs.getFloat(ZOOM, 0);
    float bearing = mapStatePrefs.getFloat(BEARING, 0);
    float tilt = mapStatePrefs.getFloat(TILT, 0);

    return new CameraPosition(target, zoom, tilt, bearing);
  }
}
