package sharklaserz.weather;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import nucleus.presenter.PresenterCreator;
import sharklaserz.weather.loader.ForecastIOAPI;
import sharklaserz.weather.presenter.MainPresenter;


public class MainActivity extends NucleusActionBarActivity {

    private TextView txView;

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

        txView = (TextView) findViewById(R.id.dispTemp);
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

    public void publishItems(ForecastIOAPI.ForecastIOTemperature temperature) {

        if(temperature != null) {
            txView.setText(temperature.toString());
        }
    }
}