package com.example.orthodoxapp.ui.channels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.example.orthodoxapp.interactors.followPlaces.FollowPlaceInteractor;
import com.example.orthodoxapp.interactors.followPlaces.tasks.GetFollowPlaceDataTask.AsyncResponse;
import com.example.orthodoxapp.ui.createUrl.UrlForFollowChurches;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ChannelViewModel extends AndroidViewModel implements AsyncResponse {

  private final String TAG = "ChannelViewModel";

  private final static String FIND_PLACES = "find_places";

  private SavedStateHandle data;

  private final DatabaseReference databaseReference = FirebaseHelper.getUserFollowsPath();

  private FollowPlaceInteractor interactor = new FollowPlaceInteractor();

  public ChannelViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
    super(application);
    data = savedStateHandle;
    setDataListener();
  }

  public LiveData<ArrayList<FindPlace>> getData() {
    return data.getLiveData(FIND_PLACES);
  }

  private void setDataListener() {
    databaseReference.addValueEventListener(new ValueEventListener() {

      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          Map<String, Boolean> map = (Map<String, Boolean>) dataSnapshot.getValue();
          Set<String> churchesIds = map.keySet();
          for (String churchId : churchesIds) {
            String url = new UrlForFollowChurches()
                .create(churchId, getApplication());
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
    addFindPlaceToData(findPlace);
//    String placeId = findPlace.getId();
//
//// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
//    List<Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);
//
//// Get a Place object
//    FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);
//
//    placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
//      Place place = response.getPlace();
//
//      if (place.getPhotoMetadatas() != null) {
//        // Get the photo metadata.
//        PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);
//
//        // Create a FetchPhotoRequest.
//        FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
//            .setMaxWidth(100) // Optional.
//            .setMaxHeight(100) // Optional.
//            .build();
//        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
//          Bitmap bitmap = fetchPhotoResponse.getBitmap();
//          findPlace.setPhoto(bitmap);
//
//          addFindPlaceToData(findPlace);
//
//        }).addOnFailureListener((exception) -> {
//          if (exception instanceof ApiException) {
//            ApiException apiException = (ApiException) exception;
//            int statusCode = apiException.getStatusCode();
//            // Handle error with given status code.
//            Log.e(TAG, "Place not found: " + exception.getMessage());
//          }
//        });
//      } else { //if no icon
//        findPlace.setPhoto(BitmapFactory.decodeResource(getApplication().getResources(), R.raw.church));
//
//        addFindPlaceToData(findPlace);
//      }
//    });

  }

  private void addFindPlaceToData(FindPlace findPlace){
    ArrayList<FindPlace> nowData = data.get(FIND_PLACES);
    if (nowData != null) {
      if (!nowData.contains(findPlace)) {
        nowData.add(findPlace);
        data.set(FIND_PLACES, nowData);
      }
    } else {
      ArrayList<FindPlace> places = new ArrayList<>();
      places.add(findPlace);
      data.set(FIND_PLACES, places);
    }
  }
}