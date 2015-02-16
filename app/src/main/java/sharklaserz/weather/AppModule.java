package sharklaserz.weather;


import android.util.Log;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit.RestAdapter;
import sharklaserz.weather.loader.ItemsLoader;
import sharklaserz.weather.loader.ServerAPI;
import sharklaserz.weather.presenter.MainPresenter;


@dagger.Module(injects = {
        MainPresenter.class,
        MainActivity.class,
        ItemsLoader.class
})
public class AppModule {

    App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    ServerAPI provideServerAPI() {
        return new RestAdapter.Builder()
                .setEndpoint(ServerAPI.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.v("Retrofit", message);
                    }
                })
                .build().create(ServerAPI.class);
    }
}