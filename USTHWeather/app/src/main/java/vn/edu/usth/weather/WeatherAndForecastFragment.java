package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class WeatherAndForecastFragment extends Fragment {

    public WeatherAndForecastFragment() {
        // Required empty public constructor
    }


//    public static WeatherAndForecastFragment newInstance(Bundle bundle) {
//        WeatherAndForecastFragment fragment = new WeatherAndForecastFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v  = (View) inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);

//
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WeatherFragment weatherFragment = new WeatherFragment();


        Log.i("Bundle", "onCreateView: "+ getArguments().getString("position"));
        
//        weatherFragment1.setArguments(getArguments());


        ForecastFragment forecastFragment = new ForecastFragment();
        weatherFragment.displayReceivedData(getArguments().getString("position"));

        fragmentTransaction.replace(R.id.weather_fragment, weatherFragment);
        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.replace(R.id.forecast_fragment, forecastFragment);
        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        
        return v;
    }

}