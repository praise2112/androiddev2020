package vn.edu.usth.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


public class ForecastFragment extends Fragment {
    private String[] days, description;
    private int[] images = {R.drawable.clear, R.drawable.cloudy, R.drawable.thundershowers, R.drawable.light_rain, R.drawable.thunderstorms, R.drawable.hot, R.drawable.foggy};
    private Random rand = new Random();
//  return day iteratively from the days array
    public String getNextDay(int j){
        if(j >= 7) {   // keeps value of i between array length
            j = j%7;
        }
        return days[j];
    }
//    return random description
    public String getRandDescription(int j){
        if(j >= 7) {   // keeps value of i between array length
            j = j%7;
        }
        return description[j];
    }

    public ForecastFragment( ) {
        // Required empty public constructor
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = new View(getContext());
//        v.setBackgroundColor(0xFF00AC9D);
//        return v;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        days = getResources().getStringArray(R.array.days);
        description = getResources().getStringArray(R.array.weather_description);

//        LinearLayout fragment_container = (LinearLayout) view.findViewById(R.id
//                .fragment_container);
        LinearLayout scroll_view_container = (LinearLayout) view.findViewById(R.id
                .scroll_view_container);


//        Generating multiple fragment_forecast_item and adding them into fragment_container
        for (int i = 0; i < 16 ; i++) { // generating 15 fragment_forecast_item
//            modifying a fragment_forecast_item
            final View v = inflater.inflate(R.layout.fragment_forecast_item, null);
            TextView day = (TextView) v.findViewById(R.id.day);
            TextView weatherDescription = (TextView) v.findViewById(R.id.weatherDescription);
            ImageView weatherImg = (ImageView) v.findViewById(R.id.weatherImg);
            day.setText(getNextDay(i));
            weatherDescription.setText(getRandDescription(i));
            weatherImg.setImageResource(images[rand.nextInt(images.length)]);   // picking random image from array
            scroll_view_container.addView(v);   // adding forest_item_fragment to scroll view
        }

//        inflater.inflate(R.layout.fragment_forecast_item, fragment_container);
        return view;
    }
}

