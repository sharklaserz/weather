package sharklaserz.weather.services.weatherapi;

/**
 * Created by toddish on 2/4/2015.
 */
public class WeatherWrapper {

    private String apiToUse = "FORECASTIO";
    private WeatherAPI myAPI = null;

    public WeatherWrapper() {

        this(null);
    }

    public WeatherWrapper(String apiChosen) {

        if(apiChosen != null) {
            this.apiToUse = apiChosen;
        }

        switch (apiToUse) {
            case "FORECASTIO":
                myAPI = new ForecastioAPI();
                break;
            default:
                myAPI = new ForecastioAPI();
                break;
        }
    }

    public void getCurrentTemperature() {

        myAPI.getCurrentTemperature();
    }
}
