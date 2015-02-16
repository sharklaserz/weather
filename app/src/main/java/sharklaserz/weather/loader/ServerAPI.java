package sharklaserz.weather.loader;

import android.text.Html;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ServerAPI {

    //public static final String ENDPOINT = "http://api.icndb.com";
    public static final String ENDPOINT = "https://api.forecast.io/forecast";

    //public static class Item {
        //@SerializedName("joke")
        //public String text;

        //@Override
        //public String toString() {
        //    return Html.fromHtml(text).toString();
        //}

    //}

    public static class MyTemp {
        @SerializedName("temperature")
        public double temperature;

        @Override
        public String toString() {
            return Double.toString(temperature);
        }
    }

    public static class Response {
        @SerializedName("currently")
        public MyTemp myCurrently;
    }

    @GET("/1d454a06cbec825829cf04cf8bdcd4ac/37.8267,-122.423")
    void getItems(Callback<Response> callback);
}