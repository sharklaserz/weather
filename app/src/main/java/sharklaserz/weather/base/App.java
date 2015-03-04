package sharklaserz.weather.base;

import android.app.Application;

import android.content.Context;
import android.widget.Toast;

public class App extends Application {

    private static App instance;
    private static Context context;

    public void onCreate() {
        super.onCreate();
        instance = this;
        App.context = getApplicationContext();
    }

    public static void reportError(String description) {
        Toast.makeText(instance, description, Toast.LENGTH_LONG).show();
    }

    public static Context getAppContext() {
        return App.context;
    }
}