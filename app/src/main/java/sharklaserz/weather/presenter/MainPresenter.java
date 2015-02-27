package sharklaserz.weather.presenter;


import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import nucleus.presenter.broker.LoaderBroker;
import sharklaserz.weather.base.Injector;
import sharklaserz.weather.MainActivity;
import sharklaserz.weather.loader.LogBroker;
import sharklaserz.weather.loader.TemperatureLoader;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;


public class MainPresenter extends Presenter<MainActivity> implements ConnectionCallbacks, OnConnectionFailedListener {

    @Inject TemperatureLoader temperatureLoader;

    private final String TAG = "Main_Presenter";
    protected GoogleApiClient googleApiClient;

    //Location variables
    protected Location locationData;
    protected double latitude;
    protected double longitude;

    @Override
    public MainActivity getView() {
        return super.getView();
    }

    @Override
    protected void onCreate(Bundle savedState) {

        Injector.inject(this);

        addPresenterBroker(new LogBroker());
        addViewBroker(new LogBroker());

        addViewBroker(new LoaderBroker<MainActivity>(temperatureLoader) {
            @Override
            protected void onPresent(MainActivity target) {
                target.publishItems(!isLoadingComplete() ? null : getData(temperatureLoader));
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
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        locationData = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (locationData != null) {
            latitude = locationData.getLatitude();
            longitude = locationData.getLongitude();
            temperatureLoader.request(latitude,longitude);
        } else {
            //TODO: Error handling for location data being null.
            //Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
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