package com.example.orthodoxapp.dataModel;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orthodoxapp.R;
import com.example.orthodoxapp.ui.createUrl.UrlForPhoto;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FindPlace implements Parcelable {

  private double lat;
  private double lng;
  private String id;
  private String name;
  private String street;
  private String phoneNumber;
  private String photoRef;


  @SuppressLint("ResourceType")
  @BindingAdapter("bind:image")
  public static void loadImage(ImageView iv, String photoRef) {

    if (photoRef != null && !photoRef.equals("")) {
      UrlForPhoto urlForPhoto = new UrlForPhoto();
      String url = urlForPhoto.create(photoRef, iv.getContext());
      Glide.with(iv.getContext())
          .load(url).apply(new RequestOptions().circleCrop()).into(iv);
    }else {
      iv.setImageResource(R.raw.church);
      Log.d("findplace", "bitmapSetting");
    }

  }

  @Override
  public String toString() {
    return "FindPlace{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FindPlace findPlace = (FindPlace) o;
    return id.equals(findPlace.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(this.lat);
    dest.writeDouble(this.lng);
    dest.writeString(this.id);
    dest.writeString(this.name);
    dest.writeString(this.street);
    dest.writeString(this.phoneNumber);
    dest.writeString(this.photoRef);
  }

  protected FindPlace(Parcel in) {
    this.lat = in.readDouble();
    this.lng = in.readDouble();
    this.id = in.readString();
    this.name = in.readString();
    this.street = in.readString();
    this.phoneNumber = in.readString();
    this.photoRef = in.readString();
  }

  public static final Creator<FindPlace> CREATOR = new Creator<FindPlace>() {
    @Override
    public FindPlace createFromParcel(Parcel source) {
      return new FindPlace(source);
    }

    @Override
    public FindPlace[] newArray(int size) {
      return new FindPlace[size];
    }
  };
}
