package vn.edu.usth.weather;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class Adapter extends FragmentPagerAdapter {
    private Resources resources;
    private final int PAGE_COUNT = 3;
    private String titles[] = null;
//     new String[] { "Hanoi", "Paris", "Toulouse" }




    public Adapter(FragmentManager fm, Resources resources) {
        super(fm);
        this.resources = resources;
    }

    @NonNull
    @Override
    public Fragment getItem(int page) {

        Bundle bundle = new Bundle();
        bundle.putString("position", Integer.toString(page));
        Log.i("Page is", "getItem: "+ page);

        switch (page) {
            case 0:
                WeatherAndForecastFragment Fragment1 = new WeatherAndForecastFragment();
                Fragment1.setArguments(bundle);
                return Fragment1;
            case 1:
                WeatherAndForecastFragment Fragment2 = new WeatherAndForecastFragment();
                Fragment2.setArguments(bundle);
                return Fragment2;
            case 2:
                WeatherAndForecastFragment Fragment3 = new WeatherAndForecastFragment();
                Fragment3.setArguments(bundle);
                return Fragment3;
        }
        WeatherAndForecastFragment EmptyFragment;
        return EmptyFragment = new WeatherAndForecastFragment();


//        return WeatherAndForecastFragment.newInstance(bundle);
//        page++;

    }

    @Override
    public int getCount() { return PAGE_COUNT; }

    @Override
    public CharSequence getPageTitle(int page) {
        titles = resources.getStringArray(R.array.locations);
// returns a tab title corresponding to the specified page
        Log.i("getPageTitle", "getItem: "+ titles[page]);
        return titles[page];
    }

}