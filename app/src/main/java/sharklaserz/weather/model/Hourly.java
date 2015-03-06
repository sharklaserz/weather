package sharklaserz.weather.model;

import java.io.Serializable;

public class Hourly implements Serializable {

    public String summary;
    public String icon;
    public BasicWeather[] data;
}
