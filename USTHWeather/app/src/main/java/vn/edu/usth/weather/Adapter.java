package vn.edu.usth.weather;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class Adapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;

    public Adapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int page) {
        WeatherAndForecastFragment weatherAndForecastFragment = new WeatherAndForecastFragment();
        Bundle bundle = new Bundle();
        page++;
        bundle.putString("position", Integer.toString(page));
        weatherAndForecastFragment.setArguments(bundle);
        return  weatherAndForecastFragment;
    }

    @Override
    public int getCount() { return PAGE_COUNT; }
}