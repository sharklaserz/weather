package sharklaserz.weather.model;

import java.io.Serializable;

public class BasicWeather implements Serializable {
    public long time;
    public String summary;
    public String icon;
    public double percipIntensity;
    public double percipProbability;
    public double dewPoint;
    public double humidity;
    public double windSpeed;
    public double windBearing;
    public double visibility;
    public double cloudCover;
    public double pressure;
    public double ozone;
}
