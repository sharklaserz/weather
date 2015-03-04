package sharklaserz.weather.loader;


import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import nucleus.model.Loader;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import sharklaserz.weather.base.App;
import sharklaserz.weather.model.ResponseBody;


public class TemperatureLoader extends Loader<ResponseBody> {

    protected ForecastIOAPI api;
    private static int CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB ~ 10 MB

    public TemperatureLoader() {

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

    public void getCurrentTempAt(double latitude, double longitude) {

        api.getCurrentTemperature(latitude, longitude, new Callback<ResponseBody>() {

            @Override
            public void success(ResponseBody responseBody, retrofit.client.Response response) {

                // Call registered loader's onPresent() because data of this type is available
                notifyReceivers(responseBody);
            }

            @Override
            public void failure(RetrofitError error) {
                App.reportError(error.getMessage());
            }
        });
    }
}