package sharklaserz.weather.presenter;


import android.os.Bundle;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import nucleus.presenter.broker.LoaderBroker;
import sharklaserz.weather.base.Injector;
import sharklaserz.weather.MainActivity;
import sharklaserz.weather.loader.LogBroker;
import sharklaserz.weather.loader.TemperatureLoader;

public class MainPresenter extends Presenter<MainActivity> {

    @Inject TemperatureLoader temperatureLoader;

    @Override
    protected void onCreate(Bundle savedState) {

        Injector.inject(this);

        addPresenterBroker(new LogBroker());
        addViewBroker(new LogBroker());

        addViewBroker(new LoaderBroker<MainActivity>(temperatureLoader) {
            @Override
            protected void onPresent(MainActivity target) {
                target.publishItems(!isLoadingComplete() ? null : getData(temperatureLoader));
            }
        });

        //Need to replace with get from GPS
        double latitude = 42.956;
        double longitude = -87.654;
        temperatureLoader.request(latitude, longitude);
    }
}