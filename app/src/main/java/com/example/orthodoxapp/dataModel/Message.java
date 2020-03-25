package com.example.orthodoxapp.dataModel;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Parcelable{

    private String placeUid;
    private String textMessage;
    private String messageDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.placeUid);
        dest.writeString(this.textMessage);
        dest.writeString(this.messageDate);
    }

    protected Message(Parcel in) {
        this.placeUid = in.readString();
        this.textMessage = in.readString();
        this.messageDate = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
