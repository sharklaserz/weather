package sharklaserz.weather.model;

import java.io.Serializable;

public class Minutely implements Serializable {

    public String summary;
    public String icon;
    public MinutelyData[] data;
}