package sharklaserz.weather.model;


public class ResponseBody {

    public double latitude;
    public double longitude;
    public String timezone;
    public int offset;
    public Currently currently;
    public Minutely minutely;
    public Hourly hourly;
    public Daily daily;
}
