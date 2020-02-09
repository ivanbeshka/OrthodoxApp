package com.example.orthodoxapp.dataModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Parcelable{

    private String senderUid;
    private String senderName;
    private String addresseeUid;
    private String addresseeName;
    private String textMessage;
    @Builder.Default
    private long messageTime = new Date().getTime();

    protected Message(Parcel in) {
        senderUid = in.readString();
        addresseeName = in.readString();
        senderName = in.readString();
        addresseeUid = in.readString();
        textMessage = in.readString();
        messageTime = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(senderUid);
        dest.writeString(addresseeUid);
        dest.writeString(textMessage);
        dest.writeLong(messageTime);
        dest.writeString(senderName);
        dest.writeString(addresseeName);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
