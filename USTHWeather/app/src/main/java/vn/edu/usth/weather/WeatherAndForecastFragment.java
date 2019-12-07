package vn.edu.usth.weather;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class WeatherAndForecastFragment extends Fragment {
    private View v;
    private WeatherHttpClient weatherHttpClient;
    public WeatherAndForecastFragment() {
        // Required empty public constructor
    }


    public static WeatherAndForecastFragment newInstance() {
        
        Bundle args = new Bundle();
        
        WeatherAndForecastFragment fragment = new WeatherAndForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v  = (View) inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);

        // getting weather data from api then adding the forecast fragment and weather fragment
        new getWeatherAsync().execute();

        return v;   // view is created but will be updated once data is recieved
    }


private class getWeatherAsync extends AsyncTask<String,Void, WeatherHttpClient>  {

    private ProgressDialog simpleWaitDialog;
//        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    @Override
    protected void onPreExecute() {
        Log.i("Async-Example", "onPreExecute Called");
        // do some preparation here, if needed
        simpleWaitDialog = ProgressDialog.show(getActivity(), "Wait", "Getting Weather Conditions");

    }

    @Override
    protected WeatherHttpClient doInBackground(String... param) {
        try {
//                Thread.sleep(10000);
            weatherHttpClient = new WeatherHttpClient(getArguments().getString("location"), 14);
            weatherHttpClient.getWeatherData();

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            WeatherFragment weatherFragment =  WeatherFragment.newInstance();
            weatherFragment.setArguments(getArguments());
            ForecastFragment forecastFragment = ForecastFragment.newInstance();
            forecastFragment.setArguments(getArguments());


            Log.i("Bundle", "onCreateView: "+ getArguments().getString("position"));

//        weatherFragment1.setArguments(getArguments());


            weatherFragment.displayReceivedData(weatherHttpClient);
            forecastFragment.displayReceivedData(weatherHttpClient);


//        fragmentTransaction.replace(R.id.weather_fragment, weatherFragment);
            fragmentTransaction.add(R.id.weather_and_forecast, weatherFragment);
            fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

//        fragmentTransaction.replace(R.id.forecast_fragment, forecastFragment);
            fragmentTransaction.add(R.id.weather_and_forecast, forecastFragment);
            fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();

            return weatherHttpClient;

        } catch (Exception e) {
            e.printStackTrace();
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
    protected void onPostExecute(WeatherHttpClient result) {
        super.onPostExecute(result);
        Log.i("Async-Example", "onPostExecute Called");
        simpleWaitDialog.dismiss();
    }
}



    public class WeatherHttpClient {
        private String location, no_of_days;
        private String[] days_ ,description_, min_degree, max_degree;
        private String current_degree, current_description, current_day;
        private int current_image;

        public String[] getMin_degree() {
            return min_degree;
        }

        public String[] getMax_degree() {
            return max_degree;
        }



        public String[] getDays_() {
            return days_;
        }

        public String[] getDescription_() {
            return description_;
        }


        public String getLocation() {
            return location;
        }

        public String getCurrent_degree() {
            return current_degree;
        }

        public String getCurrent_description() {
            return current_description;
        }

        public int getCurrent_image() {
            String weatherDescription = getCurrent_description();
            String image = weatherDescription.replace(" ", "_").replace(",","").toLowerCase();
            current_image= getResources().getIdentifier(image, "drawable",getContext().getPackageName());
            return current_image;
        }


        public WeatherHttpClient(String location, int no_of_days_int) {
            this.location = location;
            this.no_of_days = Integer.toString(no_of_days_int);
            this.days_ = new String[no_of_days_int];
            this.description_ = new String[no_of_days_int];
            this.min_degree = new String[no_of_days_int];
            this.max_degree = new String[no_of_days_int];
        }


        public int[] getWeatherImages(){
            int [] images = new int[Integer.parseInt(no_of_days)];
            String weatherDescription [] = getDescription_();
            for (int i = 0; i < weatherDescription.length; i++) {
                String image = weatherDescription[i].replace(" ", "_").replace(",","").toLowerCase();
                images[i] = getResources().getIdentifier(image, "drawable",getContext().getPackageName());
            }
            return images;
        }



        private String BASE_URL = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=";
        private String apiKey =  getResources().getString(R.string.API_KEY);


        public void getWeatherData() {

//            Log.i("Number", "getWeatherData: "+ no_of_days_int);
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
                String url = BASE_URL + apiKey + "&q=" + location + "&num_of_days=" + no_of_days + "&tp=24" + "&format=json";
                HttpURLConnection con = null;
                InputStream is = null;
                try {
                    Log.i("Urlllllllll", "getWeatherData: " + url);
                    URL urlForGetRequest = new URL(url);
                    String readLine = null;
                    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
                    conection.setRequestMethod("GET");
//                    conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
                    int responseCode = conection.getResponseCode();
                    Log.i("Response Code", "getWeatherData: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(conection.getInputStream()));
                        StringBuffer response = new StringBuffer();
                        while ((readLine = in.readLine()) != null) {
                            response.append(readLine);
                        }
                        in.close();
                        // print result
                        System.out.println("JSON String Result " + response.toString());
                        setDataFromJson(response.toString());

                        //GetAndPost.POSTRequest(response.toString());
                    } else {
                        System.out.println("GET NOT WORKED");
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (Throwable t) {
                    }
                    try {
                        con.disconnect();
                    } catch (Throwable t) {
                    }
                }

            }


        }

        public void setDataFromJson(String response) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar mydate = new GregorianCalendar();
            String TAG = "Weather";

            try {

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response);

                // getting data
                JSONObject data = (JSONObject) json.get("data");

                // getting current_weather
                JSONArray current_condition_array = (JSONArray) data.get("current_condition");
                JSONObject current_condition_obj = (JSONObject) current_condition_array.get(0);
                current_degree = (String) current_condition_obj.get("temp_C");
                Log.i(TAG, "setDataFromJson: "+ current_degree);

                // getting current_weather_description
                JSONArray current_condition_weatherDesc_array = (JSONArray) current_condition_obj.get("weatherDesc");
                JSONObject current_condition_weatherDesc_obj = (JSONObject) current_condition_weatherDesc_array.get(0);
                current_description = (String) current_condition_weatherDesc_obj.get("value");
                Log.i(TAG, "setDataFromJson: "+ current_description);


                // getting current day
                JSONArray first_weather_array = (JSONArray) data.get("weather");
                JSONObject first_weather_obj = (JSONObject) first_weather_array.get(0);
                String current_date = (String) first_weather_obj.get("date");
                Date date_current = dateFormat.parse(current_date);
                mydate.setTime(date_current);
                current_day = getDay(mydate.get(Calendar.DAY_OF_WEEK));
                Log.i(TAG, "setDataFromJson: "+ current_day);



                // get Next days
                JSONArray weather_array = (JSONArray) data.get("weather");
//                Log.i(TAG, "setDataFromJson: "+ weather_array.size());
                for (int i = 0; i < weather_array.size(); i++) {
                    JSONObject weather_obj = (JSONObject) weather_array.get(i);

                    // getting min temp of that day
                    min_degree[i] = (String) weather_obj.get("mintempC");
                    Log.i(TAG, "setDataFromJson: "+ min_degree[i]);

                    // getting max temp of that day
                    max_degree[i] = (String) weather_obj.get("maxtempC");
                    Log.i(TAG, "setDataFromJson: "+ max_degree[i]);

                    // getting weatherDesc of that day
                    JSONArray weather_hourly_array = (JSONArray) weather_obj.get("hourly");
                    JSONObject weather_hourly_obj = (JSONObject) weather_hourly_array.get(0);
                    JSONArray weatherDesc_array = (JSONArray) weather_hourly_obj.get("weatherDesc");
                    JSONObject weatherDesc_obj = (JSONObject) weatherDesc_array.get(0);
                    description_[i] = (String) weatherDesc_obj.get("value");
                    Log.i(TAG, "setDataFromJson: "+ description_[i]);


                    // getting date of the day
                    String date = (String) weather_obj.get("date");
                    Date date_ = dateFormat.parse(date);
                    mydate.setTime(date_);
                    days_[i] = getDay(mydate.get(Calendar.DAY_OF_WEEK));
                    Log.i(TAG, "setDataFromJson: "+ days_[i]);

                }

            } catch (Exception e) {
                Log.i("Error", "setDataFromJson: " + e);
            }


        }

        public String getDay(int day){
            switch (day){
                case 1: return "Sun";
                case 2: return "Mon";
                case 3: return "Tue";
                case 4: return "Wed";
                case 5: return "Thu";
                case 6: return "Fri";
                case 7: return "Sat";
                default: return "";
            }
        }

    }
}