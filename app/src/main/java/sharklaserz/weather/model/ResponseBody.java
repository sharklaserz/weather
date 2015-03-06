package sharklaserz.weather.model;

import java.io.Serializable;

public class ResponseBody implements Serializable {

    public double latitude;
    public double longitude;
    public String timezone;
    public int offset;
    public Currently currently;
    public Minutely minutely;
    public Hourly hourly;
    public Daily daily;
}
