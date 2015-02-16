package sharklaserz.weather;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import nucleus.presenter.PresenterCreator;
import nucleus.view.NucleusActivity;
import sharklaserz.weather.loader.ServerAPI;
import sharklaserz.weather.presenter.MainPresenter;


public class MainActivity extends NucleusActivity<MainPresenter> {

    private TextView txView;
    String forecast = "FORECASTIO"; //Temporary, this should be moved into a file that manages constant strings.

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
        //EventBus.getInstance().register(this);
    }

    protected void onStart() {
        super.onStart();

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

    public void publishItems(ServerAPI.MyTemp items, String user) {

        /*
        ListView listView = (ListView)findViewById(R.id.listView);

        if (items == null) {
            listView.setVisibility(View.INVISIBLE);
            return;
        }

        ViewFn.fadeIn(listView);

        check1.setChecked(user.equals(MainPresenter.NAME_1));
        check2.setChecked(user.equals(MainPresenter.NAME_2));

        listView.setAdapter(new ArrayAdapter<ServerAPI.Item>(this, R.layout.item, items));
        */

        if(items != null) {
            txView.setText(items.toString());
        } else {
            Log.d("WHHHHHHHH", "WAS NULL");
        }
    }

}