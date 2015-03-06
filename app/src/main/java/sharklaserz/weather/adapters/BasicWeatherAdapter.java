package sharklaserz.weather.adapters;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sharklaserz.weather.activity.MainActivity;
import sharklaserz.weather.R;
import sharklaserz.weather.model.ResponseBody;


public class BasicWeatherAdapter extends RecyclerView.Adapter<BasicWeatherAdapter.BasicWeatherViewHolder> {

    private ArrayList<ResponseBody> weatherData;

    public BasicWeatherAdapter(ArrayList<ResponseBody> weatherData){

        this.weatherData = weatherData;
    }

    @Override
    public BasicWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_weather_card_view, parent, false);
        view.setOnClickListener(MainActivity.cardClickListener);
        BasicWeatherViewHolder myViewHolder = new BasicWeatherViewHolder(view);
        return myViewHolder;
    }

    //Use this to set the values in the views defined by the cards in the holders.
    public void onBindViewHolder(final BasicWeatherViewHolder holder, final int listPosition) {

        TextView temperatureTextView = holder.currentTemperatureTextView;
        TextView cardNumberTextView = holder.cardNumberTextView;

        temperatureTextView.setText("" + weatherData.get(listPosition).currently.temperature);
        cardNumberTextView.setText("" + (listPosition+1));
    }

    public int getItemCount(){

        return weatherData.size();
    }

    //Nested class to define the ViewHolder required by the Adapter.
    public static class BasicWeatherViewHolder extends RecyclerView.ViewHolder{

        TextView currentTemperatureTextView;
        TextView cardNumberTextView;
        ResponseBody cardData;

        public BasicWeatherViewHolder(View itemView){

            super(itemView);
            this.currentTemperatureTextView = (TextView) itemView.findViewById(R.id.dispTemp);
            this.cardNumberTextView = (TextView) itemView.findViewById(R.id.itemCounter);
        }
    }
}
