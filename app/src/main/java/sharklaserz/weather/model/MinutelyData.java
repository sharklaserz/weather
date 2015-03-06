package sharklaserz.weather.model;

import java.io.Serializable;

public class MinutelyData implements Serializable {

    public long time;
    public double precipIntensity;
    public double precipProbability;
    public double temperature;
    public double apparentTemperature;
}