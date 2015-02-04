package sharklaserz.weather;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URL;

import sharklaserz.weather.services.weatherapi.AsyncResponse;
import sharklaserz.weather.services.weatherapi.httpRequest;


public class MainActivity extends ActionBarActivity implements AsyncResponse {

    TextView txView;
    httpRequest httpInstance = new httpRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txView = (TextView) findViewById(R.id.putDataHere);
        httpInstance.delegate = this;
    }

    protected void onStart() {
        super.onStart();
        URL myUrl = null;
        try {
            myUrl = new URL("https://api.forecast.io/forecast/94c793f57c94d2ef0102edce1351d51d/37.8267,-122.423");
        } catch (Exception e){}
        httpInstance.execute(myUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void processFinish(String output) {
        txView.setText(output);
    }
}
