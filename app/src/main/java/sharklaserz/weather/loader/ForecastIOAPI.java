package sharklaserz.weather.loader;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import sharklaserz.weather.model.ResponseBody;


public interface ForecastIOAPI {

    public static final String API_KEY = "1d454a06cbec825829cf04cf8bdcd4ac";
    public static final String ENDPOINT = "https://api.forecast.io/forecast/" + API_KEY;


    @GET("/{latitude},{longitude}")
    void getCurrentTemperature(@Path("latitude") double latitude, @Path("longitude") double longitude, Callback<ResponseBody> callback);
}