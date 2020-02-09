package com.example.orthodoxapp.dataModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Parcelable {

    @Builder.Default
    private int likes = 0;
    @Builder.Default
    private int reposts = 0;
    @Builder.Default
    private int views = 0;
    @Builder.Default
    private int comments = 0;
    @Builder.Default
    private long postTime = new Date().getTime();
    private String text;
    private String image;
    private String userUid;
    private String userName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.likes);
        dest.writeInt(this.reposts);
        dest.writeInt(this.views);
        dest.writeInt(this.comments);
        dest.writeLong(this.postTime);
        dest.writeString(this.text);
        dest.writeString(this.image);
        dest.writeString(this.userUid);
    }

    protected Post(Parcel in) {
        this.likes = in.readInt();
        this.reposts = in.readInt();
        this.views = in.readInt();
        this.comments = in.readInt();
        this.postTime = in.readLong();
        this.text = in.readString();
        this.image = in.readString();
        this.userUid = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
