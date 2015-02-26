package sharklaserz.weather.loader;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import sharklaserz.weather.model.Currently;
import sharklaserz.weather.model.Daily;
import sharklaserz.weather.model.Hourly;
import sharklaserz.weather.model.Minutely;


public interface ForecastIOAPI {

    public static final String ENDPOINT = "https://api.forecast.io/forecast/1d454a06cbec825829cf04cf8bdcd4ac";

    public static class Response {

        public double latitude;
        public double longitude;
        public String timezone;
        public int offset;
        public Currently currently;
        public Minutely minutely;
        public Hourly hourly;
        public Daily daily;
    }

    @GET("/{latitude},{longitude}")
    void getTemperature(@Path("latitude") double latitude, @Path("longitude") double longitude, Callback<Response> callback);
}