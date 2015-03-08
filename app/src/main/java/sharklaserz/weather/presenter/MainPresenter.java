package sharklaserz.weather.presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import nucleus.presenter.Presenter;
import nucleus.presenter.broker.LoaderBroker;
import sharklaserz.weather.activity.MainActivity;
import sharklaserz.weather.R;
import sharklaserz.weather.base.App;
import sharklaserz.weather.loader.LogBroker;
import sharklaserz.weather.loader.WeatherLoader;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.model.WeatherLocation;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class MainPresenter extends Presenter<MainActivity> implements ConnectionCallbacks, OnConnectionFailedListener {

    WeatherLoader weatherLoader = WeatherLoader.getInstance();

    private final String TAG = "Main_Presenter";
    ArrayList<WeatherLocation> locationsFromPreference = new ArrayList<>();
    protected GoogleApiClient googleApiClient;

    protected Location locationData;

    @Override
    protected void onCreate(Bundle savedState) {

        addPresenterBroker(new LogBroker());
        addViewBroker(new LogBroker());

        addViewBroker(new LoaderBroker<MainActivity>(weatherLoader) {
            @Override
            protected void onPresent(MainActivity target) {
                ArrayList<ResponseBody> sortedResponses = sortResponses(getData(weatherLoader));
                target.publishItems(!isLoadingComplete() ? null : sortedResponses);
            }
        });
    }

    public void onStart()
    {
        googleApiClient.connect();
    }

    public void onStop()
    {
        if(googleApiClient.isConnected())
        {
            googleApiClient.disconnect();
        }
    }

    public void setGoogleApiClient(GoogleApiClient clientReference)
    {
        if(googleApiClient == null)
        {
            googleApiClient = clientReference;
        }
    }

    public GoogleApiClient getGoogleApiClient()
    {
        return googleApiClient;
    }

    //Google Location API Call back functions
    @Override
    public void onConnected(Bundle connectionHint) {

        getWeatherData();
    }

    public void getWeatherData() {

        locationsFromPreference.clear();
        getWeatherLocationsFromPref();
        locationData = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (locationData != null) {
            double latitude = locationData.getLatitude();
            double longitude = locationData.getLongitude();
            locationsFromPreference.add(new WeatherLocation(latitude, longitude, 0));
        } else {
            App.reportError(App.getAppContext().getResources().getString(R.string.no_location_detected));
        }

        weatherLoader.getCurrentWeatherList(locationsFromPreference);
    }

    private void getWeatherLocationsFromPref() {

        Context context = App.getAppContext();
        SharedPreferences sharedPref = App.getAppContext().getSharedPreferences(context.getResources().getString(R.string.weather_location_file), context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String value = entry.getValue().toString();
            String[] parts = value.split(",");
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);

            locationsFromPreference.add(new WeatherLocation(latitude, longitude, Integer.parseInt(entry.getKey())) );
        }
    }

    private ArrayList<ResponseBody> sortResponses(ArrayList<ResponseBody> responses) {

        ArrayList<ResponseBody> sortedList = new ArrayList<>();

        // Sort locations list from preference file
        Collections.sort(locationsFromPreference, new Comparator<WeatherLocation>() {
            @Override
            public int compare(WeatherLocation loc1, WeatherLocation loc2) {
                return loc1.order - loc2.order;
            }
        });

        // Sort response list based on order from locations preference list
        for (int i = 0; i < locationsFromPreference.size(); i++) {
            WeatherLocation savedLocation = locationsFromPreference.get(i);

            for(int j = 0; j < responses.size(); j++) {
                Double responseLatitude = responses.get(j).latitude;
                Double responseLongitude = responses.get(j).longitude;

                if ((savedLocation.latitude == responseLatitude) && (savedLocation.longitude == responseLongitude)) {
                    sortedList.add(savedLocation.order, responses.get(j));
                    responses.remove(j);
                    break;
                }
            }
        }

        return sortedList;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        //TODO: Create custom error handling.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        //TODO: Add logic to determine if we should attempt to restablish connection.
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }
}