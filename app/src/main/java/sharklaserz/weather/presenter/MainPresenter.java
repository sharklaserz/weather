package sharklaserz.weather.presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import nucleus.presenter.Presenter;
import nucleus.presenter.broker.LoaderBroker;
import sharklaserz.weather.MainActivity;
import sharklaserz.weather.R;
import sharklaserz.weather.base.App;
import sharklaserz.weather.loader.LogBroker;
import sharklaserz.weather.loader.WeatherLoader;
import sharklaserz.weather.model.WeatherLocation;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Map;


public class MainPresenter extends Presenter<MainActivity> implements ConnectionCallbacks, OnConnectionFailedListener {

    WeatherLoader weatherLoader = WeatherLoader.getInstance();

    private final String TAG = "Main_Presenter";
    protected GoogleApiClient googleApiClient;

    protected Location locationData;

    @Override
    protected void onCreate(Bundle savedState) {

        addPresenterBroker(new LogBroker());
        addViewBroker(new LogBroker());

        addViewBroker(new LoaderBroker<MainActivity>(weatherLoader) {
            @Override
            protected void onPresent(MainActivity target) {
                target.publishItems(!isLoadingComplete() ? null : getData(weatherLoader));
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

        ArrayList<WeatherLocation> weatherLocations = getWeatherLocationsFromPref();

        locationData = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (locationData != null) {
            double latitude = locationData.getLatitude();
            double longitude = locationData.getLongitude();
            weatherLocations.add(0, new WeatherLocation(latitude, longitude, "Current"));
        } else {
            App.reportError(App.getAppContext().getResources().getString(R.string.no_location_detected));
        }

        weatherLoader.getCurrentWeatherList(weatherLocations);
    }

    private ArrayList<WeatherLocation> getWeatherLocationsFromPref() {

        ArrayList<WeatherLocation> locations = new ArrayList<>();

        Context context = App.getAppContext();
        SharedPreferences sharedPref = App.getAppContext().getSharedPreferences(context.getResources().getString(R.string.weather_location_file), context.MODE_PRIVATE);

        // Fake creating data until permanent solution exists
        // TODO: Add ability to sort list by proper order
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("San Francisco", "37.775,-122.4183");
        editor.putString("Athens", "37.9833,23.7333");
        editor.putString("New York", "40.7127,-74.0059");
        editor.putString("Orlando", "28.5383,-81.3792");
        editor.putString("London", "51.5073,-0.1277");
        editor.commit();

        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String value = entry.getValue().toString();
            String[] parts = value.split(",");
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);

            locations.add(new WeatherLocation(latitude, longitude, entry.getKey().toString()));
        }

        return locations;
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