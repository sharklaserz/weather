package sharklaserz.weather;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import nucleus.presenter.PresenterCreator;
import nucleus.view.NucleusActivity;
import nucleus.view.PresenterFinder;
import sharklaserz.weather.loader.ForecastIOAPI;
import sharklaserz.weather.presenter.MainPresenter;
import sharklaserz.weather.services.weatherapi.WeatherWrapper;
import sharklaserz.weather.tools.EventBus;


public class MainActivity extends NucleusActivity<MainPresenter> {

    public TextView txView;
    String forecast = "FORECASTIO"; //Temporary, this should be moved into a file that manages constant strings.
/*
    @Override
    protected PresenterCreator<MainPresenter> getPresenterCreator() {

        return new PresenterCreator<MainPresenter>() {
            @Override
            public MainPresenter createPresenter() {
                return new MainPresenter();
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weather_home);

        //txView = (TextView) findViewById(R.id.putDataHere);

        //basic ui testing
        txView = (TextView) findViewById(R.id.dispTemp);
        EventBus.getInstance().register(this);
    }

    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getActionBar();
        actionBar.show();
        //WeatherWrapper myWrapper = new WeatherWrapper(forecast, getApplicationContext());
        //myWrapper.getCurrentTemperature();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Temporarily setting this to false, as we are not interested in the contextual menu yet.
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

/*
    private static final String PRESENTER_STATE_KEY = "presenter_state";

    private MainPresenter presenter;

    //@Override
    public MainPresenter getPresenter() {
        return presenter;
    }

    protected PresenterCreator<MainPresenter> getPresenterCreator() {
        return new PresenterCreator<MainPresenter>() {
            @Override
            public MainPresenter createPresenter() {
                return new MainPresenter();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_home);
        Bundle savedPresenterState = savedInstanceState == null ? null : savedInstanceState.getBundle(PRESENTER_STATE_KEY);
        PresenterCreator<MainPresenter> creator = getPresenterCreator();
        if (creator != null)
            presenter = (MainPresenter) PresenterFinder.getInstance().findParentPresenter(this).provide(creator, savedPresenterState);

        txView = (TextView) findViewById(R.id.dispTemp);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (presenter != null)
            presenter.takeView(this);
    }

    /**
     * Presenter destruction is here because we want Activity's presenter to live longer than view's presenter
     */

/*
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (presenter != null) {
            presenter.dropView(this);

            if (isFinishing()) {
                presenter.destroy();
                presenter = null;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (presenter != null)
            outState.putBundle(PRESENTER_STATE_KEY, presenter.save());

    }

    public void publishItems(JSONObject resp) {

        txView = (TextView) findViewById(R.id.dispTemp);
        if(resp != null) {
            txView.setText(resp.toString());
        }

    }
*/

    static PresenterCreator<MainPresenter> presenterCreator = new PresenterCreator<MainPresenter>() {
        @Override
        public MainPresenter createPresenter() {
            return new MainPresenter();
        }
    };

    @Override
    protected PresenterCreator<MainPresenter> getPresenterCreator() {
        return presenterCreator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_home);
        txView = (TextView) findViewById(R.id.dispTemp);
    }

    public void publishItems(JSONObject theResponse) {

        TextView myView = (TextView)findViewById(R.id.dispTemp);

        if(theResponse == null) {
            return;
        }

        myView.setText( theResponse.toString() );
    }

}
