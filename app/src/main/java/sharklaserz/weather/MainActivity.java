package sharklaserz.weather;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import nucleus.presenter.Presenter;
import nucleus.presenter.PresenterCreator;
import sharklaserz.weather.loader.ForecastIOAPI;
import sharklaserz.weather.presenter.MainPresenter;


public class MainActivity extends NucleusActionBarActivity {

    private TextView txView;
    protected GoogleApiClient googleApiClient;

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

        buildGoogleApiClient();
        ((MainPresenter)getPresenter()).setGoogleApiClient(googleApiClient);
        txView = (TextView) findViewById(R.id.dispTemp);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        MainPresenter currentPresenter = (MainPresenter)getPresenter();
        currentPresenter.onStart();

    }

    @Override
    public void onStop()
    {
        super.onStop();
        MainPresenter currentPresenter = (MainPresenter)getPresenter();
        currentPresenter.onStop();
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

    public void publishItems(ForecastIOAPI.Response response) {

        if(response != null) {

            txView.setText("" + response.currently.temperature);
        }
    }

    protected synchronized void buildGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((MainPresenter)getPresenter())
                .addOnConnectionFailedListener((MainPresenter)getPresenter())
                .addApi(LocationServices.API)
                .build();
    }


}