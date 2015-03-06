package sharklaserz.weather.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import nucleus.presenter.PresenterCreator;
import sharklaserz.weather.R;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.presenter.DetailedWeatherPresenter;

public class DetailedWeatherActivity extends NucleusActionBarActivity {

    @Override
    protected PresenterCreator<DetailedWeatherPresenter> getPresenterCreator(){

        return new PresenterCreator<DetailedWeatherPresenter>() {
            @Override
            public DetailedWeatherPresenter createPresenter() {
                return new DetailedWeatherPresenter();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);

        //Catch the intent, and pull out the extras stored inside.
        Intent weatherDetailIntent = getIntent();
        ResponseBody responseBody = (ResponseBody)weatherDetailIntent.getSerializableExtra("WeatherData");

        //Find the textviews by id for this activity's current layout.
        TextView cardInfoView = (TextView)this.findViewById(R.id.cardInfo);

        //Set the values for the textview.
        cardInfoView.setText("Temperature: " + responseBody.currently.temperature);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_weather, menu);
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
}
