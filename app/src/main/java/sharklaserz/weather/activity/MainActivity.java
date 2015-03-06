package sharklaserz.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;

import nucleus.presenter.PresenterCreator;
import sharklaserz.weather.R;
import sharklaserz.weather.adapters.BasicWeatherAdapter;
import sharklaserz.weather.base.App;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.presenter.MainPresenter;

public class MainActivity extends NucleusActionBarActivity {

    public static View.OnClickListener cardClickListener;
    private static RecyclerView mRecyclerView;
    public ArrayList<ResponseBody> weatherData;

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
        setupRecycler();
        buildGoogleApiClient();
        cardClickListener = new BasicWeatherClickListener(this);
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

    public void publishItems(ArrayList<ResponseBody> responses) {

        weatherData = responses;
        if(responses != null) {
            mRecyclerView.setAdapter(new BasicWeatherAdapter(responses));
        }
    }

    protected synchronized void buildGoogleApiClient() {

        GoogleApiClient googleApiClient;

        if(((MainPresenter)getPresenter()).getGoogleApiClient() == null)
        {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((MainPresenter)getPresenter())
                    .addOnConnectionFailedListener((MainPresenter)getPresenter())
                    .addApi(LocationServices.API)
                    .build();
            ((MainPresenter)getPresenter()).setGoogleApiClient(googleApiClient);
        }
        else
        {
            googleApiClient = ((MainPresenter)getPresenter()).getGoogleApiClient();
        }
    }

    public void setupRecycler(){

        mRecyclerView = (RecyclerView)findViewById(R.id.weather_home_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class BasicWeatherClickListener implements View.OnClickListener{

        private final Context context;

        private BasicWeatherClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View view){

            selectBasicWeatherCard(view);
        }

        private void selectBasicWeatherCard(View view){

            //Based on the card clicked, have the Recyclerview give us data from that card.
            int selectedItemPosition = mRecyclerView.getChildPosition(view);
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView itemCounterView = (TextView) viewHolder.itemView.findViewById(R.id.itemCounter);
            ResponseBody cardData = weatherData.get(selectedItemPosition);

            //Display a Toast with the card information selected.
            String message = "Card number: " + itemCounterView.getText() + " || Temp: " + cardData.currently.temperature;
            Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();

            //Create Intent for Detailed weather activity, and attach the weather data for selected card.
            Intent detailedWeatherIntent = new Intent(App.getAppContext(), DetailedWeatherActivity.class);
            detailedWeatherIntent.putExtra("WeatherData", cardData);
            startActivity(detailedWeatherIntent);
        }
    }
}



















