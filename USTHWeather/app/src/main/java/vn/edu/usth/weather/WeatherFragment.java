package vn.edu.usth.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class WeatherFragment extends Fragment {
    private int pos;
    private WeatherAndForecastFragment.WeatherHttpClient weatherHttpClient;
    private String currentLocation [] = null ;



    public WeatherFragment() {

        // Required empty public constructor
    }

    public static WeatherFragment newInstance() {
        
        Bundle args = new Bundle();
        
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Bundle WeatherFragment", "onCreateView: "+ getArguments().getString("position"));

//        currentLocation = getResources().getStringArray(R.array.locations); // get location list from resources
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
//        Log.i("Current Location", "onCreateView: "+ currentLocation[0]);
        TextView location = (TextView) view.findViewById(R.id.location);
        TextView description = (TextView) view.findViewById(R.id.current_weather_description);
        ImageView weatherImage = (ImageView) view.findViewById(R.id.current_image_description);

//        Log.i("Current Location4", "onCreateView: "+  location.getText());
//        pos = Integer.parseInt(getArguments().getString("position"));
//        location.setText(currentLocation[pos]);
        location.setText(weatherHttpClient.getLocation());
        description.setText(weatherHttpClient.getCurrent_description() + " \n "+ weatherHttpClient.getCurrent_degree() + "°C");
        weatherImage.setImageResource(weatherHttpClient.getCurrent_image());
//        location.setText(Integer.parseInt(getArguments().getString("position")));

        return view;
    }




    public void displayReceivedData(WeatherAndForecastFragment.WeatherHttpClient message)
    {
//        pos = Integer.parseInt(message);
        weatherHttpClient = message;

    }


}
