package sharklaserz.weather.model;

import android.location.Location;

/**
 * Created by Acludia on 2/8/2015.
 */
public class WeatherRequestData
{
    //Class variables
    public double latitude;
    public double longitude;


    public WeatherRequestData()
    {
        latitude = 1337;
        longitude = 1337;
    }

    public WeatherRequestData(Location location){
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

}
