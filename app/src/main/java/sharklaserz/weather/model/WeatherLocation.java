package sharklaserz.weather.model;


public class WeatherLocation {

    public double latitude;
    public double longitude;
    public String locationName;

    public WeatherLocation(double latitude, double longitude, String locationName) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }
}
