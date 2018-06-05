package bottomnav.hitherejoe.com.bottomnavigationsample.accueil;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import bottomnav.hitherejoe.com.bottomnavigationsample.GenerationActivity;
import bottomnav.hitherejoe.com.bottomnavigationsample.R;
import bottomnav.hitherejoe.com.bottomnavigationsample.RemoteFetch;

/**
 * Created by Caron on 20/02/2017.
 */

public class WeatherFragment extends Fragment {
    Typeface weatherFont;
    static double tempe=0;
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    View rootView;

    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        CityPreference c= new CityPreference(getActivity());
        c.setCity("Paris");
        updateWeatherData(c.getCity());
    }

    public WeatherFragment(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_weather_ativity, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
        final TextView gentenue_button = (TextView) rootView.findViewById(R.id.genererbutton);
        gentenue_button.setOnClickListener(gentenue_buttonListener);
        //couleur de fond
        //rootView.setBackgroundColor(getResources().getColor(R.color.blue1));

        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

    private View.OnClickListener gentenue_buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), GenerationActivity.class);
            Bundle b = new Bundle();
            b.putDouble("temperature", getTempe());
            intent.putExtras(b);
            startActivity(intent);
        }
    };

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try {
            cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            detailsField.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ℃");
                    tempe=main.getDouble("temp");
            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }
    public static double getTempe(){
        return tempe;
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
                rootView.setBackgroundResource(R.drawable.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
                rootView.setBackgroundResource(R.drawable.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    rootView.setBackgroundResource(R.drawable.weather_lightning);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    rootView.setBackgroundResource(R.drawable.weather_brume);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    rootView.setBackgroundResource(R.drawable.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    rootView.setBackgroundResource(R.drawable.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    rootView.setBackgroundResource(R.drawable.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    rootView.setBackgroundResource(R.drawable.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }
    public void changeCity(String city){
        updateWeatherData(city);
    }
}
