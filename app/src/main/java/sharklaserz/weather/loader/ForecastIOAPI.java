package sharklaserz.weather.loader;

import com.google.gson.annotations.SerializedName;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ForecastIOAPI {

    public static final String ENDPOINT = "https://api.forecast.io/forecast/1d454a06cbec825829cf04cf8bdcd4ac";

    public static class ForecastIOTemperature {
        @SerializedName("temperature")
        public double temperature;

        @Override
        public String toString() {
            return Double.toString(temperature);
        }
    }

    public static class Response {
        @SerializedName("currently")
        public ForecastIOTemperature myCurrently;
    }

    @GET("/{latitude},{longitude}")
    void getTemperature(@Path("latitude") double latitude, @Path("longitude") double longitude, Callback<Response> callback);
}