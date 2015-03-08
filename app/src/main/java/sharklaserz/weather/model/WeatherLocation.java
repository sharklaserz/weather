package sharklaserz.weather.model;

import java.io.Serializable;

public class WeatherLocation implements Serializable {

    public double latitude;
    public double longitude;
    public int order;

    public WeatherLocation(double latitude, double longitude, int order) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.order = order;
    }
}
