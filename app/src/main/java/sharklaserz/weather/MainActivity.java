package sharklaserz.weather;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import sharklaserz.weather.services.weatherapi.WeatherWrapper;
import sharklaserz.weather.tools.EventBus;


public class MainActivity extends ActionBarActivity {

    private TextView txView;
    String forecast = "FORECASTIO"; //Temporary, this should be moved into a file that manages constant strings.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txView = (TextView) findViewById(R.id.putDataHere);
        EventBus.getInstance().register(this);
    }

    protected void onStart() {
        super.onStart();

        WeatherWrapper myWrapper = new WeatherWrapper(forecast, getApplicationContext());
        myWrapper.getCurrentTemperature();
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

    @Subscribe
    public void handleMyEvent(String response) {
        txView.setText("Bye Felicia: " + response);
    }

}
