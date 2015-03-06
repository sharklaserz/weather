package sharklaserz.weather.model;

import java.io.Serializable;

public class DailyData extends BasicWeather implements Serializable {

    public long sunriseTime;
    public long sunsetTime;
    public double moonPhase;
    public double percipIntensityMax;
    public double temperatureMin;
    public long temperatureMinTime;
    public double temperatureMax;
    public long temperatureMaxTime;
    public double apparentTemperatureMin;
    public long apparentTemperatureMinTime;
    public double apparentTemperatureMax;
    public long apparentTemperatureMaxTime;
}
