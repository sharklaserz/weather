package sharklaserz.weather.loader;

import javax.inject.Inject;

import sharklaserz.weather.base.App;
import nucleus.model.Loader;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class RetrofitLoader<ResponseT> extends Loader<ResponseT> {

    @Inject protected ForecastIOAPI api;

    protected int request;

    public RetrofitLoader() {
    }

    protected void retroRequest() {
        final int r = ++request;

        doRequest(new Callback<ResponseT>() {
            @Override
            public void success(ResponseT responseT, Response response) {
                if (r == request) // ignore all requests except the last one
                    notifyReceivers(responseT);
            }

            @Override
            public void failure(RetrofitError error) {
                App.reportError(error.getMessage());
            }
        });
    }

    protected abstract void doRequest(Callback<ResponseT> callback);
}