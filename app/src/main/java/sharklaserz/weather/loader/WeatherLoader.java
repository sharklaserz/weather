package sharklaserz.weather.loader;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import nucleus.model.Loader;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import sharklaserz.weather.R;
import sharklaserz.weather.base.App;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.model.WeatherLocation;

public class WeatherLoader extends Loader<ArrayList<ResponseBody>> {

    private static WeatherLoader ourInstance = null;
    protected ForecastIOAPI api;
    public static ArrayList<ResponseBody> responseList = null;
    public int responseListSize = 0;
    private static int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB ~ 10 MB
    HashMap<Integer, String> locationsFromPreference = new HashMap<>();

    public static WeatherLoader getInstance() {

        if (ourInstance == null) {
            responseList = new ArrayList<>();
            ourInstance = new WeatherLoader();
        }

        return ourInstance;
    }

    private WeatherLoader() {

        // Create a cache file for the http client to use

        File cacheDir = new File(App.getAppContext().getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = null;

        try {
            cache = new Cache(cacheDir, CACHE_SIZE);
        } catch (IOException ioe) {
            App.reportError(ioe.toString());
        }

        OkHttpClient okHttpClient = new OkHttpClient();

        if (cache != null) {
            okHttpClient.setCache(cache);
        }

        // Create and configure Retrofit object for ForecastIO, to make API calls from

        api = new RestAdapter.Builder()
                .setEndpoint(ForecastIOAPI.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.v("Retrofit", message);
                    }
                })
                .build().create(ForecastIOAPI.class);
    }

    private void getWeatherLocationsFromPref() {

        Context context = App.getAppContext();
        SharedPreferences sharedPref = App.getAppContext().getSharedPreferences(context.getResources().getString(R.string.weather_location_file), context.MODE_PRIVATE);

        if(locationsFromPreference.size() != 0) {
            locationsFromPreference.clear();
        }

        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            locationsFromPreference.put(Integer.parseInt(entry.getKey()), entry.getValue().toString());
        }
    }

    private void sortResponses() {

        ArrayList<ResponseBody> sortedList = new ArrayList<>();

        // Sort response list based on order from locations preference list
        for (int i = 0; i < locationsFromPreference.size(); i++) {

            String savedLocationValue = locationsFromPreference.get(i);
            String[] parts = savedLocationValue.split(",");
            Double savedLocationLatitude = Double.parseDouble(parts[0]);
            Double savedLocationLongitude = Double.parseDouble(parts[1]);

            for(int j = 0; j < responseList.size(); j++) {
                Double responseLatitude = responseList.get(j).latitude;
                Double responseLongitude = responseList.get(j).longitude;

                if ((savedLocationLatitude.equals(responseLatitude)) && (savedLocationLongitude.equals(responseLongitude))) {
                    sortedList.add(responseList.get(j));
                    responseList.remove(j);
                    break;
                }
            }
        }

        responseList = sortedList;
    }

    // Forecast IO API calls

    public void getCurrentWeatherList(String currentLocation) {

        //if (locationsFromPreference == null) {
            getWeatherLocationsFromPref();
            locationsFromPreference.put(0, currentLocation);
        //} else {
        //    locationsFromPreference.put(0, currentLocation);
        //}

        responseList.clear();
        responseListSize = locationsFromPreference.size();

        for (Map.Entry<Integer, String> entry : locationsFromPreference.entrySet()) {

            String[] parts = entry.getValue().split(",");

            api.getCurrentWeather(Double.parseDouble(parts[0]),Double.parseDouble(parts[1]), new Callback<ResponseBody>() {

                @Override
                public void success(ResponseBody responseBody, Response response) {

                    responseList.add(responseBody);

                    if(responseList.size() == responseListSize) {
                        WeatherLoader weatherLoader = WeatherLoader.getInstance();
                        weatherLoader.sortResponses();
                        Log.d("Size", "---RES Notifty Size = " + responseList.size());
                        notifyReceivers(responseList);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    App.reportError(error.getMessage());
                }
            });
        }
    }
}
