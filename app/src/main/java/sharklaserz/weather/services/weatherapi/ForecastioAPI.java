package sharklaserz.weather.services.weatherapi;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import sharklaserz.weather.tools.EventBus;

/**
 * Created by toddish on 2/4/2015.
 */
public class ForecastioAPI implements WeatherAPI {

    private String baseURL = "https://api.forecast.io/forecast";
    private String apiKey = "1d454a06cbec825829cf04cf8bdcd4ac";
    //private String apiKey = "94c793f57c94d2ef0102edce1351d51d"; //Todd's API key.
    Location lastKnownLocation = null;



    @Override
    public void getCurrentTemperature(LocationManager locationManager) {

        // Get Last known location from Location Manager
        lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        double latitude = lastKnownLocation.getLatitude();
        double longitude = lastKnownLocation.getLongitude();


        // Make HTTP Call
        //String URL = baseURL + "/" + apiKey + "/37.8267,-122.423";
        String URL = baseURL + "/" + apiKey + "/" + latitude + "," + longitude;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                processResponse(response);
            }

            public void onFailure(int statusCode, Header[] headers, JSONObject errorResponse, Throwable e) {
                Log.d("ASYNC FAILURE", errorResponse.toString());
            }
        });
    }

    private void processResponse(JSONObject response) {
        String foo = "";

        try {
            foo = response.getJSONObject("currently").getString("temperature");
        } catch (Exception e) {}

        EventBus.getInstance().post(foo);
    }

}
