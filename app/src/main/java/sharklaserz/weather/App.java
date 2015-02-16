package sharklaserz.weather;

import android.app.Application;

import android.util.Log;
import android.widget.Toast;
import dagger.ObjectGraph;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Injector.setGraph(ObjectGraph.create(new AppModule(this)));
    }

    public static void reportError(String description) {
        Log.d("ERRORORRORORORORORORORRO", description);
        Toast.makeText(instance, description, Toast.LENGTH_LONG).show();
    }
}