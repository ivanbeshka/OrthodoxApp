package com.example.orthodoxapp.ui.dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.orthodoxapp.dataModel.Message;
import com.example.orthodoxapp.firabaseHelper.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

class DialogViewModel extends ViewModel {

  private MutableLiveData<ArrayList<Message>> data;
  private DatabaseReference reference;

  private DialogViewModel(String churchId) {
    reference = FirebaseHelper.getChurchMsgPath(churchId);
  }

  private void setDataListener() {
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
          ArrayList<Message> msgs = new ArrayList<>();
          for (DataSnapshot msgFromDatabase : dataSnapshot.getChildren()) {
            Message msg = msgFromDatabase.getValue(Message.class);
            msgs.add(msg);
          }
          data.setValue(msgs);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  public LiveData<ArrayList<Message>> getData() {
    if (data == null){
      data = new MutableLiveData<>();
      setDataListener();
    }
    return data;
  }

  void addMsg(Message message){
    reference.push().setValue(message);
  }

  public static class DialogViewModelFactory implements ViewModelProvider.Factory {
    private String churchId;

    DialogViewModelFactory(String churchId) {
      this.churchId = churchId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
      return (T) new DialogViewModel(churchId);
    }
  }
}
