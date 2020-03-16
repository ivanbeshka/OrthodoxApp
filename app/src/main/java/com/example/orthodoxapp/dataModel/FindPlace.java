package com.example.orthodoxapp.dataModel;

import android.graphics.Bitmap;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class FindPlace {

  private double lat;
  private double lng;
  private String id;
  private String name;
  private String street;
  private String phoneNumber;
  private Bitmap photo;

  @BindingAdapter("bind:imageBitmap")
  public static void loadImage(ImageView iv, Bitmap bitmap) {
    iv.setImageBitmap(bitmap);
  }

  @Override
  public String toString() {
    return "FindPlace{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
