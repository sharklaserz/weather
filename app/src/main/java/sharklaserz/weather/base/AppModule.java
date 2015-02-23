package sharklaserz.weather.base;


import android.util.Log;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit.RestAdapter;
import sharklaserz.weather.MainActivity;
import sharklaserz.weather.loader.ForecastIOAPI;
import sharklaserz.weather.loader.TemperatureLoader;
import sharklaserz.weather.presenter.MainPresenter;


@dagger.Module(injects = {
        MainPresenter.class,
        MainActivity.class,
        TemperatureLoader.class
})
public class AppModule {

    App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    ForecastIOAPI provideServerAPI() {
        return new RestAdapter.Builder()
                .setEndpoint(ForecastIOAPI.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.v("Retrofit", message);
                    }
                })
                .build().create(ForecastIOAPI.class);
    }
}