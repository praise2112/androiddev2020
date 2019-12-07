package vn.edu.usth.weather;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;


public class ForecastFragment extends Fragment {
    private View view;
    private String loc;
    private WeatherAndForecastFragment.WeatherHttpClient weatherHttpClient;
    private int num_of_days = 14;
    private String[] days, description, min_temp, max_temp;
//    private int[] images = {R.drawable.clear, R.drawable.cloudy, R.drawable.thundershowers, R.drawable.light_rain, R.drawable.thunderstorms, R.drawable.hot, R.drawable.foggy};
    private int[] weather_images;
    private Random rand = new Random();

    //  return day iteratively from the days array
    public String getNextDay(int j) {
        if (j >= 7) {   // keeps value of i between array length
            j = j % 7;
        }
        return days[j];
    }

    //    return random description
    public String getRandDescription(int j) {
        if (j >= 7) {   // keeps value of i between array length
            j = j % 7;
        }
        return description[j];
    }

    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastFragment newInstance() {

        Bundle args = new Bundle();

        ForecastFragment fragment = new ForecastFragment();
        fragment.setArguments(args);
        return fragment;
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
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i("Bundle ForecastFragment", "onCreateView: "+ getArguments().getString("position"));


        view = inflater.inflate(R.layout.fragment_forecast, container, false);
        Log.i("LOCATION", "onCreateView: "+ loc);

//        WeatherHttpClient weatherHttpClient = new WeatherHttpClient(loc, num_of_days);
//        weatherHttpClient.getWeatherData();

//        weatherHttpClient.getWeatherData();


//        days = getResources().getStringArray(R.array.days);
//        description = getResources().getStringArray(R.array.weather_description);
//        loc = getArguments().getString("location");
        loc = weatherHttpClient.getLocation();
        days = weatherHttpClient.getDays_();
        description = weatherHttpClient.getDescription_();
        min_temp = weatherHttpClient.getMin_degree();
        max_temp = weatherHttpClient.getMax_degree();
        weather_images = weatherHttpClient.getWeatherImages();


//        LinearLayout fragment_container = (LinearLayout) view.findViewById(R.id
//                .fragment_container);
        LinearLayout scroll_view_container = (LinearLayout) view.findViewById(R.id
                .scroll_view_container);


//        Generating multiple fragment_forecast_item and adding them into fragment_container
        for (int i = 0; i < num_of_days; i++) { // generating 15 fragment_forecast_item
//            modifying a fragment_forecast_item
            final View v = inflater.inflate(R.layout.fragment_forecast_item, null);
            TextView day = (TextView) v.findViewById(R.id.day);
            TextView weatherDescription = (TextView) v.findViewById(R.id.weatherDescription);
            ImageView weatherImg = (ImageView) v.findViewById(R.id.weatherImg);
//            day.setText(getNextDay(i));
            day.setText(days[i]);
//            weatherDescription.setText(getRandDescription(i));
            weatherDescription.setText(description[i]+" \n "+ min_temp[i] +"°C - "+ max_temp[i] +  "°C");
//            weatherImg.setImageResource(images[rand.nextInt(images.length)]);   // picking random image from array
            weatherImg.setImageResource(weather_images[i]);   // picking random image from array
            scroll_view_container.addView(v);   // adding forest_item_fragment to scroll view
        }
//        getWeatherrrData("london", "5");

        // once, should be performed once per app instance
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getImageRequest());
//        queue.add(getWeatherrrData("london", "5"));
        // or
        // new GetRequest().execute("https://ictlab.usth.edu.vn/wp-content/uploads/logos/usth.png");



//        inflater.inflate(R.layout.fragment_forecast_item, fragment_container);
        return view;
    }
    public void displayReceivedData(WeatherAndForecastFragment.WeatherHttpClient message)
    {
//        loc = message;
        weatherHttpClient = message;
    }

    private class GetRequest extends AsyncTask<String, Void, Bitmap> {
        private String content;
        private Handler handler;
        private Message msg;
        private ProgressDialog simpleWaitDialog;
//        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        @Override
        protected void onPreExecute() {
            Log.i("Async-Example", "onPreExecute Called");
            // do some preparation here, if needed
            simpleWaitDialog = ProgressDialog.show(getActivity(), "Wait", "Downloading Image");

        }

        @Override
        protected Bitmap doInBackground(String... param) {
            try {
//                Thread.sleep(10000);
                URL url = new URL(param[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            // This method is called in the main thread, so it's possible
            // to update UI to reflect the worker thread progress here.
            // In a network access task, this should update a progress bar
            // to reflect how many percent of data has been retrieved
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.i("Async-Example", "onPostExecute Called");
            simpleWaitDialog.dismiss();
            ImageView imageView = (ImageView) view.findViewById(R.id.usthLogo);
            imageView.setImageBitmap(result);
        }
    }

    public ImageRequest getImageRequest() {

        // a listener (kinda similar to onPostExecute())
        Response.Listener<Bitmap> listener =
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView iv = (ImageView) view.findViewById(R.id.usthLogo);
                        iv.setImageBitmap(response);
                    }
                };
        // a simple request to the required image
        ImageRequest imageRequest = new ImageRequest(
                "https://ictlab.usth.edu.vn/wp-content/uploads/logos/usth.png",
                listener, 0, 0, ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888, null);
        return imageRequest;

    }


}







