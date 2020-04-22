package com.example.orthodoxapp.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.nearbyPlaces.NearbyPlacesInteractor;
import com.example.orthodoxapp.interactors.nearbyPlaces.tasks.GetNearbyPlacesDataTask.AsyncResponse;
import com.example.orthodoxapp.ui.createUrl.UrlForNearbyChurches;
import com.example.orthodoxapp.ui.map.LocationTools.CallbackLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MapFragment extends Fragment implements OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, AsyncResponse, CallbackLocation {

  private static final int RC_PERMISSION_LOCATION = 12001;

  private final int ZOOM = 13;

  private GoogleMap mMap;
  private AutocompleteSupportFragment autocomplete;

  private ArrayList<FindPlace> findPlaces;
  private ArrayList<Marker> markers;

  private LocationTools locationTools;
  private ImageButton btnSearchLocation;

  private SeekBar seekBarRadius;
  private TextView tvSearchRadius;
  private MapViewModel mapViewModel;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mapViewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
    View root = inflater.inflate(R.layout.fragment_map, container, false);

    initViews(root);

    locationTools = new LocationTools(MapFragment.this, getContext());
    locationTools.initLocation();

    seekBarRadius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setSearchRadiusView();
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    btnSearchLocation.setOnClickListener(v -> {

      if (checkPermission()) {
        LocationManager lm = (LocationManager) getContext()
            .getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
          gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
          network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
          // notify user
          new AlertDialog.Builder(getContext())
              .setMessage(R.string.gps_network_not_enabled)
              .setPositiveButton(R.string.open_location_settings, (dialog, which) -> {
                getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
              })
              .setNegativeButton(R.string.cancel, null)
              .show();
        }
        locationTools.requestSingleUpdate();
      }
    });

    autocomplete.setPlaceFields(Arrays
        .asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS,
            Place.Field.LAT_LNG));
    autocomplete.setCountry("RU");

    autocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
      @Override
      public void onPlaceSelected(@NonNull Place place) {
        double lat = place.getLatLng().latitude;
        double lng = place.getLatLng().longitude;

        refreshMap(lat, lng);
      }

      @Override
      public void onError(@NonNull Status status) {

      }
    });

    return root;
  }

  private void initViews(View root) {
    autocomplete = (AutocompleteSupportFragment) getChildFragmentManager()
        .findFragmentById(R.id.autocomplete);

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.map);

    mapFragment.getMapAsync(this);

    btnSearchLocation = root.findViewById(R.id.btn_search_location);
    seekBarRadius = root.findViewById(R.id.seek_bar);
    tvSearchRadius = root.findViewById(R.id.tv_search_radius);

    setSearchRadiusView();
  }

  private void setSearchRadiusView() {
    int searchRadius = (seekBarRadius.getProgress() + 1) * 1000;
    tvSearchRadius.setText("search radius: " + searchRadius + " km");
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    mMap.setOnMarkerClickListener(this);

    //if save markers and find places
    if (mapViewModel.getMarkersData() != null) {
      for (Marker marker : mapViewModel.getMarkersData()) {
        mMap.addMarker(new MarkerOptions().position(marker.getPosition())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
      }
      findPlaces = mapViewModel.getPlacesData();
    }

    //if save map
    MapStateManager mapStateManager = new MapStateManager(getContext());
    CameraPosition position = mapStateManager.getSavedCameraPosition();
    if (position != null) {
      mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
  }

  @Override
  public boolean onMarkerClick(Marker marker) {

    //TODO realisation with other fragment bottom on screen
    for (FindPlace findPlace : findPlaces) {
      if (findPlace.getLat() == marker.getPosition().latitude && findPlace.getLng() == marker
          .getPosition().longitude) {

        //passing place id
        FragmentFollowChurchDialog dialog = new FragmentFollowChurchDialog(findPlace.getId(),
            findPlace.getName());

        dialog.show(getParentFragmentManager(), "dialog follow show");
      }
    }

    return false;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == RC_PERMISSION_LOCATION) {

      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // permission granted

      } else {
        // permission denied
        Toast.makeText(getContext(), "Please, turn on location",
            Toast.LENGTH_LONG).show();

        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);
        }

      }
    }
  }

  //Permissions methods
  private boolean checkPermission() {
    int permissionStatusFine = ContextCompat
        .checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

    if (permissionStatusFine != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
          RC_PERMISSION_LOCATION);
    }

    return permissionStatusFine == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  public void locationChanged(Location location) {
    double lat = location.getLatitude();
    double lng = location.getLongitude();
    refreshMap(lat, lng);
  }

  private void refreshMap(double lat, double lng) {
    mMap.clear();

    int radius = (seekBarRadius.getProgress() + 1) * 1000;

    String url = new UrlForNearbyChurches()
        .create(lat, lng, getContext(), radius);

    NearbyPlacesInteractor interactor = new NearbyPlacesInteractor();
    interactor.getFindPlaceList(url, this);

    mMap.moveCamera(CameraUpdateFactory
        .newLatLngZoom(new LatLng(lat, lng), ZOOM));

  }

  @Override
  public void searchingChurches(ArrayList<FindPlace> findPlaces) {

    markers = new ArrayList<>();
    for (FindPlace place : findPlaces) {
      LatLng latLng = new LatLng(place.getLat(), place.getLng());
      Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
      markers.add(marker);
    }

    this.findPlaces = findPlaces;
  }

  @Override
  public void onPause() {
    super.onPause();
    //save markers
    if (markers != null) {
      mapViewModel.setMarkersData(markers, findPlaces);
    }

    //save map position
    MapStateManager mapStateManager = new MapStateManager(getContext());
    mapStateManager.saveMapState(mMap);
  }
}
