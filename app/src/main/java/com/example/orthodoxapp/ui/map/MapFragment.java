package com.example.orthodoxapp.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.orthodoxapp.R;
import com.example.orthodoxapp.googleApi.GetNearbyPlacesData;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int RC_PERMISSION_LOCATION = 12001;

    private final int SEARCH_RADIUS = 1000;
    private final String START_WITH = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private final int ZOOM = 15;

    private GoogleMap mMap;
    private AutocompleteSupportFragment autocomplete;
    private SupportMapFragment mapFragment;

    //save battery
    private Criteria criteria;

    private static LocationManager locationManager;
    private final Looper looper = null;

    private ImageButton btnSearchLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        initViews(root);

        initLocation();

        btnSearchLocation.setOnClickListener(v -> {

            if (checkPermission()) {
                locationManager.requestSingleUpdate(criteria, locationListener, looper);

            }

        });

        autocomplete.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG));
        autocomplete.setCountry("RU");

        // autocomplete.setTypeFilter(Place.Type.CHURCH);
        autocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                //delete all markers
                mMap.clear();

                getNearbyChurches(String.valueOf(place.getLatLng().latitude),
                        String.valueOf(place.getLatLng().longitude));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), ZOOM));
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


        return root;
    }

    private void initViews(View root) {
        autocomplete = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnSearchLocation = root.findViewById(R.id.btn_search_location);

    }

    private void initLocation() {

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    private void getNearbyChurches(String lat, String lng) {
        StringBuilder sb = new StringBuilder(START_WITH);
        //add location in request
        sb.append("location=" + lat + "," + lng);
        //add search zone
        sb.append("&radius=" + SEARCH_RADIUS);
        //type
        sb.append("&type=church");
        //key
        sb.append("&key=" + getResources().getString(R.string.google_api_key));

        String url = sb.toString();

        Object[] data = new Object[2];

        data[0] = mMap;
        data[1] = url;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(data);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //init my latlng
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.clear();

            getNearbyChurches(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));

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

    @Override
    public boolean onMarkerClick(Marker marker) {
        FragmentFollowChurchDialog dialog = new FragmentFollowChurchDialog();
        dialog.show(getFragmentManager(), "dialog follow show");
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_PERMISSION_LOCATION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted

            } else {
                // permission denied
                Toast.makeText(getActivity().getApplicationContext(), "Please, turn on location", Toast.LENGTH_LONG).show();

                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }

            }
        }
    }

    //Permissions methods
    private boolean checkPermission() {
        int permissionStatusFine = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatusFine != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    RC_PERMISSION_LOCATION);
        }

        return permissionStatusFine == PackageManager.PERMISSION_GRANTED;
    }
}
