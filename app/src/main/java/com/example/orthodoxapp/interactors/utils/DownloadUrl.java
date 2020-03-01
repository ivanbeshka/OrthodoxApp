package com.example.orthodoxapp.interactors.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrl {

    private static final String LOG = "DownloadDataFromUrl";

    public String readUrl(String mUrl) throws IOException {

        String data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            //open connection
            URL url = new URL(mUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //read tools
            inputStream = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer(); //with async task

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            Log.e(LOG, "readUrl " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }

        return data;
    }
}
