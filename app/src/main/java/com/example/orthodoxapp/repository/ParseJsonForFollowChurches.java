package com.example.orthodoxapp.repository;

import android.content.Context;
import com.example.orthodoxapp.dataModel.FindPlace;
import com.example.orthodoxapp.interactors.GetDataFromUrl;
import com.example.orthodoxapp.repository.createUrl.CreateUrlForFollowChurches;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJsonForFollowChurches implements GetDataFromUrl.AsyncResponse {

    private CallbackFollowChurches delegate;
    private String churchID;
    private Context context;

    public ParseJsonForFollowChurches(CallbackFollowChurches delegate, String churchID, Context context) {
        this.delegate = delegate;
        this.churchID = churchID;
        this.context = context;
    }

    public void parse(){
        CreateUrlForFollowChurches url = new CreateUrlForFollowChurches(churchID, context);
        GetDataFromUrl getDataFromUrl = new GetDataFromUrl(this);
        getDataFromUrl.execute(url.getUrlForFollowChurches());
    }

    @Override
    public void getData(String s) {

        try {
            JSONObject result = new JSONObject(s);
            JSONObject info = result.getJSONObject("result");

            String nameOfChurch = info.getString("name");
            String phoneNumber = info.getString("formatted_phone_number");
            String address = info.getString("formatted_address");
            FindPlace findPlace = FindPlace.builder()
                    .name(nameOfChurch)
                    .id(churchID)
                    .street(address)
                    .phoneNumber(phoneNumber).build();

            delegate.followChurches(findPlace);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface CallbackFollowChurches{
        void followChurches(FindPlace followChurch);
    }
}
