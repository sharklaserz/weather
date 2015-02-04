package sharklaserz.weather.services.weatherapi;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sharklaserz.weather.R;

/**
 * Created by todds_000 on 2/3/2015.
 */
public class httpRequest extends AsyncTask<URL, Void, String> {

    public AsyncResponse delegate = null;
    //public URL myUrl;
    String mUserAgent = "";

    protected String doInBackground(URL... url) {

        HttpURLConnection con = null;
        String responseString = "";
        try {
            con = (HttpURLConnection) url[0].openConnection();
            if (mUserAgent != null) {
                con.setRequestProperty("User-Agent", mUserAgent);
            }
            con.setDoOutput(false);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    responseString = line;
                }
            } catch (IOException e) {
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        reader = null;
                    } catch (IOException e) {
                    }
                }
            }
        } catch (IOException e1) {
        }

        //Log.d("TODDISH", "HELLO WORLD");
        return responseString;
    }

    protected void onPostExecute(String response) {
        delegate.processFinish(response);
        Log.d("TODDISH", response);
    }
}

