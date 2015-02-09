package sharklaserz.weather.services.weatherapi;

import android.location.LocationManager;

import sharklaserz.weather.model.WeatherRequestData;

/**
 * Created by toddish on 2/4/2015.
 */
public interface WeatherAPI {

    void getCurrentTemperature(WeatherRequestData requestData);
}
