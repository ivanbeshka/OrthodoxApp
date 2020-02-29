package com.example.orthodoxapp.ui.msgs;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.repository.ParseJsonForFollowChurches;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MsgViewModel extends ViewModel implements ParseJsonForFollowChurches.CallbackFollowChurches {

    private MutableLiveData<ArrayList<FindPlace>> data;

    DatabaseReference databaseReference;

    public MsgViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("follows");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Boolean> map = (Map) dataSnapshot.getValue();
                    map.values().removeIf(value -> value.equals(false));
                    Set<String> churchesIDs = map.keySet();
                    for (String churchId : churchesIDs) {
                       // ParseJsonForFollowChurches parser = new ParseJsonForFollowChurches(MsgViewModel.this, churchId, );
                      //  parser.parse();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public LiveData<ArrayList<FindPlace>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    @Override
    public void followChurches(FindPlace followChurch) {
        ArrayList<FindPlace> nowData = data.getValue();
        assert nowData != null;
        if(nowData.contains(followChurch)){
            nowData.add(followChurch);
            data.setValue(nowData);
        }
    }
}