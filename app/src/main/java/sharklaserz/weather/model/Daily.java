package sharklaserz.weather.model;

import java.io.Serializable;

public class Daily implements Serializable {

    public String summary;
    public String icon;
    public DailyData[] data;
}
