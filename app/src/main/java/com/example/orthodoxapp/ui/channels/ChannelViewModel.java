package com.example.orthodoxapp.ui.channels;

import static com.example.orthodoxapp.ui.MainActivity.placesClient;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.example.orthodoxapp.interactors.followPlaces.FollowPlaceInteractor;
import com.example.orthodoxapp.interactors.followPlaces.tasks.GetFollowPlaceDataTask.AsyncResponse;
import com.example.orthodoxapp.ui.createUrl.CreateUrlForFollowChurches;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChannelViewModel extends AndroidViewModel implements AsyncResponse {

  private final String TAG = "ChannelViewModel";

  private MutableLiveData<ArrayList<FindPlace>> data;

  private final DatabaseReference databaseReference = FirebaseHelper.getUserFollowsPath();

  private FollowPlaceInteractor interactor = new FollowPlaceInteractor();

  public ChannelViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<ArrayList<FindPlace>> getData() {
    if (data == null) {
      data = new MutableLiveData<>();
      setDataListener();
    }
    return data;
  }

  private void setDataListener() {
    databaseReference.addValueEventListener(new ValueEventListener() {

      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          Map<String, Boolean> map = (Map) dataSnapshot.getValue();
          Set<String> churchesIds = map.keySet();
          for (String churchId : churchesIds) {
            String url = new CreateUrlForFollowChurches()
                .createUrlForFollowChurches(churchId, getApplication());
            interactor.getFollowPlace(url, ChannelViewModel.this);
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
      }
    });
  }

  @Override
  public void followPlace(FindPlace findPlace) {

    String placeId = findPlace.getId();

// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
    List<Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);

// Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
    FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

    placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
      Place place = response.getPlace();

      if (place.getPhotoMetadatas() != null) {
        // Get the photo metadata.
        PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);

        // Create a FetchPhotoRequest.
        FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
            .setMaxWidth(500) // Optional.
            .setMaxHeight(500) // Optional.
            .build();
        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
          Bitmap bitmap = fetchPhotoResponse.getBitmap();
          findPlace.setPhoto(bitmap);


        }).addOnFailureListener((exception) -> {
          if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            int statusCode = apiException.getStatusCode();
            // Handle error with given status code.
            Log.e(TAG, "Place not found: " + exception.getMessage());
          }
        });
      } else { //if no icon
        findPlace.setPhoto(
            BitmapFactory.decodeResource(getApplication().getResources(), R.raw.church));
      }

      //this is main////////////////////
      ArrayList<FindPlace> nowData = new ArrayList<>();

      if (data.getValue() != null) {
        nowData = data.getValue();
        if (!nowData.contains(findPlace)) {
          nowData.add(findPlace);
          data.setValue(nowData);
        }
      } else {
        nowData.add(findPlace);
        data.setValue(nowData);
      }
      //////////////////////////////////

    });
  }
}