package sharklaserz.weather.loader;

import retrofit.Callback;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TemperatureLoader extends RetrofitLoader<ForecastIOAPI.Response> {

    private double latitude;
    private double longitude;

    @Inject
    public TemperatureLoader() {
    }

    public void request(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        retroRequest();
    }

    @Override
    protected void doRequest(Callback<ForecastIOAPI.Response> callback) {
        api.getTemperature(latitude, longitude, callback);
    }
}