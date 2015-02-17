package sharklaserz.weather.services.weatherapi;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import sharklaserz.weather.model.WeatherRequestData;

/**
 * Created by toddish on 2/4/2015.
 */
public class WeatherWrapper {

    private String apiToUse = "FORECASTIO";
    private WeatherAPI myAPI = null;
    private LocationManager locationManager = null;

    public WeatherWrapper() {

        this(null);
    }

    public WeatherWrapper(String apiChosen) {

        if(apiChosen != null) {
            this.apiToUse = apiChosen;
        }

        switch (apiToUse) {
            case "FORECASTIO":
                break;
            default:
                break;
        }
    }

    public WeatherWrapper(String apiChosen, Context mContext){

        this(null);
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);

    }

    private void setWeatherLocationData(WeatherRequestData requestData){

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //TODO: Insert toast message here...and better solution later
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        requestData.latitude = lastKnownLocation.getLatitude();
        requestData.longitude = lastKnownLocation.getLongitude();
    }

    public boolean isValidLatitudeLongitude(double latitude, double longitude){
        final double MAXIMUM_LATITUDE = 90.0;
        final double MINIMUM_LATITUDE = -90.0;
        final double MAXIMUM_LONGITUDE = 180.0;
        final double MINIMUM_LONGITUDE = -180.0;
        if((latitude <= MAXIMUM_LATITUDE && latitude >= MINIMUM_LATITUDE) && (longitude <= MAXIMUM_LONGITUDE && longitude >= MINIMUM_LONGITUDE)){
            return true;
        }
        return false;
    }

    //Weather request functions
    //Get Current Temperature requires Latitude, Longitude
    public void getCurrentTemperature() {

        WeatherRequestData newRequest = new WeatherRequestData();
        setWeatherLocationData(newRequest);
        if(isValidLatitudeLongitude(newRequest.latitude, newRequest.longitude))
        {
            myAPI.getCurrentTemperature(newRequest);
        }
    }

}
