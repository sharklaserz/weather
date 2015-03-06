package sharklaserz.weather.loader;


import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nucleus.model.Loader;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import sharklaserz.weather.base.App;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.model.WeatherLocation;

public class WeatherLoader extends Loader<ArrayList<ResponseBody>> {

    private static WeatherLoader ourInstance = null;
    protected ForecastIOAPI api;
    public static ArrayList<ResponseBody> responseList = null;
    public int responseListSize = 0;
    private static int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB ~ 10 MB

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

    // Forecast IO API calls

    public void getCurrentWeatherList(ArrayList<WeatherLocation> locations) {

        responseList.clear();
        responseListSize = locations.size();

        for (int i = 0; i < locations.size(); i++) {
            WeatherLocation theLocation = locations.get(i);
            api.getCurrentWeather(theLocation.latitude, theLocation.longitude, new Callback<ResponseBody>() {

                @Override
                public void success(ResponseBody responseBody, Response response) {

                    WeatherLoader theLoader = WeatherLoader.getInstance();
                    theLoader.responseList.add(responseBody);

                    if(theLoader.responseList.size() == theLoader.responseListSize) {
                        notifyReceivers(theLoader.responseList);
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
