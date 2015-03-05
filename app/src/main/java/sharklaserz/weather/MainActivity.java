package sharklaserz.weather;

import android.content.Context;
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
import sharklaserz.weather.adapters.BasicWeatherAdapter;
import sharklaserz.weather.model.ResponseBody;
import sharklaserz.weather.presenter.MainPresenter;

public class MainActivity extends NucleusActionBarActivity {

    private static ArrayList<ResponseBody> weatherData;
    public static View.OnClickListener cardClickListener;
    private static RecyclerView mRecyclerView;

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

    public void publishItems(ResponseBody response) {

        if(response != null) {

            weatherData = generateFakeData(response);
            mRecyclerView.setAdapter(new BasicWeatherAdapter(weatherData));
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
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public ArrayList<ResponseBody> generateFakeData(ResponseBody data){

        ArrayList<ResponseBody> fakeData = new ArrayList<>();
        for(int index=0; index < 15; index++){
            fakeData.add(data);
        }
        return fakeData;
    }

    private class BasicWeatherClickListener implements View.OnClickListener{

        private final Context context;

        private BasicWeatherClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v){

            selectBasicWeatherCard(v);
        }

        private void selectBasicWeatherCard(View view){
            int selectedItemPosition = mRecyclerView.getChildPosition(view);
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView itemCounterView = (TextView) viewHolder.itemView.findViewById(R.id.itemCounter);
            ResponseBody cardData = weatherData.get(selectedItemPosition);
            String message = "Card number: " + itemCounterView.getText() + " || Temp: " + cardData.currently.temperature;
            Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
        }
    }
}