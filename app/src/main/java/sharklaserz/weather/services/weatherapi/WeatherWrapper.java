package sharklaserz.weather.services.weatherapi;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

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
                myAPI = new ForecastioAPI();
                break;
            default:
                myAPI = new ForecastioAPI();
                break;
        }
    }

    public WeatherWrapper(String apiChosen, Context mContext)
    {
        this(null);
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);

    }

    public void getCurrentTemperature() {

        if(locationManager != null)
        {
            myAPI.getCurrentTemperature(locationManager);
        }
    }
}
