package com.example.orthodoxapp.interactors;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;


public class GetDataFromUrl extends AsyncTask<String, String, String> {

    private static final String TAG = "GetDataFromUrl";

    private String googlePlacesData;

    private AsyncResponse delegate;

    public GetDataFromUrl(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        DownloadDataFromUrl downloadDataFromUrl = new DownloadDataFromUrl();

        try {
            googlePlacesData = downloadDataFromUrl.readUrl(url);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
            delegate.getData(s);
    }

    public interface AsyncResponse {
        void getData(String s);
    }
}
