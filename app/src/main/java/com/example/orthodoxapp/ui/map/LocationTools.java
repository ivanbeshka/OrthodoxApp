package com.example.orthodoxapp.ui.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

public class LocationTools {

  //save battery
  private Criteria criteria;

  private static LocationManager locationManager;
  private final Looper looper = null;

  private Context context;

  CallbackLocation delegate;

  public LocationTools(CallbackLocation delegate, Context context) {
    this.delegate = delegate;
    this.context = context;
  }

  void initLocation() {

    criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
    criteria.setPowerRequirement(Criteria.POWER_LOW);
    criteria.setAltitudeRequired(false);
    criteria.setBearingRequired(false);
    criteria.setSpeedRequired(false);
    criteria.setCostAllowed(true);
    criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
    criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  }

  private final LocationListener locationListener = new LocationListener() {

    @Override
    public void onLocationChanged(Location location) {
      delegate.locationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };

  public interface CallbackLocation {

    void locationChanged(Location location);
  }


  //only after check permission
  @SuppressLint("MissingPermission")
  void requestSingleUpdate() {
    locationManager.requestSingleUpdate(criteria, locationListener, looper);
  }

}
