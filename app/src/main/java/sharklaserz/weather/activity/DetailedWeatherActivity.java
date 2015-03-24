package sharklaserz.weather.activity;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nucleus.presenter.PresenterCreator;
import sharklaserz.weather.R;
import sharklaserz.weather.adapters.ExpandableRecyclerAdapter;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.presenter.DetailedWeatherPresenter;

public class DetailedWeatherActivity extends NucleusActionBarActivity {

    ExpandableRecyclerAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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

        //setup expandable recycler view
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableRecyclerView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableRecyclerAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //set height of banner
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = (width * 2 / 3); //3:2 aspect ratio

        RelativeLayout bannerLayout = (RelativeLayout)this.findViewById(R.id.bannerLayout);
        bannerLayout.getLayoutParams().width = width;
        bannerLayout.getLayoutParams().height = height;
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

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding future categories
        listDataHeader.add("Minute by Minute Forecast");
        listDataHeader.add("Hourly Forecast");
        listDataHeader.add("14 Day Forecast");

        // Adding child data
        List<String> minuteChild = new ArrayList<String>();
        minuteChild.add("minute data");

        List<String> hourlyChild = new ArrayList<String>();
        hourlyChild.add("Hour data");

        List<String> dailyChild = new ArrayList<String>();
        dailyChild.add("daily data");

        listDataChild.put(listDataHeader.get(0), minuteChild); // Header, Child data
        listDataChild.put(listDataHeader.get(1), hourlyChild);
        listDataChild.put(listDataHeader.get(2), dailyChild);
    }
}
