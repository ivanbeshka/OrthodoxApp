package com.example.orthodoxapp.ui.msgs;

import android.app.Application;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.followPlaces.FollowPlaceInteractor;
import com.example.orthodoxapp.interactors.followPlaces.tasks.GetFollowPlaceDataTask.AsyncResponse;
import com.example.orthodoxapp.ui.createUrl.CreateUrlForFollowChurches;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MsgViewModel extends AndroidViewModel implements AsyncResponse {

  private final String TAG = "MsgViewModel";

  private MutableLiveData<ArrayList<FindPlace>> data;

  private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
      .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("follows");

  private FollowPlaceInteractor interactor = new FollowPlaceInteractor();

  public MsgViewModel(@NonNull Application application) {
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

      @RequiresApi(api = VERSION_CODES.N)
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          Map<String, Boolean> map = (Map) dataSnapshot.getValue();
          map.values().removeIf(value -> value.equals(false));
          Set<String> churchesIDs = map.keySet();
          Log.e(TAG, churchesIDs.toString());
          for (String churchId : churchesIDs) {
            String url = new CreateUrlForFollowChurches()
                .createUrlForFollowChurches(churchId, getApplication());
            interactor.getFollowPlace(url, MsgViewModel.this);
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
  }
}