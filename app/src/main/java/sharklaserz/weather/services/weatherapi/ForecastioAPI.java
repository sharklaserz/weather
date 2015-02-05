package sharklaserz.weather.services.weatherapi;

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
    private String apiKey = "94c793f57c94d2ef0102edce1351d51d";

    @Override
    public void getCurrentTemperature() {

        // Make HTTP Call
        String URL = baseURL + "/" + apiKey + "/37.8267,-122.423";
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
