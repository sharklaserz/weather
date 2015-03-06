package sharklaserz.weather.model;

import java.io.Serializable;

public class Currently extends BasicWeather implements Serializable {

    public int nearestStormDistance;
    public int nearestStormBearing;
    public double temperature;
    public double apparentTemperature;
}