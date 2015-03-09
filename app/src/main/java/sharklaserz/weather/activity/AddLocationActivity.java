package sharklaserz.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import sharklaserz.weather.R;
import sharklaserz.weather.base.App;

public class AddLocationActivity extends NucleusActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_location, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void addLocation(View view) {

        Context context = App.getAppContext();
        SharedPreferences sharedPref = App.getAppContext().getSharedPreferences(context.getResources().getString(R.string.weather_location_file), context.MODE_PRIVATE);

        // Fake creating data until permanent solution exists
        ArrayList<String> locationPool = new ArrayList<>();
        locationPool.add("37.775,-122.4183");
        locationPool.add("37.9833,23.7333");
        locationPool.add("40.7127,-74.0059");
        locationPool.add("28.5383,-81.3792");
        locationPool.add("51.5073,-0.1277");

        Random randomGenerator = new Random();
        int locationChosen = randomGenerator.nextInt(locationPool.size());
        int currentLocNumber = sharedPref.getAll().size();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("" + (currentLocNumber + 1), locationPool.get(locationChosen));
        editor.apply();

        startActivity(new Intent(App.getAppContext(), MainActivity.class));
    }
}
